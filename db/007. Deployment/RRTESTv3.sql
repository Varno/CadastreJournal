CREATE USER RRTEST IDENTIFIED BY work 
DEFAULT TABLESPACE "USERS"
TEMPORARY TABLESPACE "TEMP"
PASSWORD EXPIRE 
ACCOUNT LOCK ;

ALTER USER "RRTEST" QUOTA UNLIMITED ON USERS;

create table rrtest.destinations 
(
  destination_id int constraint destination_id_nn not null
  , description varchar2(100) constraint desctination_description_nn not null
  , constraint destination_pk primary key(destination_id)
);

create table rrtest.usages
(
  usage_id int constraint usage_id_nn not null
  , description nvarchar2(100) constraint usage_description_nn not null
  , constraint usage_pk primary key(usage_id)
);

create table rrtest.facilities
(
  facility_id number constraint facility_id_nn not null
  , cadastral_number varchar2(20) constraint facility_cadastral_number_nn not null
  , square number(*, 2)
  , destination_id int constraint facility_destination_id_nn not null
  , area_description nclob
  , usage_id int constraint facility_usage_id_nn not null
  , address nvarchar2(1024) 
  , created_date date constraint facility_created_date_nn not null
  , created_by nvarchar2(256) constraint facility_created_by_nn not null
  , modified_date date constraint facility_modified_date_nn not null
  , modified_by nvarchar2(256) constraint facility_modified_by_nn not null
  , modified_by_ip nvarchar2(256) constraint facility_modified_by_ip_nn not null
  , constraint facility_pk primary key(facility_id)
  , constraint facility_destination_fk foreign key(destination_id)
      references rrtest.destinations(destination_id)
  , constraint facility_usage_fk foreign key(usage_id)
      references rrtest.usages(usage_id)
  , constraint facility_cadastral_number_uk unique (cadastral_number)
);

create table rrtest.facility_documents
(
  document_id number constraint facility_doc_id_nn not null
  , facility_id number constraint facility_doc_facil_id_nn not null
  , file_name nvarchar2(100) constraint facility_doc_file_name_nn not null
  , stored_path nvarchar2(1024) constraint facility_doc_stored_path_nn not null
  , created_date date constraint facility_doc_created_date_nn not null
  , created_by nvarchar2(256) constraint facility_doc_created_by_nn not null
  , modified_date date constraint facility_doc_modified_date_nn not null
  , modified_by nvarchar2(256) constraint facility_doc_modified_by_nn not null
  , modified_by_ip nvarchar2(256) constraint facility_doc_modified_by_ip_nn not null
  , constraint facility_document_pk primary key(document_id)
  , constraint facility_document_facility_fk foreign key(facility_id)
      references rrtest.facilities(facility_id)
);

create table rrtest.facility_history
(
  facility_history_id number constraint facility_hist_id_nn not null
  , facility_id number constraint facility_hist_fac_id_nn not null
  , modified_date date constraint facility_hist_mod_date_nn not null
  , modified_by nvarchar2(256) constraint facility_hist_mod_by_nn not null
  , modified_by_ip nvarchar2(256) constraint facility_hist_mod_by_ip_nn not null
  , action varchar2(50) constraint facility_hist_action_nn not null
  , description xmltype constraint facility_hist_descr_nn not null
  , constraint facility_history_pk primary key(facility_history_id)
  , constraint facility_history_facility_fk foreign key(facility_id)
      references rrtest.facilities(facility_id)
);

create sequence rrtest.facility_seq start with 1 increment by 1;
create sequence rrtest.facility_document_seq start with 1 increment by 1;
create sequence rrtest.facility_history_seq start with 1 increment by 1;

-- comments
comment on table rrtest.destinations is '??????????: ?????????? ???????? ????????????';
comment on column rrtest.destinations.destination_id is '????????? ???? ??????? DESTINATIONS';
comment on column rrtest.destinations.description is '???????? ??????????. 100 ????????, NOT NULL';

