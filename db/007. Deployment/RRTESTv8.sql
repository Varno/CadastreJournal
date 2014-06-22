CREATE USER RRTEST IDENTIFIED BY 1111
DEFAULT TABLESPACE "USERS"
TEMPORARY TABLESPACE "TEMP"
;

ALTER USER "RRTEST" QUOTA UNLIMITED ON USERS;

GRANT "ADM_PARALLEL_EXECUTE_TASK" TO "RRTEST" ;
GRANT "AQ_ADMINISTRATOR_ROLE" TO "RRTEST" ;
GRANT "DELETE_CATALOG_ROLE" TO "RRTEST" ;
GRANT "OEM_MONITOR" TO "RRTEST" ;
GRANT "XDB_WEBSERVICES" TO "RRTEST" ;
GRANT "HS_ADMIN_SELECT_ROLE" TO "RRTEST" ;
GRANT "EXECUTE_CATALOG_ROLE" TO "RRTEST" ;
GRANT "SCHEDULER_ADMIN" TO "RRTEST" ;
GRANT "DATAPUMP_IMP_FULL_DATABASE" TO "RRTEST" ;
GRANT "RESOURCE" TO "RRTEST" ;
GRANT "DATAPUMP_EXP_FULL_DATABASE" TO "RRTEST" ;
GRANT "AQ_USER_ROLE" TO "RRTEST" ;
GRANT "APEX_ADMINISTRATOR_ROLE" TO "RRTEST" ;
GRANT "SELECT_CATALOG_ROLE" TO "RRTEST" ;
GRANT "CTXAPP" TO "RRTEST" ;
GRANT "RECOVERY_CATALOG_OWNER" TO "RRTEST" ;
GRANT "GATHER_SYSTEM_STATISTICS" TO "RRTEST" ;
GRANT "DBA" TO "RRTEST" ;
GRANT "CONNECT" TO "RRTEST" ;
GRANT "AUTHENTICATEDUSER" TO "RRTEST" ;
GRANT "XDB_SET_INVOKER" TO "RRTEST" ;
GRANT "HS_ADMIN_EXECUTE_ROLE" TO "RRTEST" ;
GRANT "PLUSTRACE" TO "RRTEST" ;
GRANT "LOGSTDBY_ADMINISTRATOR" TO "RRTEST" ;
GRANT "XDB_WEBSERVICES_WITH_PUBLIC" TO "RRTEST" ;
GRANT "XDBADMIN" TO "RRTEST" ;
GRANT "XDB_WEBSERVICES_OVER_HTTP" TO "RRTEST" ;
GRANT "EXP_FULL_DATABASE" TO "RRTEST" ;
GRANT "IMP_FULL_DATABASE" TO "RRTEST" ;
GRANT "HS_ADMIN_ROLE" TO "RRTEST" ;
GRANT "DBFS_ROLE" TO "RRTEST" ;
GRANT "OEM_ADVISOR" TO "RRTEST" ;

create table rrtest.destinations 
(
  destination_id int constraint destination_id_nn not null
  , description varchar2(100) constraint desctination_description_nn not null
  , constraint destination_pk primary key(destination_id)
);

create table rrtest.usages
(
  usage_id int constraint usage_id_nn not null
  , description varchar2(100) constraint usage_description_nn not null
  , constraint usage_pk primary key(usage_id)
);

create table rrtest.facilities
(
  facility_id number constraint facility_id_nn not null
  , cadastral_number varchar2(20) constraint facility_cadastral_number_nn not null
  , square number(*, 2)
  , destination_id int constraint facility_destination_id_nn not null
  , area_description varchar2(4000)
  , usage_id int constraint facility_usage_id_nn not null
  , address varchar2(1024) 
  , search_key clob constraint facility_search_key_nn not null
  , created_date TIMESTAMP constraint facility_created_date_nn not null
  , created_by varchar2(256) constraint facility_created_by_nn not null
  , modified_date TIMESTAMP constraint facility_modified_date_nn not null
  , modified_by varchar2(256) constraint facility_modified_by_nn not null
  , modified_by_ip varchar2(256) constraint facility_modified_by_ip_nn not null
  , constraint facility_pk primary key(facility_id)
  , constraint facility_destination_fk foreign key(destination_id)
      references rrtest.destinations(destination_id)
  , constraint facility_usage_fk foreign key(usage_id)
      references rrtest.usages(usage_id)
  , constraint facility_cadastral_number_uk unique (cadastral_number)
);

