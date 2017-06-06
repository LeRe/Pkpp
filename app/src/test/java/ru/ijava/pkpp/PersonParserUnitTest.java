package ru.ijava.pkpp;

import org.junit.Test;

import ru.ijava.pkpp.utils.PersonParser;

import static org.junit.Assert.*;


/**
 * Created by levchenko on 06.06.2017.
 */

public class PersonParserUnitTest {

    String[]  rightFullName = {
            "Бабынин Сергей Александрович", "Боева Любовь Николаевна", "Бороданев Сергей Михайлович",
            "Гаврилов Сергей Александрович", "Зайцев Константин Викторович", "Иванова Ольга Владимировна",
            "Кабанова Наталья Владимировна", "Космынина Галина Петровна", "Котов Игорь Валентинович",
            "Куликова Татьяна Александровна", "Калинина (Ломакина) Ирина Юрьевна", "Малиянц Светлана Геннадьевна",
            "Мартынов Павел Петрович", "Монахова Юлия Валерьевна", "Мочалкин Андрей Михайлович",
            "Кизко Антон Валентинович", "Романова Инна Викторовна", "Селин Дмитрий Сергеевич",
            "Матвеенкова  (Семененко) Ксения Сергеевна", "Сконкина Анастасия Александровна",
            "Тенников Анатолий Николаевич", "Чернобровкина Надежда Ивановна", "Полудкина Ирина Васильевна",
            "Шадрунов Феликс Валентинович", "Шкуркина Елена Георгиевна", "Шведова Мария Владимировна", "Шиков Алексей Васильевич",
            "Матвеева Татьяна Дмитриевна", "Дьяченко Марина Николаевна", "Чугунова Татьяна Владимировна",
            "Сергеева Юлия Александровна", "Зайцев Михаил Александрович", "Рогозина Элеонора Реджеповна",
            "Фандеев Сергей Алексеевич", "Рябец Владимир Александрович", "Насонова Юлия Ивановна",
            "Сибирко Екатерина Владимировна", "Аристов Александр Николаевич", "Карпов Иван Павлович",
            "Ефремов Евгений Михайлович", "Соболев Александр Александрович", "Зотов Роман Игоревич",
            "Савин Евгений Васильевич", "Гречкин Антон Александрович", "Дмитриева Татьяна Алексеевна",
            "Ковалев Анатолий Яковлевич", "Курдюков Виталий Ильич", "Свистухин Анатолий Александрович",
            "Пилюгина Анастасия Викторовна", "Матохин Денис Юрьевич", "Монахова Екатерина Сергеевна",
            "Копцева Лаура Пайкаровна", "Щеголев Игорь Вячеславович",
            "Кириллова (Чернякова) Мария Викторовна", "Звягин Сергей Викторович",
            "Спиридонов Павел Николаевич", "Луговая (Мазур) Оксана Анатольевна",
            "Золотухина Наталья Алексеевна", "Мачульская(Хромова) Евгения Владимировна", "Аликова Галина Владимировна",
            "Мазаева Елена Сергеевна", "Колесник Андрей Владимирович", "Круглякова Ксения Дмитриевна",
            "Тюкачев Николай Николаевич", "Баков Михаил Сергеевич", "Баландин Александр Сергеевич", "Куц Ирина Николаевна",
            "Липгарт Елена Вадимовна", "Фадеева Инна Борисовна", "Павловская Марина Евгеньевна", "Гончарук Оксана Васильевна",
            "Метелицына Юлия Юрьевна", "Меляков Николай Иванович",
            "Романов Андрей Викторович", "Мухортова Лариса Алексеевна", "Кузнецов Сергей Александрович",
            "Николаева Валентина Николаевна",
            "Чухно Ирина Михайловна", "Логачев Андрей Вячеславович", "Савина (Сивак) Виктория Александровна",
            "Ломакин Сергей Васильевич", "Городничев Евгений Анатольевич",
            "Гаврюкова Елена Николаевна", "Маклакова Надежда Сергеевна",
            "Мысин Александр Николаевич", "Коваева Замбела Макаровна", "Молодцова Оксана Анатольевна",
            "Головчак Виктор Марьянович", "Огурцова Людмила Михайловна", "Коврева Алла Борисовна",
            "Шинкаренко Ирина Евгеньевна", "Журавлева Марина Дмитриевна", "Буряк Евгений Григорьевич", "Пуриньш Алиса Александровна",
            "Никифорова Юлия Викторовна", "Харитоненко Наталия Николаевна", "Кабаев Александр Андреевич",
            "Зарубин Дмитрий Олегович", "Загрядский Константин Николаевич", "Куликова (Селезенева) Екатерина Александровна",
            "Рой Вячеслав Олегович", "Ковалева Эльвира Игоревна", "Чуйко Светлана Валерьевна", "Кошелев Анатолий Семенович",
            "Вартанян Нерсес Арташесович", "Рогозин Андрей Вячеславович", "Симонов Михаил Валентинович",
            "Абрамова Анна Евгеньевна", "Левченко Роман Петрович", "Симонова Екатерина Дмитреевна", "Долотова Маргарита Викторовна",
            "Затонских Виктория Илларионовна", "Нещемная Валерия Валерьевна", "Сусленкова Анна Сергеевна", "Неупокоев Николай",
            "Михайлов Петр Владимирович", "Калжанов Анатолий Юрьевич", "Зубков Евгений Владимирович",
            "Сердюк Владимир Николаевич", "Разинкин Евгений Владимирович", "Долотов Алексей Владимирович",
            "Архипенков Олег Анатольевич", "Юмашев Алексей Александрович", "Ганеева Светлана Алиянусовна",
            "Мисюнас Виктор Станиславович", "Пилюгина Елена Викторовна", "Тедеев Батраз Дмитриевич (Борис)",
            "Коновалов Алексей Иванович", "Сочнева Анастасия Сергеевна"};

