create or replace 
procedure rrtest.get_facility_documents
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
    DOCUMENT_ID
    , FILE_NAME
    , STORED_PATH      
  from 
  (
    select 
      DOCUMENT_ID
      , FILE_NAME
      , STORED_PATH      
      , row_number() over (order by DOCUMENT_ID asc) as rnk
    from RRTEST.FACILITY_DOCUMENTS
    where FACILITY_ID = p_facility_id
  ) p
  where p.rnk > p_skip and p.rnk <= p_take;
  
  select count(*) into p_rowcount
  from RRTEST.FACILITY_DOCUMENTS
  where FACILITY_ID = p_facility_id;
   
end;
/