comment on table rrtest.usages is '??????????: ??????????? ?????????????';
comment on column rrtest.usages.usage_id is '????????? ???? ??????? USAGES';
comment on column rrtest.usages.description is '???????? ?????????????. 100 ????????, NOT NULL';

comment on table rrtest.facilities is '??????? ????????????';
comment on column rrtest.facilities.facility_id is '????????? ???? ??????? FACILITIES';
comment on column rrtest.facilities.cadastral_number is '??????????? ?????. ??????????. 20 ????????, NOT NULL';
comment on column rrtest.facilities.square is '??????? ??????? ????????????. ???????? 2 ????? ????? ???????';
comment on column rrtest.facilities.destination_id is '??? ??????????. ??????? ???? ?? ??????? DESTINATIONS, NOT NULL';
comment on column rrtest.facilities.area_description is '???????? ??????????????';
comment on column rrtest.facilities.usage_id is '??? ?????????????. ??????? ???? ?? ??????? USAGES, NOT NULL';
comment on column rrtest.facilities.address is '????? ??????? ????????????, 1024 ???????';

comment on table rrtest.facility_documents is '????????? ???????????? ????';
comment on column rrtest.facility_documents.document_id is '????????? ???? ??????? FACILITY_DOCUMENTS';
comment on column rrtest.facility_documents.facility_id is '??? ??????? ????????????. ??????? ???? ?? ??????? FACILITIES, NOT NULL';
comment on column rrtest.facility_documents.stored_path is '???? ?? ?????. 1024 ???????, NOT NULL';
/

create or replace FUNCTION rrtest.quote_xml 
(
  input_srt in nclob
)
    RETURN nclob IS
