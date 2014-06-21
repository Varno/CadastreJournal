-- =============================================
-- v.1.0: Created by Pavel Kuznetsov 20/06/2014
-- after all oparations with facilities doc write to log
-- =============================================
create or replace trigger rrtest.add_facility_document_history
after insert or update or delete on rrtest.facility_documents
for each row
declare
  changes clob;
  changed_item clob;
  descr xmltype;
  action RRTEST.FACILITY_HISTORY.ACTION%TYPE;
  facility_id  RRTEST.FACILITY_HISTORY.FACILITY_ID%TYPE;
  modified_by RRTEST.FACILITY_HISTORY.MODIFIED_BY%TYPE;
  modified_by_ip  RRTEST.FACILITY_HISTORY.MODIFIED_BY_IP%TYPE;
begin

  changes := '';
  changed_item := '';
  
  if (:old.DOCUMENT_ID is null) then
    
    action := 'create document';
    facility_id := :new.FACILITY_ID;
    modified_by := :new.MODIFIED_BY;
    modified_by_ip := :new.MODIFIED_BY_IP;

    select
        XMLElement("Field", XMLForest('DOCUMENT_ID' as Name
                      , 'null' as Old
                      , :new.DOCUMENT_ID as New)) || 
        XMLElement("Field" , XMLForest('FILE_NAME' as Name
                      , 'null' as Old
                      , :new.FILE_NAME as New)) ||
        XMLElement("Field", XMLForest('STORED_PATH' as Name
                      , 'null' as Old
                      , :new.STORED_PATH as New)) x
    into changes    
    from dual;
    
  elsif (:new.DOCUMENT_ID is null) then
    
    action := 'delete document';
    facility_id := :old.FACILITY_ID;
    modified_by := :old.MODIFIED_BY;
    modified_by_ip := :old.MODIFIED_BY_IP;

    select
        XMLElement("Field", XMLForest('DOCUMENT_ID' as Name
                      , :old.DOCUMENT_ID as Old
                      , :old.DOCUMENT_ID as New)) || 
        XMLElement("Field", XMLForest('FILE_NAME' as Name
                      , :old.FILE_NAME as Old
                      , :old.FILE_NAME as New)) ||
        XMLElement("Field", XMLForest('STORED_PATH' as Name
                      , :old.STORED_PATH as Old
                      , :old.FILE_NAME as New)) x
    into changes    
    from dual;

  else
    
    action := 'update document';
    facility_id := :new.FACILITY_ID;
    modified_by := :new.MODIFIED_BY;
    modified_by_ip := :new.MODIFIED_BY_IP;

    if (:old.FILE_NAME <> :new.FILE_NAME or :old.STORED_PATH <> :new.STORED_PATH) then
      select ''||
        XMLElement("Field", XMLForest('DOCUMENT_ID' as Name
                      , :old.DOCUMENT_ID as Old
                      , :new.DOCUMENT_ID as New))
      into changed_item
      from dual;
      changes := changes || changed_item;
    end if;
    
    if (:old.FILE_NAME <> :new.FILE_NAME) then
      select ''||
        XMLElement("Field", XMLForest('FILE_NAME' as Name
                      , :old.FILE_NAME as Old
                      , :new.FILE_NAME as New))
      into changed_item
      from dual;
      changes := changes || changed_item;
    end if;
  
    if (:old.STORED_PATH <> :new.STORED_PATH) then
      select ''||
        XMLElement("Field", XMLForest('STORED_PATH' as Name
                      , :old.STORED_PATH as Old
                      , :new.STORED_PATH as New))
      into changed_item
      from dual;
      changes := changes || changed_item;
    end if;
  
  end if;

  if (length(changes) > 0) then

    descr := xmltype('<Fields>'||changes||'</Fields>');

    insert into rrtest.facility_history
    values
      (rrtest.facility_history_seq.nextval
      , facility_id
      , sysdate
      , modified_by
      , modified_by_ip
      , action
      , descr);
  
  end if;

end;
