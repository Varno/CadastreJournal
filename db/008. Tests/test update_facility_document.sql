SET SERVEROUTPUT ON SIZE 1000000;

DECLARE
  p_inserted_id  RRTEST.FACILITIES.FACILITY_ID%TYPE;
BEGIN
  RRTEST.update_facility_document
    (
    p_document_id => null,
    p_facility_id    => 123,
    p_file_name => dbms_random.string('x', dbms_random.value(10, 20)) || '.doc',
    p_stored_path => dbms_random.string('x', dbms_random.value(10, 50)) || '\' || dbms_random.string('x', dbms_random.value(10, 50)),
    p_user_name => 'test_5',
    p_user_ip => '::4',
    p_inserted_id => p_inserted_id);
            
    DBMS_OUTPUT.PUT_LINE(p_inserted_id);
END;
/