create index rrtest.facilities_ft_idx ON rrtest.facilities ( search_key ) 
indextype is ctxsys.context
parameters ('sync (every "SYSDATE+5/1440") TRANSACTIONAL');

create table rrtest.facility_documents
(
  document_id number constraint facility_doc_id_nn not null
  , facility_id number constraint facility_doc_facil_id_nn not null
  , file_name varchar2(100) constraint facility_doc_file_name_nn not null
  , stored_path varchar2(1024) constraint facility_doc_stored_path_nn not null
  , created_date TIMESTAMP constraint facility_doc_created_date_nn not null
  , created_by varchar2(256) constraint facility_doc_created_by_nn not null
  , modified_date TIMESTAMP constraint facility_doc_modified_date_nn not null
  , modified_by varchar2(256) constraint facility_doc_modified_by_nn not null
  , modified_by_ip varchar2(256) constraint facility_doc_modified_by_ip_nn not null
  , constraint facility_document_pk primary key(document_id)
  , constraint facility_document_facility_fk foreign key(facility_id)
      references rrtest.facilities(facility_id)
);

create index rrtest.facility_document_facility_idx 
  on rrtest.facility_documents(facility_id asc, document_id asc);

create table rrtest.facility_history
(
  facility_history_id number constraint facility_hist_id_nn not null
  , facility_id number constraint facility_hist_fac_id_nn not null
  , modified_date TIMESTAMP constraint facility_hist_mod_date_nn not null
  , modified_by varchar2(256) constraint facility_hist_mod_by_nn not null
  , modified_by_ip varchar2(256) constraint facility_hist_mod_by_ip_nn not null
  , action varchar2(50) constraint facility_hist_action_nn not null
  , description xmltype constraint facility_hist_descr_nn not null
  , constraint facility_history_pk primary key(facility_history_id)
  , constraint facility_history_facility_fk foreign key(facility_id)
      references rrtest.facilities(facility_id)
);

create index rrtest.facility_history_facility_idx 
  on rrtest.facility_history(facility_id asc, facility_history_id desc);

create sequence rrtest.facility_seq start with 1 increment by 1;
create sequence rrtest.facility_document_seq start with 1 increment by 1;
create sequence rrtest.facility_history_seq start with 1 increment by 1;

-- comments
comment on table rrtest.destinations is 'Справочник назначений объектов недвижимости';
comment on column rrtest.destinations.destination_id is 'Первичный ключ таблицы DESTINATIONS';
comment on column rrtest.destinations.description is 'Описание. 100 символов, NOT NULL';

comment on table rrtest.usages is 'Справочник разрешенных использований';
comment on column rrtest.usages.usage_id is 'Первичный ключ таблицы USAGES';
comment on column rrtest.usages.description is 'Описание. 100 символов, NOT NULL';

comment on table rrtest.facilities is 'Объекты недвижимости';
comment on column rrtest.facilities.facility_id is 'Первичный ключ таблицы FACILITIES';
comment on column rrtest.facilities.cadastral_number is 'Кадастровый номер. 20 символов, NOT NULL';
comment on column rrtest.facilities.square is 'Площадь. Точность 2 знака после запятой';
comment on column rrtest.facilities.destination_id is 'Код назвачений. Внешний ключ на таблицу DESTINATIONS, NOT NULL';
comment on column rrtest.facilities.area_description is 'Местоположение';
comment on column rrtest.facilities.usage_id is 'Код использования. Внешний ключ на таблицу USAGES, NOT NULL';
comment on column rrtest.facilities.address is 'Адрес, 1024 символов';

comment on table rrtest.facility_documents is 'Документы кадастрового дела';
comment on column rrtest.facility_documents.document_id is 'Первичный ключ таблицы  FACILITY_DOCUMENTS';
comment on column rrtest.facility_documents.facility_id is 'Код объекта недвижимочти. Внешний ключ на таблицу FACILITIES, NOT NULL';
comment on column rrtest.facility_documents.file_name is 'Имя файла. 100 символов, NOT NULL';
comment on column rrtest.facility_documents.stored_path is 'Папка хранения. 1024 символа, NOT NULL';

