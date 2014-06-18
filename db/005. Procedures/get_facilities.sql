create or replace 
procedure rrtest.get_facilities
(
  p_facility_id in number
  , p_search_string in nvarchar2
  , p_skip in number
  , p_take in number
  , p_cursor out SYS_REFCURSOR
  , p_rowcount out number
)
authid current_user
as
begin

  if (p_facility_id is not null) then
    
    open p_cursor for
    select 
      FACILITY_ID
      , CADASTRAL_NUMBER
      , SQUARE
      , DESTINATION_ID
      , AREA_DESCRIPTION
      , USAGE_ID
      , ADDRESS
      , MODIFIED_DATE
    from RRTEST.FACILITIES
    where FACILITY_ID = p_facility_id;
    
    select count(*) into p_rowcount
    from RRTEST.FACILITIES
    where FACILITY_ID = p_facility_id;
    
  elsif (p_search_string is not null) then

    open p_cursor for
    select 
      FACILITY_ID
      , CADASTRAL_NUMBER
      , SQUARE
      , DESTINATION_ID
      , AREA_DESCRIPTION
      , USAGE_ID
      , ADDRESS 
      , MODIFIED_DATE
    from 
    (
    select 
      FACILITY_ID
      , CADASTRAL_NUMBER
      , SQUARE
      , DESTINATION_ID
      , (substr(AREA_DESCRIPTION, 1, 256) || case when length(AREA_DESCRIPTION) > 256 then '...' else '' end) AREA_DESCRIPTION
      , USAGE_ID
      , ADDRESS
      , MODIFIED_DATE
      , rank() over (order by CADASTRAL_NUMBER asc) as rnk
    from RRTEST.FACILITIES
    where 
      contains(SEARCH_KEY, p_search_string) > 0
    ) p
    where p.rnk > p_skip and p.rnk <= p_take;

    select count(*) into p_rowcount
    from RRTEST.FACILITIES
    where 
      contains(SEARCH_KEY, p_search_string) > 0;
  
  else
  
    open p_cursor for
    select 
      FACILITY_ID
      , CADASTRAL_NUMBER
      , SQUARE
      , DESTINATION_ID
      , AREA_DESCRIPTION
      , USAGE_ID
      , ADDRESS
      , MODIFIED_DATE
    from 
    (
    select 
      FACILITY_ID
      , CADASTRAL_NUMBER
      , SQUARE
      , DESTINATION_ID
      , (substr(AREA_DESCRIPTION, 1, 256) || case when length(AREA_DESCRIPTION) > 256 then '...' else '' end) AREA_DESCRIPTION
      , USAGE_ID
      , ADDRESS 
      , MODIFIED_DATE
      , rank() over (order by CADASTRAL_NUMBER asc) as rnk
    from RRTEST.FACILITIES
    ) p
    where p.rnk > p_skip and p.rnk <= p_take;

    select count(*) into p_rowcount
    from RRTEST.FACILITIES;
  
  end if;

end;
/