    String[]  failFullName = {"Участок допеч. подготовки (общий)",
            "Дежурный электрик", "Дежурный электроник", "Диспетчер отдела автопогрузки", "Мастер смены брошюровочного участка",
            "Мастер смены брошюровочного участка", "Мастер смены листового участка", "Мастер смены листового участка(цех №9)",
            "Мастер журнально-ролевого участка (цех №3,№4,№10)", "Мастер смены цех №11", "Мастер смены СВХ",
            "Механик журнально-ролевого участка (цех №3, Sunday)", "Механик цеха №10", "Механик брошюровочного участка (цех №6)",
            "Механик цеха №11", "Водители погрузчиков цех№10", "Водители погрузчиков цех№9",
            "Электропогрузчики", "Контролер по сигнальным экземплярам", "Кладовщик склада листовой бумаги",
            "Кладовщик склада ролевой бумаги", "Оператор высотного хранения", "Персонофикация участок",
            "Приемка бумаги", "Приемка полуфабрикатов", "Приемщик-отправитель", "Охрана (КПП)", "Печатники 11 цеха",
            "Медпункт", "Участок вторсырья", "Участок персонофикации", "Охрана (ПКПП)",  "Участок сортировки листового цеха",
            "Уборщики",  "Секретари факс", "Оператор участка макулатуры", "Старший смены СВХ",
            "Охрана (Рябиновая)", "Участок качества (ОТК)",
            "лаборатория отдела гл.технолога", "Столовая", "Мастер смены печатного цеха", "Мастер ручных работ", "Мастер участка опалечивания",
            "Дежурный диспетчер брошюровочного цеха", "Механик 10 цеха", "Дежурный сантехник", "Охрана (КПП-Каскад)", "Дежурный грузчик СВХ", "Водитель погрузчика"
    };



    @Test
    public void isNameSurnamePatronymic_isCorrect() throws Exception {
        PersonParser personParser = new PersonParser();

        for (int i = 0; i < rightFullName.length; i++) {
            assertTrue(personParser.isNameSurnamePatronymic(rightFullName[i]));
        }

        for (int i = 0; i < failFullName.length; i++) {
            assertFalse(personParser.isNameSurnamePatronymic(failFullName[i]));
        }
    }


}




