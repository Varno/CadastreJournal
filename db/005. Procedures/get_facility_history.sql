-- =============================================
-- v.1.0: Created by Pavel Kuznetsov 20/06/2014
-- return history for facility (with docs history) or for all
-- =============================================
create or replace procedure get_facility_history
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