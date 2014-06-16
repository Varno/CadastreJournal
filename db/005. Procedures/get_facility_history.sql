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
      , rank() over (order by FACILITY_HISTORY_ID asc) as rnk
    from RRTEST.FACILITY_HISTORY
    where FACILITY_ID = p_facility_id
  ) p
  where p.rnk > p_skip and p.rnk <= p_take;

  select count(*) into p_rowcount
  from RRTEST.FACILITY_HISTORY
  where FACILITY_ID = p_facility_id;
  
end;
/