begin
  return 
    replace(replace(replace(replace(replace(input_srt
      , '&', '&'||'amp;')
      , '<', '&'||'lt;')
      , '>', '&'||'gt;')
      , '"', '&'||'quot;')
      , '''', '&'||'apos;');
end;
/

create or replace trigger rrtest.add_facility_history
after insert or update or delete on rrtest.facilities
for each row
declare
  changes nclob;
  descr xmltype;
  action RRTEST.FACILITY_HISTORY.ACTION%TYPE;
  facility_id  RRTEST.FACILITY_HISTORY.FACILITY_ID%TYPE;
  modified_by RRTEST.FACILITY_HISTORY.MODIFIED_BY%TYPE;
  modified_by_ip  RRTEST.FACILITY_HISTORY.MODIFIED_BY_IP%TYPE;
begin

  changes := '';
  
  if (:old.FACILITY_ID is null) then
    
    action := 'create facility';
    facility_id := :new.FACILITY_ID;
    modified_by := :new.MODIFIED_BY;
    modified_by_ip := :new.MODIFIED_BY_IP;
    
    changes := '<Field><Name>CADASTRAL_NUMBER</Name><Old>null</Old><New>' 
      || nvl(rrtest.quote_xml(:new.CADASTRAL_NUMBER), 'null') || '</New></Field>'
      || '<Field><Name>SQUARE</Name><Old>null</Old><New>' 
      || nvl(:new.SQUARE, 0) || '</New></Field>'
      || '<Field><Name>DESTINATION_ID</Name><Old>null</Old><New>' 
      || nvl(:new.DESTINATION_ID, 0) || '</New></Field>'
      || '<Field><Name>AREA_DESCRIPTION</Name><Old>null</Old><New>' 
      || nvl(rrtest.quote_xml(:new.AREA_DESCRIPTION), 'null') || '</New></Field>'
      || '<Field><Name>USAGE_ID</Name><Old>null</Old><New>' 
      || nvl(:new.USAGE_ID, 0) || '</New></Field>'
      || '<Field><Name>ADDRESS</Name><Old>null</Old><New>' 
      || nvl(rrtest.quote_xml(:new.ADDRESS), 'null') || '</New></Field>';
    
  elsif (:new.FACILITY_ID is null) then
    
    action := 'delete facility';
    facility_id := :old.FACILITY_ID;
    modified_by := :old.MODIFIED_BY;
    modified_by_ip := :old.MODIFIED_BY_IP;

    changes := '<Field><Name>CADASTRAL_NUMBER</Name><Old>' 
      || nvl(rrtest.quote_xml(:old.CADASTRAL_NUMBER), 'null') 
      || '</Old><New>null</New></Field>'
      || '<Field><Name>SQUARE</Name><Old>' || nvl(:old.SQUARE, 0) 
      || '</Old><New>null</New></Field>'
      || '<Field><Name>DESTINATION_ID</Name><Old>' 
      || nvl(:old.DESTINATION_ID, 0) || '</Old><New>null</New></Field>'
      || '<Field><Name>AREA_DESCRIPTION</Name><Old>' 
      || nvl(rrtest.quote_xml(:old.AREA_DESCRIPTION), 'null') 
      || '</Old><New>null</New></Field>'
      || '<Field><Name>USAGE_ID</Name><Old>' 
      || nvl(:old.USAGE_ID, 0) || '</Old><New>null</New></Field>'
      || '<Field><Name>ADDRESS</Name><Old>' 
      || nvl(rrtest.quote_xml(:old.ADDRESS), 'null') 
      || '</Old><New>null</New></Field>';
    
  else
    
    action := 'update facility';
    facility_id := :new.FACILITY_ID;
    modified_by := :new.MODIFIED_BY;
    modified_by_ip := :new.MODIFIED_BY_IP;
    
    if (:old.CADASTRAL_NUMBER <> :new.CADASTRAL_NUMBER) then
      changes := changes || '<Field><Name>CADASTRAL_NUMBER</Name><Old>' 
        || nvl(rrtest.quote_xml(:old.CADASTRAL_NUMBER), 'null') || '</Old><New>' 
        || nvl(rrtest.quote_xml(:new.CADASTRAL_NUMBER), 'null') || '</New></Field>';
    end if;
  
    if (:old.SQUARE <> :new.SQUARE) then
      changes := changes || '<Field><Name>SQUARE</Name><Old>' 
        || nvl(:old.SQUARE, 0) || '</Old><New>' 
        || nvl(:new.SQUARE, 0) || '</New></Field>';
    end if;
  
    if (:old.DESTINATION_ID <> :new.DESTINATION_ID) then
      changes := changes || '<Field><Name>DESTINATION_ID</Name><Old>' 
        || nvl(:old.DESTINATION_ID, 0) || '</Old><New>' 
        || nvl(:new.DESTINATION_ID, 0) || '</New></Field>';
    end if;
  
    if (:old.AREA_DESCRIPTION <> :new.AREA_DESCRIPTION) then
      changes := changes || '<Field><Name>AREA_DESCRIPTION</Name><Old>' 
        || nvl(rrtest.quote_xml(:old.AREA_DESCRIPTION), 'null') || '</Old><New>' 
        || nvl(rrtest.quote_xml(:new.AREA_DESCRIPTION), 'null') || '</New></Field>';
    end if;
  
    if (:old.USAGE_ID <> :new.USAGE_ID) then
      changes := changes || '<Field><Name>USAGE_ID</Name><Old>' 
        || nvl(:old.USAGE_ID, 0) || '</Old><New>' 
        || nvl(:new.USAGE_ID, 0) || '</New></Field>';
    end if;
  
    if (:old.ADDRESS <> :new.ADDRESS) then
      changes := changes || '<Field><Name>ADDRESS</Name><Old>' 
        || nvl(rrtest.quote_xml(:old.ADDRESS), 'null') || '</Old><New>' 
        || nvl(rrtest.quote_xml(:new.ADDRESS), 'null') || '</New></Field>';
    end if;
    
  end if;

  if (length(changes) > 0) then

    descr := xmltype('<Fields>'||changes||'</Fields>');

    insert into rrtest.facility_history
    values
      (rrtest.facility_history_seq.nextval
      , facility_id
      , sysdate
      , modified_by
      , modified_by_ip
      , action
      , descr);
  
  end if;

end;
/

create or replace trigger rrtest.add_facility_document_history
after insert or update or delete on rrtest.facility_documents
for each row
declare
  changes nclob;
  descr xmltype;
  action RRTEST.FACILITY_HISTORY.ACTION%TYPE;
  facility_id  RRTEST.FACILITY_HISTORY.FACILITY_ID%TYPE;
  modified_by RRTEST.FACILITY_HISTORY.MODIFIED_BY%TYPE;
  modified_by_ip  RRTEST.FACILITY_HISTORY.MODIFIED_BY_IP%TYPE;
begin

  changes := '';
  
  if (:old.DOCUMENT_ID is null) then
    
    action := 'create document';
    facility_id := :new.FACILITY_ID;
    modified_by := :new.MODIFIED_BY;
    modified_by_ip := :new.MODIFIED_BY_IP;

    changes := '<Field><Name>DOCUMENT_ID</Name><Old>null</Old><New>' 
      || nvl(:new.DOCUMENT_ID, 0) || '</New></Field>'
      || '<Field><Name>FILE_NAME</Name><Old>null</Old><New>' 
      || nvl(rrtest.quote_xml(:new.FILE_NAME), 'null') || '</New></Field>'
      || '<Field><Name>STORED_PATH</Name><Old>null</Old><New>' 
      || nvl(rrtest.quote_xml(:new.STORED_PATH), 'null') || '</New></Field>';
    
  elsif (:new.DOCUMENT_ID is null) then
    
    action := 'delete document';
    facility_id := :old.FACILITY_ID;
    modified_by := :old.MODIFIED_BY;
    modified_by_ip := :old.MODIFIED_BY_IP;

    changes := '<Field><Name>DOCUMENT_ID</Name><Old>' 
      || nvl(:old.DOCUMENT_ID, 0) 
      || '</Old><New>null</New></Field>'
      || '<Field><Name>FILE_NAME</Name><Old>' 
      || nvl(rrtest.quote_xml(:old.FILE_NAME), 'null') 
      || '</Old><New>null</New></Field>'
      || '<Field><Name>STORED_PATH</Name><Old>' 
      || nvl(rrtest.quote_xml(:old.STORED_PATH), 'null') 
      || '</Old><New>null</New></Field>';
    
  else
    
    action := 'update document';
    facility_id := :new.FACILITY_ID;
    modified_by := :new.MODIFIED_BY;
    modified_by_ip := :new.MODIFIED_BY_IP;

    if (:old.FILE_NAME <> :new.FILE_NAME or :old.STORED_PATH <> :new.STORED_PATH) then
      changes := changes || '<Field><Name>DOCUMENT_ID</Name><Old>' 
        || nvl(:old.DOCUMENT_ID, 0) || '</Old><New>' 
        || nvl(:new.DOCUMENT_ID, 0) || '</New></Field>';
    end if;
    
    if (:old.FILE_NAME <> :new.FILE_NAME) then
      changes := changes || '<Field><Name>FILE_NAME</Name><Old>' 
        || nvl(rrtest.quote_xml(:old.FILE_NAME), 'null') || '</Old><New>' 
        || nvl(rrtest.quote_xml(:new.FILE_NAME), 'null') || '</New></Field>';
    end if;
  
    if (:old.STORED_PATH <> :new.STORED_PATH) then
      changes := changes || '<Field><Name>STORED_PATH</Name><Old>' 
        || nvl(rrtest.quote_xml(:old.STORED_PATH), 'null') || '</Old><New>' 
        || nvl(rrtest.quote_xml(:new.STORED_PATH), 'null') || '</New></Field>';
    end if;
  
  end if;

  if (length(changes) > 0) then

    descr := xmltype('<Fields>'||changes||'</Fields>');

    insert into rrtest.facility_history
    values
      (rrtest.facility_history_seq.nextval
      , facility_id
      , sysdate
      , modified_by
      , modified_by_ip
      , action
      , descr);
  
  end if;

end;
/
create or replace 
procedure rrtest.get_facilities
(
  p_facility_id in number
  , p_cadastral_number in varchar2
  , p_skip in number
  , p_take in number
  , p_cursor out SYS_REFCURSOR
  , p_rowcount out number
)
authid current_user
as
begin

  if (p_facility_id is not null) then
    
    open p_cursor for
    select 
      FACILITY_ID
      , CADASTRAL_NUMBER
      , SQUARE
      , DESTINATION_ID
      , AREA_DESCRIPTION
      , USAGE_ID
      , ADDRESS    
    from RRTEST.FACILITIES
    where FACILITY_ID = p_facility_id;
    
    select count(*) into p_rowcount
    from RRTEST.FACILITIES
    where FACILITY_ID = p_facility_id;
    
  elsif (p_cadastral_number is not null) then

    open p_cursor for
    select 
      FACILITY_ID
      , CADASTRAL_NUMBER
      , SQUARE
      , DESTINATION_ID
      , AREA_DESCRIPTION
      , USAGE_ID
      , ADDRESS       
    from 
    (
    select 
      FACILITY_ID
      , CADASTRAL_NUMBER
      , SQUARE
      , DESTINATION_ID
      , AREA_DESCRIPTION
      , USAGE_ID
      , ADDRESS    
      , rank() over (order by CADASTRAL_NUMBER asc) as rnk
    from RRTEST.FACILITIES
    where CADASTRAL_NUMBER like p_cadastral_number || '%'
    ) p
    where p.rnk > p_skip and p.rnk <= p_take;

    select count(*) into p_rowcount
    from RRTEST.FACILITIES
    where CADASTRAL_NUMBER like p_cadastral_number || '%';
  
  else
  
    open p_cursor for
    select 
      FACILITY_ID
      , CADASTRAL_NUMBER
      , SQUARE
      , DESTINATION_ID
      , AREA_DESCRIPTION
      , USAGE_ID
      , ADDRESS       
    from 
    (
    select 
      FACILITY_ID
      , CADASTRAL_NUMBER
      , SQUARE
      , DESTINATION_ID
      , AREA_DESCRIPTION
      , USAGE_ID
      , ADDRESS    
      , rank() over (order by FACILITY_ID asc) as rnk
    from RRTEST.FACILITIES
    ) p
    where p.rnk > p_skip and p.rnk <= p_take;

    select count(*) into p_rowcount
    from RRTEST.FACILITIES;
  
  end if;

end;
/

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
begin

  open p_cursor for
  select 
    DOCUMENT_ID
    , FILE_NAME
    , STORED_PATH      
  from 
  (
    select 
      DOCUMENT_ID
      , FILE_NAME
      , STORED_PATH      
      , rank() over (order by DOCUMENT_ID asc) as rnk
    from RRTEST.FACILITY_DOCUMENTS
    where FACILITY_ID = p_facility_id
  ) p
  where p.rnk > p_skip and p.rnk <= p_take;
  
  select count(*) into p_rowcount
  from RRTEST.FACILITY_DOCUMENTS
  where FACILITY_ID = p_facility_id;
   
end;
/

create or replace 
procedure rrtest.get_facility_history
(
  p_facility_id in number
  , p_skip in number
  , p_take in number
  , p_cursor out SYS_REFCURSOR
  , p_rowcount out number
)
authid current_user
as
begin

  open p_cursor for
  select 
    FACILITY_HISTORY_ID
    , MODIFIED_DATE
    , MODIFIED_BY
    , MODIFIED_BY_IP
    , ACTION
    , DESCRIPTION
  from 
  (
    select 
      FACILITY_HISTORY_ID
      , MODIFIED_DATE
      , MODIFIED_BY
      , MODIFIED_BY_IP
      , ACTION
      , DESCRIPTION
      , rank() over (order by FACILITY_HISTORY_ID asc) as rnk
    from RRTEST.FACILITY_HISTORY
    where FACILITY_ID = p_facility_id
  ) p
  where p.rnk > p_skip and p.rnk <= p_take;

  select count(*) into p_rowcount
  from RRTEST.FACILITY_HISTORY
  where FACILITY_ID = p_facility_id;
  
end;
/

create or replace 
procedure rrtest.update_facility
(
  p_facility_id in RRTEST.FACILITIES.FACILITY_ID%TYPE
  , p_cadastral_number in RRTEST.FACILITIES.CADASTRAL_NUMBER%TYPE
  , p_square in RRTEST.FACILITIES.SQUARE%TYPE
  , p_destination_id in RRTEST.FACILITIES.DESTINATION_ID%TYPE
  , p_area_description in RRTEST.FACILITIES.AREA_DESCRIPTION%TYPE
  , p_usage_id in RRTEST.FACILITIES.USAGE_ID%TYPE
  , p_address in RRTEST.FACILITIES.ADDRESS%TYPE
  , p_user_name in RRTEST.FACILITIES.MODIFIED_BY%TYPE
  , p_user_ip in RRTEST.FACILITIES.MODIFIED_BY_IP%TYPE
  , p_inserted_id out RRTEST.FACILITIES.FACILITY_ID%TYPE
)
authid current_user
as
  l_sql_text long;
begin

  if (p_facility_id is not null) then

    update rrtest.facilities
    set 
      SQUARE = p_square
      , DESTINATION_ID = p_destination_id
      , AREA_DESCRIPTION = p_area_description
      , USAGE_ID = p_usage_id
      , ADDRESS = p_address
      , MODIFIED_DATE = sysdate
      , MODIFIED_BY = p_user_name
      , MODIFIED_BY_IP = p_user_ip
    where FACILITY_ID = p_facility_id;
    
    p_inserted_id := p_facility_id;
    
  else
    
    select rrtest.facility_seq.nextval into p_inserted_id from dual;
    
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
    values
      (p_inserted_id
      , p_cadastral_number
      , p_square
      , p_destination_id
      , p_area_description
      , p_usage_id
      , p_address
      , sysdate
      , p_user_name
      , sysdate
      , p_user_name
      , p_user_ip);
    
  end if;

  commit;

end;
/

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

create or replace 
procedure rrtest.delete_facility_document
(
  p_document_id in RRTEST.FACILITY_DOCUMENTS.DOCUMENT_ID%TYPE
  , p_user_name in RRTEST.FACILITY_DOCUMENTS.MODIFIED_BY%TYPE
  , p_user_ip in RRTEST.FACILITY_DOCUMENTS.MODIFIED_BY_IP%TYPE
)
authid current_user
as
  l_sql_text long;
begin

  update rrtest.facility_documents
  set 
    MODIFIED_DATE = sysdate
    , MODIFIED_BY = p_user_name
    , MODIFIED_BY_IP = p_user_ip
  where DOCUMENT_ID = p_document_id;

  delete from rrtest.facility_documents
  where DOCUMENT_ID = p_document_id;

  commit;

end;
/

insert into rrtest.destinations(destination_id, description)
values(1, '111');
insert into rrtest.destinations(destination_id, description)
values(2, '222');
insert into rrtest.destinations(destination_id, description)
values(3, '333');
insert into rrtest.destinations(destination_id, description)
values(4, '444');
insert into rrtest.destinations(destination_id, description)
values(5, '555');

insert into rrtest.usages(usage_id, description)
values(1, '123');
insert into rrtest.usages(usage_id, description)
values(2, '456');
