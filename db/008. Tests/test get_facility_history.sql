SET SERVEROUTPUT ON SIZE 1000000;

DECLARE
  l_cursor  SYS_REFCURSOR;
  l_id   RRTEST.FACILITY_HISTORY.FACILITY_HISTORY_ID%TYPE;
  l_md  RRTEST.FACILITY_HISTORY.MODIFIED_DATE%TYPE;
  l_mb  RRTEST.FACILITY_HISTORY.MODIFIED_BY%TYPE;
  l_mi  RRTEST.FACILITY_HISTORY.MODIFIED_BY_IP%TYPE;
  l_a  RRTEST.FACILITY_HISTORY.ACTION%TYPE;
  l_d  clob;--RRTEST.FACILITY_HISTORY.DESCRIPTION%TYPE;
  l_cnt number;
BEGIN

  RRTEST.GET_FACILITY_HISTORY
    (p_facility_id    => null,
    p_skip => 0,
    p_take => 20,
    p_cursor => l_cursor,
    p_rowcount => l_cnt);
            
  LOOP 
    FETCH l_cursor 
    INTO  l_id, l_md, l_mb, l_mi, l_a, l_d; -- xmltype in output ignored 
    EXIT WHEN l_cursor%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE(l_id || ', ' || l_md || ', ' || l_mb || ', ' || l_mi || ', ' || l_a );
--    DBMS_OUTPUT.PUT_LINE('xml: 
--' ||  l_d.getCLOBVal() );
    DBMS_OUTPUT.PUT_LINE('txt: 
' ||  l_d || '
');
  END LOOP;
  CLOSE l_cursor;
  
  DBMS_OUTPUT.PUT_LINE('row count: ' || l_cnt);
END;
/