comment on table rrtest.facility_history is 'История изменений объектов недвижимости и документов';
comment on column rrtest.facility_history.facility_history_id is 'Первичный ключ таблицы  FACILITY_HISTORY';
comment on column rrtest.facility_history.facility_id is 'Код объекта недвижимочти. Внешний ключ на таблицу FACILITIES, NOT NULL';
comment on column rrtest.facility_history.action is 'Действие, NOT NULL';
comment on column rrtest.facility_history.description is 'Описание изменений в XML, NOT NULL';
/

create type rrtest.r_page is object(record_id number, record_order number);
/

create type rrtest.t_page as table of rrtest.r_page;
/

-- =============================================
-- v.1.0: Created by Pavel Kuznetsov 20/06/2014
-- after all oparations with facility write to log
-- =============================================
create or replace trigger rrtest.add_facility_history
after insert or update or delete on rrtest.facilities
for each row
declare
  changes clob;
  changed_item clob;
  descr xmltype;
  action RRTEST.FACILITY_HISTORY.ACTION%TYPE;
  facility_id  RRTEST.FACILITY_HISTORY.FACILITY_ID%TYPE;
  modified_by RRTEST.FACILITY_HISTORY.MODIFIED_BY%TYPE;
  modified_by_ip  RRTEST.FACILITY_HISTORY.MODIFIED_BY_IP%TYPE;
begin

  changes := '';
  changed_item := '';
  
  if (:old.FACILITY_ID is null) then
    
    action := 'Создание';
    facility_id := :new.FACILITY_ID;
    modified_by := :new.MODIFIED_BY;
    modified_by_ip := :new.MODIFIED_BY_IP;

    select
        XMLElement("Field", XMLForest('CADASTRAL_NUMBER' as Name
                      , 'null' as Old
                      , :new.CADASTRAL_NUMBER as New)) || 
        XMLElement("Field" , XMLForest('SQUARE' as Name
                      , 'null' as Old
                      , :new.SQUARE as New)) ||
        XMLElement("Field", XMLForest('DESTINATION_ID' as Name
                      , 'null' as Old
                      , :new.DESTINATION_ID as New)) ||
        XMLElement("Field", XMLForest('AREA_DESCRIPTION' as Name
                      , 'null' as Old
                      , :new.AREA_DESCRIPTION as New)) ||
        XMLElement("Field", XMLForest('USAGE_ID' as Name
                      , 'null' as Old
                      , :new.USAGE_ID as New)) ||
        XMLElement("Field", XMLForest('ADDRESS' as Name
                      , 'null' as Old
                      , :new.ADDRESS as New)) x
    into changes    
    from dual;

  elsif (:new.FACILITY_ID is null) then
    
    action := 'Удаление';
    facility_id := :old.FACILITY_ID;
    modified_by := :old.MODIFIED_BY;
    modified_by_ip := :old.MODIFIED_BY_IP;

    select
        XMLElement("Field", XMLForest('CADASTRAL_NUMBER' as Name
                      , :old.CADASTRAL_NUMBER as Old
                      , :old.CADASTRAL_NUMBER as New)) || 
        XMLElement("Field", XMLForest('SQUARE' as Name
                      , :old.SQUARE as Old
                      , :old.SQUARE as New)) ||
        XMLElement("Field", XMLForest('DESTINATION_ID' as Name
                      , :old.DESTINATION_ID as Old
                      , :old.DESTINATION_ID as New)) ||
        XMLElement("Field", XMLForest('AREA_DESCRIPTION' as Name
                      , :old.AREA_DESCRIPTION as Old
                      , :old.AREA_DESCRIPTION as New)) ||
        XMLElement("Field", XMLForest('USAGE_ID' as Name
                      , :old.USAGE_ID as Old
                      , :old.USAGE_ID as New)) ||
        XMLElement("Field", XMLForest('ADDRESS' as Name
                      , :old.ADDRESS as Old
                      , :old.ADDRESS as New)) x
    into changes    
    from dual;

  else
    
    action := 'Изменение';
    facility_id := :new.FACILITY_ID;
    modified_by := :new.MODIFIED_BY;
    modified_by_ip := :new.MODIFIED_BY_IP;
    
    if (:old.CADASTRAL_NUMBER <> :new.CADASTRAL_NUMBER) then
      select ''||
        XMLElement("Field", XMLForest('CADASTRAL_NUMBER' as Name
                      , :old.CADASTRAL_NUMBER as Old
                      , :new.CADASTRAL_NUMBER as New))
      into changed_item
      from dual;
      changes := changes || changed_item;
    end if;
  
    if (:old.SQUARE <> :new.SQUARE) then
      select ''||
        XMLElement("Field", XMLForest('SQUARE' as Name
                      , :old.SQUARE as Old
                      , :new.SQUARE as New))
      into changed_item
      from dual;    
      changes := changes || changed_item;
    end if;
  
    if (:old.DESTINATION_ID <> :new.DESTINATION_ID) then
      select ''||
        XMLElement("Field", XMLForest('DESTINATION_ID' as Name
                      , :old.DESTINATION_ID as Old
                      , :new.DESTINATION_ID as New))
      into changed_item
      from dual;    
      changes := changes || changed_item;
    end if;
  
    if (:old.AREA_DESCRIPTION <> :new.AREA_DESCRIPTION) then
      select ''||
        XMLElement("Field", XMLForest('AREA_DESCRIPTION' as Name
                      , :old.AREA_DESCRIPTION as Old
                      , :new.AREA_DESCRIPTION as New))
      into changed_item
      from dual;    
      changes := changes || changed_item;
    end if;
  
    if (:old.USAGE_ID <> :new.USAGE_ID) then
      select ''||
        XMLElement("Field", XMLForest('USAGE_ID' as Name
                      , :old.USAGE_ID as Old
                      , :new.USAGE_ID as New))
      into changed_item
      from dual;    
      changes := changes || changed_item;
    end if;
  
    if (:old.ADDRESS <> :new.ADDRESS) then
      select ''||
        XMLElement("Field", XMLForest('ADDRESS' as Name
                      , :old.ADDRESS as Old
                      , :new.ADDRESS as New))
      into changed_item
      from dual;    
      changes := changes || changed_item;
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

