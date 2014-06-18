declare
  type tmp_table_type is table of RRTEST.FACILITIES.AREA_DESCRIPTION%TYPE index by binary_integer;
  tmp_area tmp_table_type;
  tmp_street tmp_table_type;
  l_destination_id number;
  l_destination RRTEST.DESTINATIONS.DESCRIPTION%TYPE;
  l_usage_id number;
  l_usage RRTEST.USAGES.DESCRIPTION%TYPE;
  
  l_cadastral_number RRTEST.FACILITIES.CADASTRAL_NUMBER%TYPE;
  l_area_description RRTEST.FACILITIES.AREA_DESCRIPTION%TYPE;
  l_address RRTEST.FACILITIES.ADDRESS%TYPE;
  l_search_key RRTEST.FACILITIES.SEARCH_KEY%TYPE;
begin

  tmp_area(0) := 'Гостиница Акме расположилась в окружении архитектурных памятников: от здания всего две минуты до Гостиного двора, за четверть часа можно дойти до Казанского собора, Русского музея или храма Спас на крови, Дворцовой площади и Эрмитажа. Ближайшие станции метро «Гостиный двор» и «Невский проспект».';
  tmp_area(1) := 'Мини-гостиница Резиденция (Адмирал) открылась на Гороховой улице, в самом сердце Санкт-Петербурга у Адмиралтейства. Классический интерьер мини-гостиницы украшен изящной лепниной и живописью. Минимальное количество просторных номеров создает гармонию уюта и комфорта домашней расслабляющей обстановки. Резиденция (Адмирал) расположена в центре Петербурга близ Дворцовой площади. А вечером развлечения, рестораны, прогулки по главным набережным Невы и возможность наблюдать за разведением мостов, прогулки на корабликах и прекрасных яхтах доступны Вам буквально в нескольких шагах от нашей мини-отеля. Элитное месторасположение мини-гостиницы Резиденция (Адмирал) даст Вам почувствовать истинный дух этого прекрасного города. Вы будите приятно удивлены реальными ценами. Приезжайте к нам!';
  tmp_area(2) := 'В Санкт-Петербурге есть множество красивых мест: площади, дворцы, фонтаны… Но самое чудесное в этом городе, что его архитектура живая: на нее можно не просто смотреть издали, но и находиться внутри этого пространства, жить в исторических зданиях, прикасаться к граниту набережных, проходить по булыжным мостовым. И именно для того чтобы так полностью окунуть туристов в атмосферу города на Неве отель «Лидваль» расположился прямо в сердце северной столицы на Большой Конюшенной улице в старинном здании.';
  tmp_area(3) := 'В Санкт-Петербурге в своих стенах рада приветствовать Вас гостиница Эрмитаж. Это новая частная гостиница категории ****, не похожая ни на одну из петербургских гостиниц. Ее преимущество заключается в том, что Вы можете забронировать не только один из его 4 комфортабельных и уютных двухместных номеров, но и снять всю мини-гостиницу Эрмитаж целиком, пользуясь ей как индивидуальным номером люкс, где в Вашем распоряжении будут находиться 4 спальни, полностью оборудованный для работы кабинет, парадный холл с местом для общения у камина, а также весь персонал гостиницы, работающий только с Вами. Тщательная конфиденциальность, оснащенность всех номеров по последнему слову техники и высокий уровень обслуживания гарантированы Вам в гостинице Эрмитаж и обеспечат вам хороший отдых. В гостинице Вы сможете отвлечься от городской суеты, насладившись тишиной и уютом. Несмотря на то, что гостиница расположена в самом центре Санкт-Петербурга, всего в 3 минутах ходьбы от Эрмитажа и Дворцовой площади вы всегда сможете здесь полноценно отдохнуть и вас не побеспокоит шум большого города. В нескольких шагах от гостиницы находятся Марсово поле и Летний Сад - великолепные места для прогулок, а также Дворцовая набережная с незабываемым видом на Петропавловскую крепость и Стрелку Васильевского острова.';
  tmp_area(4) := 'Санкт-Петербург не зря известен, как город каналов и мостов: узкие набережные пронизывают центр города и составляют на карте причудливый узор. Именно здесь, возле каналов, расположились самые старинные красивые дома Петербурга: ранее, в XIX веке, когда в основном застраивалась эта часть Петербурга, в этом районе было принято строить доходные дома – большие пяти-, шестиэтажные особняки, в которых могли селиться богатые постояльцы. Это были своего рода гостиницы для длительного проживания.';
  tmp_area(5) := 'Гостиница Атлантика в Санкт-Петербурге относится к бизнес-классу и находится всего в нескольких минутах ходьбы от Невского проспекта. Она занимает четыре этажа в здании 18 столетия и предоставляет своим клиентам несколько категорий номеров от одноместного до полулюкса с качественным обслуживанием. Все номера находятся в зоне покрытия Wi-Fi. Доступ к Интернету предоставляется гостям отеля абсолютно бесплатно.';
  tmp_area(6) := 'Очень уютная и стильная мини-гостиница бизнес-класса Старая Вена располагается в здании 13/8 по Малой Морской. Раньше на этом месте находился ресторан столичной богемы Вена. Посетители гостиницы Старая Вена имеют прекрасную возможность при доступных ценах получить возможность размещения в самом сердце города, почувствовать в атмосферу прошлого века. В вашем распоряжении будет уникальная библиотека. К услугам проживающих в мини-гостинице Старая Вена: конференц-зал, факсимильная связь, ксерокс и бесплатный доступ в интернет, сауна и круглосуточное кафе. Расположение гостиницы более чем удачное. Важнейшие достопримечательности Петербурга: Эрмитаж, Исаакиевский собор и Адмиралтейство находятся в пределах 5 минут ходьбы от гостиницы. Это позволит Вам насладиться красотой Санкт-Петербурга, не изнуряя себя поездками на каком-либо транспорте.';
  tmp_area(7) := 'Мини-отель «На Конюшенной» соседствует с самыми знаменитыми достопримечательностями Петербурга: Дворцовая площадь, Эрмитаж, Летний Сад, Казанский собор. Он находится на Большой конюшенной улице, 1, недалеко от метро «Гостиный двор», «Невский проспект» и «Канал Грибоедова».';
  tmp_area(8) := 'Отнюдь не просто держать марку пятизвездочного отеля, ведь гостиницу этого класса можно сравнить с целым микрорайоном, инфраструктура которого должна отвечать самым высоким требованиям. Гостиница Гранд Отель Европа в Санкт-Петербурге, явившаяся первым пятизвездочным отелем России, не просто превосходно справляется с этой ответственностью, но и постоянно подтверждает статус лучшего, о чем свидетельствуют победы в таких престижных конкурсах, как: Лучший отель Санкт-Петербурга, Лучший отель Санкт-Петербурга и Москвы, Пятизвездный Алмаз, Лучший отель Восточной Европы и др.';
  tmp_area(9) := 'Мини-гостиница «Резиденция на Мойке» открылась в 2007 году, но за столь короткий срок уже успел завоевать популярность среди туристов. Отель располагается в легендарном месте Санкт-Петербурга -  на набережной реки Мойки, в нескольких шагах от Эрмитажа и Дворцовой площади. Наслаждаться архитектурными красотами можно будет прямо из окон своего номера. Резиденция на Мойке находится в одном из старейших зданий, которое хранит множество историй и легенд. Это центр культурно-исторической жизни города, именно здесь пересекались судьбы многих известных русских писателей.';
  
  tmp_street(0) := 'пр. Невский';
  tmp_street(1) := 'пр. Лиговский';
  tmp_street(2) := 'пр. Вознесенский';
  tmp_street(3) := 'пр. Литейный';
  tmp_street(4) := 'пр. Адмиралтейский';
  tmp_street(5) := 'ул. Некрасова';
  tmp_street(6) := 'ул. Моховая';
  tmp_street(7) := 'ул. Маяковского';
  tmp_street(8) := 'ул. Казанская';
  tmp_street(9) := 'пр. Жуковского';
  
