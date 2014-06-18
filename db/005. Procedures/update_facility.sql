create or replace 
procedure rrtest.update_facility
(
  p_facility_id in RRTEST.FACILITIES.FACILITY_ID%TYPE
  , p_cadastral_number in RRTEST.FACILITIES.CADASTRAL_NUMBER%TYPE
  , p_square in RRTEST.FACILITIES.SQUARE%TYPE
  , p_destination_id in RRTEST.FACILITIES.DESTINATION_ID%TYPE
  , p_area_description in RRTEST.FACILITIES.AREA_DESCRIPTION%TYPE
  , p_usage_id in RRTEST.FACILITIES.USAGE_ID%TYPE
  , p_address in RRTEST.FACILITIES.ADDRESS%TYPE
  , p_user_name in RRTEST.FACILITIES.MODIFIED_BY%TYPE
  , p_user_ip in RRTEST.FACILITIES.MODIFIED_BY_IP%TYPE
  , p_inserted_id out RRTEST.FACILITIES.FACILITY_ID%TYPE
)
authid current_user
as
  l_search_key clob;
  l_destination RRTEST.DESTINATIONS.DESCRIPTION%TYPE;
  l_usage RRTEST.USAGES.DESCRIPTION%TYPE;
begin

  select DESCRIPTION into l_destination
  from rrtest.destinations
  where destination_id = p_destination_id;
  
  select DESCRIPTION into l_usage
  from rrtest.usages
  where usage_id = p_usage_id;
  
  l_search_key := nvl(p_cadastral_number, ' ') || ' ' 
    || nvl(l_destination, ' ') || ' '
    || nvl(l_usage, ' ') || ' ' 
    || nvl(p_area_description, ' ') || ' '
    || nvl(p_address, ' ');

  if (p_facility_id is not null) then

    update rrtest.facilities
    set 
      SQUARE = p_square
      , DESTINATION_ID = p_destination_id
      , AREA_DESCRIPTION = p_area_description
      , USAGE_ID = p_usage_id
      , ADDRESS = p_address
      , SEARCH_KEY = l_search_key
      , MODIFIED_DATE = sysdate
      , MODIFIED_BY = p_user_name
      , MODIFIED_BY_IP = p_user_ip
    where FACILITY_ID = p_facility_id;
    
    p_inserted_id := p_facility_id;
    
  else
    
    select rrtest.facility_seq.nextval into p_inserted_id from dual;
    
    insert into rrtest.facilities
      (FACILITY_ID
      , CADASTRAL_NUMBER
      , SQUARE
      , DESTINATION_ID
      , AREA_DESCRIPTION
      , USAGE_ID
      , ADDRESS
      , SEARCH_KEY
      , CREATED_DATE
      , CREATED_BY
      , MODIFIED_DATE
      , MODIFIED_BY
      , MODIFIED_BY_IP)
    values
      (p_inserted_id
      , p_cadastral_number
      , p_square
      , p_destination_id
      , p_area_description
      , p_usage_id
      , p_address
      , l_search_key
      , sysdate
      , p_user_name
      , sysdate
      , p_user_name
      , p_user_ip);
    
  end if;

  commit;

end;
/