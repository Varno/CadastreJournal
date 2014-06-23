-- =============================================
-- v.1.0: Created by Pavel Kuznetsov 20/06/2014
-- return properties of facility
-- when id is not null -> return by id
-- else used param p_search_string 
-- =============================================
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
  temp_page rrtest.t_page;
  l_take number;
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
    
  else 
  
    l_take := p_skip + p_take;

    if (p_search_string is not null) then

      l_search_string := replace(replace(p_search_string, '%', '|%'), '_', '|_') || '%';

      select rrtest.r_page(p.FACILITY_ID, p.rnk)
      bulk collect into temp_page
      from 
         (select 
            FACILITY_ID
            , row_number() over (order by CADASTRAL_NUMBER asc) as rnk
          from RRTEST.FACILITIES
          where CADASTRAL_NUMBER like l_search_string escape '|'
            and rownum < 1000
          ) p
      where p.rnk > p_skip and p.rnk <= l_take;
      
      if (temp_page.count = 0) then

        select rrtest.r_page(p.FACILITY_ID, p.rnk)
        bulk collect into temp_page
        from 
            (select 
              FACILITY_ID
              , row_number() over (order by CADASTRAL_NUMBER asc) as rnk
            from RRTEST.FACILITIES
            where contains(SEARCH_KEY, p_search_string) > 0 
              and rownum < 1000
            ) p
        where p.rnk > p_skip and p.rnk <= l_take;
      
      end if;

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

      select rrtest.r_page(p.FACILITY_ID, p.rnk)
      bulk collect into temp_page
      from 
        (select
          FACILITY_ID
          , row_number() over (order by CADASTRAL_NUMBER asc) as rnk
        from RRTEST.FACILITIES
        where rownum <= 1000
        ) p
      where p.rnk > p_skip and p.rnk <= l_take;      
  
      select count(*) into p_rowcount
      from RRTEST.FACILITIES;

    end if;

    if (p_rowcount > 1000) then
      p_rowcount := 1000;
    end if;

    open p_cursor for
    select 
      f.FACILITY_ID
      , f.CADASTRAL_NUMBER
      , f.SQUARE
      , f.DESTINATION_ID
      , (substr(f.AREA_DESCRIPTION, 1, 256) || case when length(f.AREA_DESCRIPTION) > 256 then '...' else '' end) AREA_DESCRIPTION
      , f.USAGE_ID
      , f.ADDRESS
      , f.MODIFIED_DATE
    from 
      table(temp_page) tp
      inner join RRTEST.FACILITIES f
        on TP.record_id = f.FACILITY_ID
    order by tp.record_order ;
    
  end if;

end;
/