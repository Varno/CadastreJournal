SET SERVEROUTPUT ON SIZE 1000000;

DECLARE
  l_cursor  SYS_REFCURSOR;
  l_id   RRTEST.FACILITY_DOCUMENTS.DOCUMENT_ID%TYPE;
  l_n  RRTEST.FACILITY_DOCUMENTS.FILE_NAME%TYPE;
  l_p  RRTEST.FACILITY_DOCUMENTS.STORED_PATH%TYPE;
  l_cnt number;
BEGIN

  RRTEST.GET_FACILITY_DOCUMENTS
    (p_facility_id    => 123,
    p_skip => 0,
    p_take => 20,
    p_cursor => l_cursor,
    p_rowcount => l_cnt);
            
  LOOP 
    FETCH l_cursor 
    INTO  l_id, l_n, l_p;
    EXIT WHEN l_cursor%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE(l_id || ', ' || l_n || ', ' || l_p);
  END LOOP;
  CLOSE l_cursor;
  
  DBMS_OUTPUT.PUT_LINE('row count: ' || l_cnt);
END;
/