-- =============================================
-- v.1.0: Created by Pavel Kuznetsov 20/06/2014
-- after all oparations with facilities doc write to log
-- =============================================
create or replace trigger rrtest.add_facility_document_history
after insert or update or delete on rrtest.facility_documents
for each row
declare
  changes clob;
  changed_item clob;
  descr xmltype;
  action RRTEST.FACILITY_HISTORY.ACTION%TYPE;
  facility_id  RRTEST.FACILITY_HISTORY.FACILITY_ID%TYPE;
  modified_by RRTEST.FACILITY_HISTORY.MODIFIED_BY%TYPE;
  modified_by_ip  RRTEST.FACILITY_HISTORY.MODIFIED_BY_IP%TYPE;
begin

  changes := '';
  changed_item := '';
  
  if (:old.DOCUMENT_ID is null) then
    
    action := 'Создание документа';
    facility_id := :new.FACILITY_ID;
    modified_by := :new.MODIFIED_BY;
    modified_by_ip := :new.MODIFIED_BY_IP;

    select
        XMLElement("Field", XMLForest('DOCUMENT_ID' as Name
                      , 'null' as Old
                      , :new.DOCUMENT_ID as New)) || 
        XMLElement("Field" , XMLForest('FILE_NAME' as Name
                      , 'null' as Old
                      , :new.FILE_NAME as New)) ||
        XMLElement("Field", XMLForest('STORED_PATH' as Name
                      , 'null' as Old
                      , :new.STORED_PATH as New)) x
    into changes    
    from dual;
    
  elsif (:new.DOCUMENT_ID is null) then
    
    action := 'Удаление документа';
    facility_id := :old.FACILITY_ID;
    modified_by := :old.MODIFIED_BY;
    modified_by_ip := :old.MODIFIED_BY_IP;

    select
        XMLElement("Field", XMLForest('DOCUMENT_ID' as Name
                      , :old.DOCUMENT_ID as Old
                      , :old.DOCUMENT_ID as New)) || 
        XMLElement("Field", XMLForest('FILE_NAME' as Name
                      , :old.FILE_NAME as Old
                      , :old.FILE_NAME as New)) ||
        XMLElement("Field", XMLForest('STORED_PATH' as Name
                      , :old.STORED_PATH as Old
                      , :old.FILE_NAME as New)) x
    into changes    
    from dual;

  else
    
    action := 'Обновление документа';
    facility_id := :new.FACILITY_ID;
    modified_by := :new.MODIFIED_BY;
    modified_by_ip := :new.MODIFIED_BY_IP;

    if (:old.FILE_NAME <> :new.FILE_NAME or :old.STORED_PATH <> :new.STORED_PATH) then
      select ''||
        XMLElement("Field", XMLForest('DOCUMENT_ID' as Name
                      , :old.DOCUMENT_ID as Old
                      , :new.DOCUMENT_ID as New))
      into changed_item
      from dual;
      changes := changes || changed_item;
    end if;
    
    if (:old.FILE_NAME <> :new.FILE_NAME) then
      select ''||
        XMLElement("Field", XMLForest('FILE_NAME' as Name
                      , :old.FILE_NAME as Old
                      , :new.FILE_NAME as New))
      into changed_item
      from dual;
      changes := changes || changed_item;
    end if;
  
    if (:old.STORED_PATH <> :new.STORED_PATH) then
      select ''||
        XMLElement("Field", XMLForest('STORED_PATH' as Name
                      , :old.STORED_PATH as Old
                      , :new.STORED_PATH as New))
      into changed_item
      from dual;
      changes := changes || changed_item;
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

