create or replace 
procedure rrtest.get_facilities
(
  p_facility_id in number
  , p_cadastral_number in varchar2
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
    from RRTEST.FACILITIES
    where FACILITY_ID = p_facility_id;
    
    select count(*) into p_rowcount
    from RRTEST.FACILITIES
    where FACILITY_ID = p_facility_id;
    
  elsif (p_cadastral_number is not null) then

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
    where CADASTRAL_NUMBER like p_cadastral_number || '%'
    ) p
    where p.rnk > p_skip and p.rnk <= p_take;

    select count(*) into p_rowcount
    from RRTEST.FACILITIES
    where CADASTRAL_NUMBER like p_cadastral_number || '%';
  
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