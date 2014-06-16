create or replace 
procedure rrtest.update_facility_document
(
  p_document_id in RRTEST.FACILITY_DOCUMENTS.DOCUMENT_ID%TYPE
  , p_facility_id in RRTEST.FACILITY_DOCUMENTS.FACILITY_ID%TYPE
  , p_file_name in RRTEST.FACILITY_DOCUMENTS.FILE_NAME%TYPE
  , p_stored_path in RRTEST.FACILITY_DOCUMENTS.STORED_PATH%TYPE
  , p_user_name in RRTEST.FACILITY_DOCUMENTS.MODIFIED_BY%TYPE
  , p_user_ip in RRTEST.FACILITY_DOCUMENTS.MODIFIED_BY_IP%TYPE
  , p_inserted_id out RRTEST.FACILITY_DOCUMENTS.DOCUMENT_ID%TYPE
)
authid current_user
as
  l_sql_text long;
begin

  if (p_document_id is not null) then

    update rrtest.facility_documents
    set 
      FILE_NAME = p_file_name
      , STORED_PATH = p_stored_path
      , MODIFIED_DATE = sysdate
      , MODIFIED_BY = p_user_name
      , MODIFIED_BY_IP = p_user_ip
    where DOCUMENT_ID = p_document_id;
    
    p_inserted_id := p_document_id;
    
  else
    
    select rrtest.facility_document_seq.nextval into p_inserted_id from dual;
    
    insert into rrtest.facility_documents
      (DOCUMENT_ID
      , FACILITY_ID
      , FILE_NAME
      , STORED_PATH
      , CREATED_DATE
      , CREATED_BY
      , MODIFIED_DATE
      , MODIFIED_BY
      , MODIFIED_BY_IP)
    values
      (p_inserted_id
      , p_facility_id
      , p_file_name
      , p_stored_path
      , sysdate
      , p_user_name
      , sysdate
      , p_user_name
      , p_user_ip);
    
  end if;

  commit;

end;
/