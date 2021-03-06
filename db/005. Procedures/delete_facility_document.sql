-- =============================================
-- v.1.0: Created by Pavel Kuznetsov 20/06/2014
-- delete facility doc
-- =============================================
create or replace 
procedure rrtest.delete_facility_document
(
  p_document_id in RRTEST.FACILITY_DOCUMENTS.DOCUMENT_ID%TYPE
  , p_user_name in RRTEST.FACILITY_DOCUMENTS.MODIFIED_BY%TYPE
  , p_user_ip in RRTEST.FACILITY_DOCUMENTS.MODIFIED_BY_IP%TYPE
)
authid current_user
as
begin

  savepoint begin_delete_facility_doc;

  update rrtest.facility_documents
  set 
    MODIFIED_DATE = sysdate
    , MODIFIED_BY = p_user_name
    , MODIFIED_BY_IP = p_user_ip
  where DOCUMENT_ID = p_document_id;

  delete from rrtest.facility_documents
  where DOCUMENT_ID = p_document_id;

  commit;

  exception
    when others then rollback to begin_delete_facility_doc;
    raise;

end;
/