-- =============================================
-- v.1.0: Created by Pavel Kuznetsov 20/06/2014
-- return properties of facility
-- when id is not null -> return by id
-- else used param p_search_string 
-- =============================================
create or replace 
procedure rrtest.get_facilities
(
  p_facility_id in number
  , p_search_string in nvarchar2
  , p_skip in number
  , p_take in number
  , p_cursor out SYS_REFCURSOR
  , p_rowcount out number
)
authid current_user
as
  l_search_string nvarchar2(32767);
  temp_page rrtest.t_page;
  l_take number;
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
      , MODIFIED_DATE
    from RRTEST.FACILITIES
    where FACILITY_ID = p_facility_id;
    
    select count(*) into p_rowcount
    from RRTEST.FACILITIES
    where FACILITY_ID = p_facility_id;
    
  else 
  
    l_take := p_skip + p_take;

    if (p_search_string is not null) then

      l_search_string := replace(replace(p_search_string, '%', '|%'), '_', '|_') || '%';

      select rrtest.r_page(p.FACILITY_ID, p.rnk)
      bulk collect into temp_page
      from 
         (select 
            FACILITY_ID
            , row_number() over (order by CADASTRAL_NUMBER asc) as rnk
          from RRTEST.FACILITIES
          where CADASTRAL_NUMBER like l_search_string escape '|'
            and rownum < 1000
          ) p
      where p.rnk > p_skip and p.rnk <= l_take;
      
      if (temp_page.count = 0) then

        select rrtest.r_page(p.FACILITY_ID, p.rnk)
        bulk collect into temp_page
        from 
            (select 
              FACILITY_ID
              , row_number() over (order by CADASTRAL_NUMBER asc) as rnk
            from RRTEST.FACILITIES
            where contains(SEARCH_KEY, p_search_string) > 0 
              and rownum < 1000
            ) p
        where p.rnk > p_skip and p.rnk <= l_take;
      
      end if;

      select count(*) into p_rowcount
      from
      (
        select rowid
        from RRTEST.FACILITIES
        where CADASTRAL_NUMBER like l_search_string escape '|'
        union
        select rowid
        from RRTEST.FACILITIES
        where contains(SEARCH_KEY, p_search_string) > 0
      );

    else

      select rrtest.r_page(p.FACILITY_ID, p.rnk)
      bulk collect into temp_page
      from 
        (select
          FACILITY_ID
          , row_number() over (order by CADASTRAL_NUMBER asc) as rnk
        from RRTEST.FACILITIES
        where rownum <= 1000
        ) p
      where p.rnk > p_skip and p.rnk <= l_take;      
  
      select count(*) into p_rowcount
      from RRTEST.FACILITIES;

    end if;

    open p_cursor for
    select 
      f.FACILITY_ID
      , f.CADASTRAL_NUMBER
      , f.SQUARE
      , f.DESTINATION_ID
      , (substr(f.AREA_DESCRIPTION, 1, 256) || case when length(f.AREA_DESCRIPTION) > 256 then '...' else '' end) AREA_DESCRIPTION
      , f.USAGE_ID
      , f.ADDRESS
      , f.MODIFIED_DATE
    from 
      table(temp_page) tp
      inner join RRTEST.FACILITIES f
        on TP.record_id = f.FACILITY_ID
    order by tp.record_order ;
    
  end if;

end;
/

