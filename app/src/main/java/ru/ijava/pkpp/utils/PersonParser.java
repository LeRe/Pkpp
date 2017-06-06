package ru.ijava.pkpp.utils;

import android.util.Log;

import ru.ijava.pkpp.model.ListPersons;
import ru.ijava.pkpp.model.Person;

/**
 * Created by levchenko on 06.06.2017.
 * Предназначен для разбора строк содержащих Фамилию Имя Отчество.
 * Цели:
 *  1) Получить раздельные Фмилию/Имя/Отчество
 *  2)
 */
public class PersonParser {

    public PersonParser()//ListPersons listPersons)
    {
        //объекту при создании придется обучиться  для этого переберем переданный
        // в конструктор listPersons  и посмотрим что он содержит внутри

        //for (Person person : listPersons.getPersons()) {
        //    Log.i("RELE", person.getFullName());
        //}



    }

    public boolean isNameSurnamePatronymic(String fullName) {
        boolean result = false;


        result = true;


        return result;
    }




}
