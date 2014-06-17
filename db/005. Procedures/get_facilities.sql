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
  l_search_string nvarchar2(32767);
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
    from RRTEST.FACILITIES
    where FACILITY_ID = p_facility_id;
    
    select count(*) into p_rowcount
    from RRTEST.FACILITIES
    where FACILITY_ID = p_facility_id;
    
  elsif (p_search_string is not null) then

    l_search_string := '%' || 
      replace(replace(upper(p_search_string), '_', '|_'), '%', '|%') || '%';

    open p_cursor for
    select 
      FACILITY_ID
      , CADASTRAL_NUMBER
      , SQUARE
      , DESTINATION_ID
      , AREA_DESCRIPTION
      , USAGE_ID
      , ADDRESS       
    from 
    (
    select 
      FACILITY_ID
      , CADASTRAL_NUMBER
      , SQUARE
      , DESTINATION_ID
      , AREA_DESCRIPTION
      , USAGE_ID
      , ADDRESS    
      , rank() over (order by CADASTRAL_NUMBER asc) as rnk
    from RRTEST.FACILITIES
    where 
      upper(CADASTRAL_NUMBER) like l_search_string escape '|'
      or upper(AREA_DESCRIPTION) like l_search_string escape '|'
      or upper(ADDRESS) like l_search_string escape '|'
      or DESTINATION_ID in (select d.DESTINATION_ID from RRTEST.DESTINATIONS d 
        where upper(d.DESCRIPTION) like l_search_string escape '|')
      or USAGE_ID in (select u.USAGE_ID from RRTEST.USAGES u
        where upper(u.DESCRIPTION) like l_search_string escape '|')
    ) p
    where p.rnk > p_skip and p.rnk <= p_take;

    select count(*) into p_rowcount
    from RRTEST.FACILITIES
    where 
      upper(CADASTRAL_NUMBER) like l_search_string escape '|'
      or upper(AREA_DESCRIPTION) like l_search_string escape '|'
      or upper(ADDRESS) like l_search_string escape '|'
      or DESTINATION_ID in (select d.DESTINATION_ID from RRTEST.DESTINATIONS d 
        where upper(d.DESCRIPTION) like l_search_string escape '|')
      or USAGE_ID in (select u.USAGE_ID from RRTEST.USAGES u
        where upper(u.DESCRIPTION) like l_search_string escape '|');
  
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
    from 
    (
    select 
      FACILITY_ID
      , CADASTRAL_NUMBER
      , SQUARE
      , DESTINATION_ID
      , AREA_DESCRIPTION
      , USAGE_ID
      , ADDRESS    
      , rank() over (order by FACILITY_ID asc) as rnk
    from RRTEST.FACILITIES
    ) p
    where p.rnk > p_skip and p.rnk <= p_take;

    select count(*) into p_rowcount
    from RRTEST.FACILITIES;
  
  end if;

end;
/