for i in 1..100
loop

  l_destination_id := trunc(dbms_random.value(1,5));
  l_usage_id := trunc(dbms_random.value(1,13));
  l_cadastral_number := trunc(dbms_random.value(11,99)) || ':' 
    || trunc(dbms_random.value(11,99)) || ':' 
    || trunc(dbms_random.value(100001,999999)) || ':' 
    || trunc(dbms_random.value(11,99));
  l_area_description := tmp_area(trunc(dbms_random.value(0, 9)));
  l_address := 'г. Санкт-Петербург, ' 
    || tmp_street(trunc(dbms_random.value(0, 9))) 
    || ', д. ' || trunc(dbms_random.value(1, 100));

  select DESCRIPTION into l_destination
  from rrtest.destinations
  where destination_id = l_destination_id;
  
  select DESCRIPTION into l_usage
  from rrtest.usages
  where usage_id = l_usage_id;
  
  l_search_key := nvl(l_cadastral_number, ' ') || ' ' 
    || nvl(l_destination, ' ') || ' '
    || nvl(l_usage, ' ') || ' ' 
    || nvl(l_area_description, ' ') || ' '
    || nvl(l_address, ' ');

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
  select 
    rrtest.facility_seq.nextval
    , l_cadastral_number
    , trunc(dbms_random.value(1,9999), 2)
    , l_destination_id
    , l_area_description
    , l_usage_id
    , l_address
    , l_search_key
    , sysdate
    , 'test'
    , sysdate
    , 'test'
    , '123'
  from
    dual;

  commit;

end loop;

end;
/
EXECUTE CTX_DDL.SYNC_INDEX ( 'rrtest.facilities_ft_idx' );
