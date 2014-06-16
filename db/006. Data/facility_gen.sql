begin

for i in 1..10
loop

  insert into rrtest.facilities
  (FACILITY_ID
  , CADASTRAL_NUMBER
  , SQUARE
  , DESTINATION_ID
  , AREA_DESCRIPTION
  , USAGE_ID
  , ADDRESS
  , CREATED_DATE
  , CREATED_BY
  , MODIFIED_DATE
  , MODIFIED_BY
  , MODIFIED_BY_IP)
  select 
    rrtest.facility_seq.nextval
    , trunc(dbms_random.value(11,99)) || ':' || trunc(dbms_random.value(11,99)) || ':' || trunc(dbms_random.value(100001,999999)) || ':' || level
    , trunc(dbms_random.value(1,9999), 2)
    , trunc(dbms_random.value(1,5))
    , dbms_random.string('p', dbms_random.value(100, 1000))
    , trunc(dbms_random.value(1,2))
    , dbms_random.string('p', dbms_random.value(100, 200))
    , sysdate
    , 'test'
    , sysdate
    , 'test'
    , '123'
  from
    dual
  connect by 
    level <= 100;

  commit;

end loop;

end;
