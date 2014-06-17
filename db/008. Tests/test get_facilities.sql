SET SERVEROUTPUT ON SIZE 1000000;

DECLARE
  l_cursor  SYS_REFCURSOR;
  l_id   RRTEST.FACILITIES.FACILITY_ID%TYPE;
  l_cn   RRTEST.FACILITIES.CADASTRAL_NUMBER%TYPE;
  l_sq  RRTEST.FACILITIES.SQUARE%TYPE;
  l_did  RRTEST.FACILITIES.DESTINATION_ID%TYPE;
  l_ad  RRTEST.FACILITIES.AREA_DESCRIPTION%TYPE;
  l_uid  RRTEST.FACILITIES.USAGE_ID%TYPE;
  l_a  RRTEST.FACILITIES.ADDRESS%TYPE;
  l_cnt number;
BEGIN
  RRTEST.GET_FACILITIES
    (p_facility_id    => null,
    p_search_string => 'ОТнюдь',
    p_skip => 0,
    p_take => 20,
    p_cursor => l_cursor,
    p_rowcount => l_cnt);

  LOOP 
    FETCH l_cursor 
    INTO  l_id, l_cn, l_sq, l_did, l_ad, l_uid, l_a;
    EXIT WHEN l_cursor%NOTFOUND;
    DBMS_OUTPUT.PUT_LINE(l_id || ', ' || l_cn || ', ' || l_sq || ', ' || l_did || ', ' || l_ad || ', ' || l_uid || ', ' || l_a);
  END LOOP;
  CLOSE l_cursor;
  
  DBMS_OUTPUT.PUT_LINE('row count: ' || l_cnt);
END;
/