-- =============================================
-- v.1.0: Created by Pavel Kuznetsov 20/06/2014
-- return documents for facility
-- =============================================
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
  temp_page rrtest.t_page;
  l_take number;
begin

  l_take := p_skip + p_take;

  select rrtest.r_page(p.DOCUMENT_ID, p.rnk)
  bulk collect into temp_page
  from 
    (select 
      DOCUMENT_ID
      , row_number() over (order by DOCUMENT_ID asc) as rnk
    from RRTEST.FACILITY_DOCUMENTS
    where FACILITY_ID = p_facility_id
    ) p
  where p.rnk > p_skip and p.rnk <= l_take;  

  select count(*) into p_rowcount
  from RRTEST.FACILITY_DOCUMENTS
  where FACILITY_ID = p_facility_id;

  open p_cursor for
  select 
    fd.DOCUMENT_ID
    , fd.FILE_NAME
    , fd.STORED_PATH      
  from 
    table(temp_page) tp
    inner join RRTEST.FACILITY_DOCUMENTS fd
      on TP.record_id = fd.DOCUMENT_ID
  order by tp.record_order ;
   
end;
/

-- =============================================
-- v.1.0: Created by Pavel Kuznetsov 20/06/2014
-- return history for facility (with docs history) or for all
-- =============================================
create or replace procedure                                    get_facility_history
(
  p_facility_id in number
  , p_skip in number
  , p_take in number
  , p_cursor out SYS_REFCURSOR
  , p_rowcount out number
)
authid current_user
as
  temp_page rrtest.t_page;
  l_take number;
begin

  l_take := p_skip + p_take;

  if (p_facility_id is not null) then

    select rrtest.r_page(p.FACILITY_HISTORY_ID, p.rnk)
    bulk collect into temp_page
    from 
      (select 
        FACILITY_HISTORY_ID
        , row_number() over (order by FACILITY_HISTORY_ID desc) as rnk
       from RRTEST.FACILITY_HISTORY
       where FACILITY_ID = p_facility_id
    ) p
    where p.rnk > p_skip and p.rnk <= l_take;  

    select count(*) into p_rowcount
    from RRTEST.FACILITY_HISTORY
    where FACILITY_ID = p_facility_id;

  else

    select rrtest.r_page(p.FACILITY_HISTORY_ID, p.rnk)
    bulk collect into temp_page
    from 
      (select 
        FACILITY_HISTORY_ID
        , row_number() over (order by FACILITY_HISTORY_ID desc) as rnk
       from RRTEST.FACILITY_HISTORY
    ) p
    where p.rnk > p_skip and p.rnk <= l_take;
  
    select count(*) into p_rowcount
    from RRTEST.FACILITY_HISTORY;
  
  end if;

  open p_cursor for
  select 
    r.FACILITY_HISTORY_ID
    , max(r.MODIFIED_DATE)
    , max(r.MODIFIED_BY)
    , max(r.MODIFIED_BY_IP)
    , max(r.ACTION)
    , listagg(r.DESCR, '
') within group (order by r.f_order) txt_descr
  from
    (select 
      p.FACILITY_HISTORY_ID
      , p.MODIFIED_DATE
      , p.MODIFIED_BY
      , p.MODIFIED_BY_IP
      , p.ACTION
      , p.record_order 
      , case x.field_name
            when 'CADASTRAL_NUMBER' then 'Кадастровый №'
            when 'SQUARE' then 'Площадь'
            when 'AREA_DESCRIPTION' then 'Описание местности'
            when 'ADDRESS' then 'Адрес'
            when 'DESTINATION_ID' then 'Назначение'
            when 'USAGE_ID' then 'Разрешенное использование'
            when 'DOCUMENT_ID' then 'ID документа'
            when 'FILE_NAME' then 'Название документа'
            when 'STORED_PATH' then 'Папка хранения док-та'
            else x.field_name
        end || ':  ' 
        || nvl(case x.field_name
          when 'DESTINATION_ID' then (select description from rrtest.destinations where destination_id = x.new_value)
          when 'USAGE_ID' then (select description from rrtest.usages where usage_id = x.new_value)
          else x.new_value
          end, 'null') descr
        , case x.field_name
            when 'CADASTRAL_NUMBER' then 0
            when 'SQUARE' then 1
            when 'AREA_DESCRIPTION' then 2
            when 'ADDRESS' then 3
            when 'DESTINATION_ID' then 4
            when 'USAGE_ID' then 5
            when 'DOCUMENT_ID' then 6
            when 'FILE_NAME' then 7
            when 'STORED_PATH' then 8
            else 99
        end f_order    
    from
      (select 
        fh.FACILITY_HISTORY_ID
        , fh.MODIFIED_DATE
        , fh.MODIFIED_BY
        , fh.MODIFIED_BY_IP
        , fh.ACTION
        , fh.DESCRIPTION
        , tp.record_order 
      from 
        table(temp_page) tp
        inner join RRTEST.FACILITY_HISTORY fh
          on TP.record_id = FH.FACILITY_HISTORY_ID
      ) p
      , XMLTable('/Fields/Field'
              passing p.DESCRIPTION
              columns field_name varchar(20) path 'NAME',
                      old_value varchar(4000) path 'OLD',
                      new_value varchar(4000) path 'NEW') x
    where x.field_name <> 'AREA_DESCRIPTION'
    ) r
  group by r.FACILITY_HISTORY_ID, r.record_order
  order by r.record_order ;
  
end;
/

-- =============================================
-- v.1.0: Created by Pavel Kuznetsov 20/06/2014
-- insert/update facility
-- =============================================
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
  l_search_key clob;
  l_destination RRTEST.DESTINATIONS.DESCRIPTION%TYPE;
  l_usage RRTEST.USAGES.DESCRIPTION%TYPE;
begin

  savepoint begin_update_facility;

  select DESCRIPTION into l_destination
  from rrtest.destinations
  where destination_id = p_destination_id;
  
  select DESCRIPTION into l_usage
  from rrtest.usages
  where usage_id = p_usage_id;
  
  l_search_key := nvl(l_destination, ' ') || ' '
    || nvl(l_usage, ' ') || ' ' 
    || nvl(p_area_description, ' ') || ' '
    || nvl(p_address, ' ');

  if (p_facility_id is not null) then

    update rrtest.facilities
    set 
      SQUARE = p_square
      , DESTINATION_ID = p_destination_id
      , AREA_DESCRIPTION = p_area_description
      , USAGE_ID = p_usage_id
      , ADDRESS = p_address
      , SEARCH_KEY = l_search_key
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
      , SEARCH_KEY
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
      , l_search_key
      , sysdate
      , p_user_name
      , sysdate
      , p_user_name
      , p_user_ip);
    
  end if;

  commit;

  exception
    when others then rollback to begin_update_facility;
    raise;

