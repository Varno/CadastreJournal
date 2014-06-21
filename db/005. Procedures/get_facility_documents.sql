-- =============================================
-- v.1.0: Created by Pavel Kuznetsov 20/06/2014
-- return documents for facility
-- =============================================
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
  temp_page rrtest.t_page;
  l_take number;
begin

  l_take := p_skip + p_take;

  select rrtest.r_page(p.DOCUMENT_ID, p.rnk)
  bulk collect into temp_page
  from 
    (select 
      DOCUMENT_ID
      , row_number() over (order by DOCUMENT_ID asc) as rnk
    from RRTEST.FACILITY_DOCUMENTS
    where FACILITY_ID = p_facility_id
    ) p
  where p.rnk > p_skip and p.rnk <= l_take;  

  select count(*) into p_rowcount
  from RRTEST.FACILITY_DOCUMENTS
  where FACILITY_ID = p_facility_id;

  open p_cursor for
  select 
    fd.DOCUMENT_ID
    , fd.FILE_NAME
    , fd.STORED_PATH      
  from 
    table(temp_page) tp
    inner join RRTEST.FACILITY_DOCUMENTS fd
      on TP.record_id = fd.DOCUMENT_ID
  order by tp.record_order ;
   
end;
/