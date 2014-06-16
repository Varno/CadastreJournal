SET SERVEROUTPUT ON SIZE 1000000;

DECLARE
  p_facility_id  RRTEST.FACILITIES.FACILITY_ID%TYPE;
  p_cadastral_number  RRTEST.FACILITIES.CADASTRAL_NUMBER%TYPE;
  p_square  RRTEST.FACILITIES.SQUARE%TYPE;
  p_destination_id  RRTEST.FACILITIES.DESTINATION_ID%TYPE;
  p_area_description  RRTEST.FACILITIES.AREA_DESCRIPTION%TYPE;
  p_usage_id  RRTEST.FACILITIES.USAGE_ID%TYPE;
  p_address  RRTEST.FACILITIES.ADDRESS%TYPE;
  p_user_name  RRTEST.FACILITIES.MODIFIED_BY%TYPE;
  p_user_ip  RRTEST.FACILITIES.MODIFIED_BY_IP%TYPE;
  p_inserted_id  RRTEST.FACILITIES.FACILITY_ID%TYPE;
BEGIN
  RRTEST.update_facility
    (p_facility_id    => 1002,
    p_cadastral_number => trunc(dbms_random.value(11,99)) || ':' || trunc(dbms_random.value(11,99)) || ':' || trunc(dbms_random.value(100001,999999)) || ':' || '1234',
    p_square => 10.5,
    p_destination_id => 2,
    p_area_description => '2 sdkjhdskf kjdhfksjh sdkjhkdsjh',
    p_usage_id => 1,
    p_address => '2 df sdfdsf sdf d',
    p_user_name => 'test_3',
    p_user_ip => '::4',
    p_inserted_id => p_inserted_id);
            
    DBMS_OUTPUT.PUT_LINE(p_inserted_id);
END;
/

