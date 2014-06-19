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
      , MODIFIED_DATE
    from RRTEST.FACILITIES
    where FACILITY_ID = p_facility_id;
    
    select count(*) into p_rowcount
    from RRTEST.FACILITIES
    where FACILITY_ID = p_facility_id;
    
  elsif (p_search_string is not null) then

    l_search_string := replace(replace(p_search_string, '%', '|%'), '_', '|_') || '%';
    
    open p_cursor for
    select
      x.FACILITY_ID
      , x.CADASTRAL_NUMBER
      , f.SQUARE
      , f.DESTINATION_ID
      , (substr(f.AREA_DESCRIPTION, 1, 256) || case when length(f.AREA_DESCRIPTION) > 256 then '...' else '' end) AREA_DESCRIPTION
      , f.USAGE_ID
      , f.ADDRESS
      , f.MODIFIED_DATE
    from 
      (select 
          p.FACILITY_ID
          , p.CADASTRAL_NUMBER
          , row_number() over (order by p.CADASTRAL_NUMBER asc) as rnk
        from
          (select 
            FACILITY_ID
            , CADASTRAL_NUMBER
          from RRTEST.FACILITIES
          where rownum <= 1000
            and CADASTRAL_NUMBER like l_search_string escape '|'
          union 
          select 
            FACILITY_ID
            , CADASTRAL_NUMBER
          from RRTEST.FACILITIES
          where rownum <= 1000
            and contains(SEARCH_KEY, p_search_string) > 0 
          order by CADASTRAL_NUMBER
          ) p
      ) x
      inner join RRTEST.FACILITIES f
        on x.FACILITY_ID = F.FACILITY_ID
    where
      x.rnk > p_skip and x.rnk <= p_take;   

    select count(*) into p_rowcount
    from
    (
      select rowid
      from RRTEST.FACILITIES
      where CADASTRAL_NUMBER like l_search_string escape '|'
      union
      select rowid
      from RRTEST.FACILITIES
      where contains(SEARCH_KEY, p_search_string) > 0
    );
  
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
      , row_number() over (order by CADASTRAL_NUMBER asc) as rnk
    from RRTEST.FACILITIES
    where rownum <= 1000
    ) p
    where p.rnk > p_skip and p.rnk <= p_take;

    select count(*) into p_rowcount
    from RRTEST.FACILITIES;
  
  end if;

end;
/