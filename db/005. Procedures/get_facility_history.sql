create or replace 
procedure rrtest.get_facility_history
(
  p_facility_id in number
  , p_skip in number
  , p_take in number
  , p_cursor out SYS_REFCURSOR
  , p_rowcount out number
)
authid current_user
as
  temp_page rrtest.t_page;
  l_take number;
begin

  l_take := p_skip + p_take;

  if (p_facility_id is not null) then

    select rrtest.r_page(p.FACILITY_HISTORY_ID, p.rnk)
    bulk collect into temp_page
    from 
      (select 
        FACILITY_HISTORY_ID
        , row_number() over (order by FACILITY_HISTORY_ID desc) as rnk
       from RRTEST.FACILITY_HISTORY
       where FACILITY_ID = p_facility_id
    ) p
    where p.rnk > p_skip and p.rnk <= l_take;  

    select count(*) into p_rowcount
    from RRTEST.FACILITY_HISTORY
    where FACILITY_ID = p_facility_id;

  else

    select rrtest.r_page(p.FACILITY_HISTORY_ID, p.rnk)
    bulk collect into temp_page
    from 
      (select 
        FACILITY_HISTORY_ID
        , row_number() over (order by FACILITY_HISTORY_ID desc) as rnk
       from RRTEST.FACILITY_HISTORY
    ) p
    where p.rnk > p_skip and p.rnk <= l_take;
  
    select count(*) into p_rowcount
    from RRTEST.FACILITY_HISTORY;
  
  end if;

  open p_cursor for
  select 
    fh.FACILITY_HISTORY_ID
    , fh.MODIFIED_DATE
    , fh.MODIFIED_BY
    , fh.MODIFIED_BY_IP
    , fh.ACTION
    , fh.DESCRIPTION
  from 
    table(temp_page) tp
    inner join RRTEST.FACILITY_HISTORY fh
      on TP.record_id = FH.FACILITY_HISTORY_ID
  order by tp.record_order ;
  
end;
/