end;
/

-- =============================================
-- v.1.0: Created by Pavel Kuznetsov 20/06/2014
-- insert/update facility doc
-- =============================================
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
begin

  savepoint begin_update_facility_doc;

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

  exception
    when others then rollback to begin_update_facility_doc;
    raise;

end;
/

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

insert into rrtest.destinations(destination_id, description)
values(1, 'Земельный участок');
insert into rrtest.destinations(destination_id, description)
values(2, 'Здание жилое');
insert into rrtest.destinations(destination_id, description)
values(3, 'Здание нежилое');
insert into rrtest.destinations(destination_id, description)
values(4, 'Сооружение');
insert into rrtest.destinations(destination_id, description)
values(5, 'Изолированное помещение');

insert into rrtest.usages(usage_id, description)
values(1, 'Сельскохозяйственная деятельность');
insert into rrtest.usages(usage_id, description)
values(2, 'Жилая застройка');
insert into rrtest.usages(usage_id, description)
values(3, 'Общественная деятельность');
insert into rrtest.usages(usage_id, description)
values(4, 'Деловое использование');
insert into rrtest.usages(usage_id, description)
values(5, 'Рекреационная деятельность');
insert into rrtest.usages(usage_id, description)
values(6, 'Производственная деятельность');
insert into rrtest.usages(usage_id, description)
values(7, 'Транспорт');
insert into rrtest.usages(usage_id, description)
values(8, 'Оборона и безопасность');
insert into rrtest.usages(usage_id, description)
values(9, 'Особо охраняемая территория');
insert into rrtest.usages(usage_id, description)
values(10, 'Лесная деятельность');
insert into rrtest.usages(usage_id, description)
values(11, 'Водная деятельность');
insert into rrtest.usages(usage_id, description)
values(12, 'Специальная деятельность');
insert into rrtest.usages(usage_id, description)
values(13, 'Отсутствие хозяйственной деятельности');



