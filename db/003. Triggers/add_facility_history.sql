-- =============================================
-- v.1.0: Created by Pavel Kuznetsov 20/06/2014
-- after all oparations with facility write to log
-- =============================================
create or replace trigger rrtest.add_facility_history
after insert or update or delete on rrtest.facilities
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
  
  if (:old.FACILITY_ID is null) then
    
    action := 'Создание';
    facility_id := :new.FACILITY_ID;
    modified_by := :new.MODIFIED_BY;
    modified_by_ip := :new.MODIFIED_BY_IP;

    select
        XMLElement("Field", XMLForest('CADASTRAL_NUMBER' as Name
                      , 'null' as Old
                      , :new.CADASTRAL_NUMBER as New)) || 
        XMLElement("Field" , XMLForest('SQUARE' as Name
                      , 'null' as Old
                      , :new.SQUARE as New)) ||
        XMLElement("Field", XMLForest('DESTINATION_ID' as Name
                      , 'null' as Old
                      , :new.DESTINATION_ID as New)) ||
        XMLElement("Field", XMLForest('AREA_DESCRIPTION' as Name
                      , 'null' as Old
                      , :new.AREA_DESCRIPTION as New)) ||
        XMLElement("Field", XMLForest('USAGE_ID' as Name
                      , 'null' as Old
                      , :new.USAGE_ID as New)) ||
        XMLElement("Field", XMLForest('ADDRESS' as Name
                      , 'null' as Old
                      , :new.ADDRESS as New)) x
    into changes    
    from dual;

  elsif (:new.FACILITY_ID is null) then
    
    action := 'Удаление';
    facility_id := :old.FACILITY_ID;
    modified_by := :old.MODIFIED_BY;
    modified_by_ip := :old.MODIFIED_BY_IP;

    select
        XMLElement("Field", XMLForest('CADASTRAL_NUMBER' as Name
                      , :old.CADASTRAL_NUMBER as Old
                      , :old.CADASTRAL_NUMBER as New)) || 
        XMLElement("Field", XMLForest('SQUARE' as Name
                      , :old.SQUARE as Old
                      , :old.SQUARE as New)) ||
        XMLElement("Field", XMLForest('DESTINATION_ID' as Name
                      , :old.DESTINATION_ID as Old
                      , :old.DESTINATION_ID as New)) ||
        XMLElement("Field", XMLForest('AREA_DESCRIPTION' as Name
                      , :old.AREA_DESCRIPTION as Old
                      , :old.AREA_DESCRIPTION as New)) ||
        XMLElement("Field", XMLForest('USAGE_ID' as Name
                      , :old.USAGE_ID as Old
                      , :old.USAGE_ID as New)) ||
        XMLElement("Field", XMLForest('ADDRESS' as Name
                      , :old.ADDRESS as Old
                      , :old.ADDRESS as New)) x
    into changes    
    from dual;

  else
    
    action := 'Изменение';
    facility_id := :new.FACILITY_ID;
    modified_by := :new.MODIFIED_BY;
    modified_by_ip := :new.MODIFIED_BY_IP;
    
    if (:old.SQUARE <> :new.SQUARE) then
      select ''||
        XMLElement("Field", XMLForest('SQUARE' as Name
                      , :old.SQUARE as Old
                      , :new.SQUARE as New))
      into changed_item
      from dual;    
      changes := changes || changed_item;
    end if;
  
    if (:old.DESTINATION_ID <> :new.DESTINATION_ID) then
      select ''||
        XMLElement("Field", XMLForest('DESTINATION_ID' as Name
                      , :old.DESTINATION_ID as Old
                      , :new.DESTINATION_ID as New))
      into changed_item
      from dual;    
      changes := changes || changed_item;
    end if;
  
    if (:old.AREA_DESCRIPTION <> :new.AREA_DESCRIPTION) then
      select ''||
        XMLElement("Field", XMLForest('AREA_DESCRIPTION' as Name
                      , :old.AREA_DESCRIPTION as Old
                      , :new.AREA_DESCRIPTION as New))
      into changed_item
      from dual;    
      changes := changes || changed_item;
    end if;
  
    if (:old.USAGE_ID <> :new.USAGE_ID) then
      select ''||
        XMLElement("Field", XMLForest('USAGE_ID' as Name
                      , :old.USAGE_ID as Old
                      , :new.USAGE_ID as New))
      into changed_item
      from dual;    
      changes := changes || changed_item;
    end if;
  
    if (:old.ADDRESS <> :new.ADDRESS) then
      select ''||
        XMLElement("Field", XMLForest('ADDRESS' as Name
                      , :old.ADDRESS as Old
                      , :new.ADDRESS as New))
      into changed_item
      from dual;    
      changes := changes || changed_item;
    end if;

    if (length(changes) > 0) then
      select ''||
        XMLElement("Field", XMLForest('CADASTRAL_NUMBER' as Name
                      , :old.CADASTRAL_NUMBER as Old
                      , :new.CADASTRAL_NUMBER as New))
      into changed_item
      from dual;
      changes := changed_item || changes;
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