create or replace trigger rrtest.add_facility_history
after insert or update or delete on rrtest.facilities
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
  
  if (:old.FACILITY_ID is null) then
    
    action := 'create facility';
    facility_id := :new.FACILITY_ID;
    modified_by := :new.MODIFIED_BY;
    modified_by_ip := :new.MODIFIED_BY_IP;
    
    changes := '<Field><Name>CADASTRAL_NUMBER</Name><Old>null</Old><New>' 
      || nvl(rrtest.quote_xml(:new.CADASTRAL_NUMBER), 'null') || '</New></Field>'
      || '<Field><Name>SQUARE</Name><Old>null</Old><New>' 
      || nvl(:new.SQUARE, 0) || '</New></Field>'
      || '<Field><Name>DESTINATION_ID</Name><Old>null</Old><New>' 
      || nvl(:new.DESTINATION_ID, 0) || '</New></Field>'
      || '<Field><Name>AREA_DESCRIPTION</Name><Old>null</Old><New>' 
      || nvl(rrtest.quote_xml(:new.AREA_DESCRIPTION), 'null') || '</New></Field>'
      || '<Field><Name>USAGE_ID</Name><Old>null</Old><New>' 
      || nvl(:new.USAGE_ID, 0) || '</New></Field>'
      || '<Field><Name>ADDRESS</Name><Old>null</Old><New>' 
      || nvl(rrtest.quote_xml(:new.ADDRESS), 'null') || '</New></Field>';
    
  elsif (:new.FACILITY_ID is null) then
    
    action := 'delete facility';
    facility_id := :old.FACILITY_ID;
    modified_by := :old.MODIFIED_BY;
    modified_by_ip := :old.MODIFIED_BY_IP;

    changes := '<Field><Name>CADASTRAL_NUMBER</Name><Old>' 
      || nvl(rrtest.quote_xml(:old.CADASTRAL_NUMBER), 'null') 
      || '</Old><New>null</New></Field>'
      || '<Field><Name>SQUARE</Name><Old>' || nvl(:old.SQUARE, 0) 
      || '</Old><New>null</New></Field>'
      || '<Field><Name>DESTINATION_ID</Name><Old>' 
      || nvl(:old.DESTINATION_ID, 0) || '</Old><New>null</New></Field>'
      || '<Field><Name>AREA_DESCRIPTION</Name><Old>' 
      || nvl(rrtest.quote_xml(:old.AREA_DESCRIPTION), 'null') 
      || '</Old><New>null</New></Field>'
      || '<Field><Name>USAGE_ID</Name><Old>' 
      || nvl(:old.USAGE_ID, 0) || '</Old><New>null</New></Field>'
      || '<Field><Name>ADDRESS</Name><Old>' 
      || nvl(rrtest.quote_xml(:old.ADDRESS), 'null') 
      || '</Old><New>null</New></Field>';
    
  else
    
    action := 'update facility';
    facility_id := :new.FACILITY_ID;
    modified_by := :new.MODIFIED_BY;
    modified_by_ip := :new.MODIFIED_BY_IP;
    
    if (:old.CADASTRAL_NUMBER <> :new.CADASTRAL_NUMBER) then
      changes := changes || '<Field><Name>CADASTRAL_NUMBER</Name><Old>' 
        || nvl(rrtest.quote_xml(:old.CADASTRAL_NUMBER), 'null') || '</Old><New>' 
        || nvl(rrtest.quote_xml(:new.CADASTRAL_NUMBER), 'null') || '</New></Field>';
    end if;
  
    if (:old.SQUARE <> :new.SQUARE) then
      changes := changes || '<Field><Name>SQUARE</Name><Old>' 
        || nvl(:old.SQUARE, 0) || '</Old><New>' 
        || nvl(:new.SQUARE, 0) || '</New></Field>';
    end if;
  
    if (:old.DESTINATION_ID <> :new.DESTINATION_ID) then
      changes := changes || '<Field><Name>DESTINATION_ID</Name><Old>' 
        || nvl(:old.DESTINATION_ID, 0) || '</Old><New>' 
        || nvl(:new.DESTINATION_ID, 0) || '</New></Field>';
    end if;
  
    if (:old.AREA_DESCRIPTION <> :new.AREA_DESCRIPTION) then
      changes := changes || '<Field><Name>AREA_DESCRIPTION</Name><Old>' 
        || nvl(rrtest.quote_xml(:old.AREA_DESCRIPTION), 'null') || '</Old><New>' 
        || nvl(rrtest.quote_xml(:new.AREA_DESCRIPTION), 'null') || '</New></Field>';
    end if;
  
    if (:old.USAGE_ID <> :new.USAGE_ID) then
      changes := changes || '<Field><Name>USAGE_ID</Name><Old>' 
        || nvl(:old.USAGE_ID, 0) || '</Old><New>' 
        || nvl(:new.USAGE_ID, 0) || '</New></Field>';
    end if;
  
    if (:old.ADDRESS <> :new.ADDRESS) then
      changes := changes || '<Field><Name>ADDRESS</Name><Old>' 
        || nvl(rrtest.quote_xml(:old.ADDRESS), 'null') || '</Old><New>' 
        || nvl(rrtest.quote_xml(:new.ADDRESS), 'null') || '</New></Field>';
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