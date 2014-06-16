SET SERVEROUTPUT ON SIZE 1000000;

DECLARE
  p_inserted_id  RRTEST.FACILITIES.FACILITY_ID%TYPE;
BEGIN
  RRTEST.delete_facility_document
    (
    p_document_id => 2,
    p_user_name => 'test_6',
    p_user_ip => '::6'
    );
            
END;
/

