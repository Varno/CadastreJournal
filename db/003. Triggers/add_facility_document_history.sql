create or replace trigger rrtest.add_facility_document_history
after insert or update or delete on rrtest.facility_documents
for each row
declare
  changes nclob;
  descr xmltype;
  action RRTEST.FACILITY_HISTORY.ACTION%TYPE;
  facility_id  RRTEST.FACILITY_HISTORY.FACILITY_ID%TYPE;
  modified_by RRTEST.FACILITY_HISTORY.MODIFIED_BY%TYPE;
  modified_by_ip  RRTEST.FACILITY_HISTORY.MODIFIED_BY_IP%TYPE;
begin

  changes := '';
  
  if (:old.DOCUMENT_ID is null) then
    
    action := 'create document';
    facility_id := :new.FACILITY_ID;
    modified_by := :new.MODIFIED_BY;
    modified_by_ip := :new.MODIFIED_BY_IP;

    changes := '<Field><Name>DOCUMENT_ID</Name><Old>null</Old><New>' 
      || nvl(:new.DOCUMENT_ID, 0) || '</New></Field>'
      || '<Field><Name>FILE_NAME</Name><Old>null</Old><New>' 
      || nvl(rrtest.quote_xml(:new.FILE_NAME), 'null') || '</New></Field>'
      || '<Field><Name>STORED_PATH</Name><Old>null</Old><New>' 
      || nvl(rrtest.quote_xml(:new.STORED_PATH), 'null') || '</New></Field>';
    
  elsif (:new.DOCUMENT_ID is null) then
    
    action := 'delete document';
    facility_id := :old.FACILITY_ID;
    modified_by := :old.MODIFIED_BY;
    modified_by_ip := :old.MODIFIED_BY_IP;

    changes := '<Field><Name>DOCUMENT_ID</Name><Old>' 
      || nvl(:old.DOCUMENT_ID, 0) 
      || '</Old><New>null</New></Field>'
      || '<Field><Name>FILE_NAME</Name><Old>' 
      || nvl(rrtest.quote_xml(:old.FILE_NAME), 'null') 
      || '</Old><New>null</New></Field>'
      || '<Field><Name>STORED_PATH</Name><Old>' 
      || nvl(rrtest.quote_xml(:old.STORED_PATH), 'null') 
      || '</Old><New>null</New></Field>';
    
  else
    
    action := 'update document';
    facility_id := :new.FACILITY_ID;
    modified_by := :new.MODIFIED_BY;
    modified_by_ip := :new.MODIFIED_BY_IP;

    if (:old.FILE_NAME <> :new.FILE_NAME or :old.STORED_PATH <> :new.STORED_PATH) then
      changes := changes || '<Field><Name>DOCUMENT_ID</Name><Old>' 
        || nvl(:old.DOCUMENT_ID, 0) || '</Old><New>' 
        || nvl(:new.DOCUMENT_ID, 0) || '</New></Field>';
    end if;
    
    if (:old.FILE_NAME <> :new.FILE_NAME) then
      changes := changes || '<Field><Name>FILE_NAME</Name><Old>' 
        || nvl(rrtest.quote_xml(:old.FILE_NAME), 'null') || '</Old><New>' 
        || nvl(rrtest.quote_xml(:new.FILE_NAME), 'null') || '</New></Field>';
    end if;
  
    if (:old.STORED_PATH <> :new.STORED_PATH) then
      changes := changes || '<Field><Name>STORED_PATH</Name><Old>' 
        || nvl(rrtest.quote_xml(:old.STORED_PATH), 'null') || '</Old><New>' 
        || nvl(rrtest.quote_xml(:new.STORED_PATH), 'null') || '</New></Field>';
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
