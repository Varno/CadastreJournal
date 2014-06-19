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
begin

  if (p_facility_id is not null) then

    open p_cursor for
    select 
      p.FACILITY_HISTORY_ID
      , fh.MODIFIED_DATE
      , fh.MODIFIED_BY
      , fh.MODIFIED_BY_IP
      , fh.ACTION
      , fh.DESCRIPTION
    from 
      (select 
          FACILITY_HISTORY_ID
          , row_number() over (order by FACILITY_HISTORY_ID desc) as rnk
        from RRTEST.FACILITY_HISTORY
        where FACILITY_ID = p_facility_id
      ) p
      inner join RRTEST.FACILITY_HISTORY fh
        on p.FACILITY_HISTORY_ID = fh.FACILITY_HISTORY_ID
    where p.rnk > p_skip and p.rnk <= p_take;
  
    select count(*) into p_rowcount
    from RRTEST.FACILITY_HISTORY
    where FACILITY_ID = p_facility_id;

  else

    open p_cursor for
    select 
      FACILITY_HISTORY_ID
      , MODIFIED_DATE
      , MODIFIED_BY
      , MODIFIED_BY_IP
      , ACTION
      , DESCRIPTION
    from 
    (
      select 
        FACILITY_HISTORY_ID
        , MODIFIED_DATE
        , MODIFIED_BY
        , MODIFIED_BY_IP
        , ACTION
        , DESCRIPTION
        , row_number() over (order by FACILITY_HISTORY_ID desc) as rnk
      from RRTEST.FACILITY_HISTORY
    ) p
    where p.rnk > p_skip and p.rnk <= p_take;
  
    select count(*) into p_rowcount
    from RRTEST.FACILITY_HISTORY;
  
  end if;
  
end;
/