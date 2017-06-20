package ru.ijava.pkpp.utils;

import android.util.Log;

import java.util.StringTokenizer;

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
    String fullName;

    public PersonParser()//ListPersons listPersons)
    {
        //объекту при создании придется обучиться  для этого переберем переданный
        // в конструктор listPersons  и посмотрим что он содержит внутри

        //for (Person person : listPersons.getPersons()) {
        //    Log.i("RELE", person.getFullName());
        //}
    }

    public PersonParser(String fullName) {
        this.fullName = fullName;
    }

    public int wordCount(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str);
        return stringTokenizer.countTokens();
    }

    public String getSurname() {
        checkNullFullName();
        return getSurname(this.fullName);
    }

    public String getSurname(String fullName) {
        StringTokenizer stringTokenizer = new StringTokenizer(fullName);

        return stringTokenizer.nextToken();
    }

    public String getName() {
        checkNullFullName();
        return getName(this.fullName);
    }

    public String getName(String fullName) {
        StringTokenizer stringTokenizer = new StringTokenizer(fullName);
        stringTokenizer.nextToken();
        return stringTokenizer.nextToken();
    }

    public String getPatronymic() {
        checkNullFullName();
        return getPatronymic(this.fullName);
    }

    public String getPatronymic(String fullName) {
        StringTokenizer stringTokenizer = new StringTokenizer(fullName);
        stringTokenizer.nextToken();
        stringTokenizer.nextToken();
        return stringTokenizer.nextToken();
    }

    public String getNameLetter(String fullName) {
        return getName(fullName).trim().substring(0, 1);
    }

    public String getPatronymicLetter(String fullName) {
        return getPatronymic(fullName).trim().substring(0, 1);
    }

    public String  getShortName() {
        return getShortName(fullName);
    }

    public String getShortName(String fullName) {
        StringBuilder sb = new StringBuilder();
        fullName = dropBrackets(fullName);
        if (isCorrectNameSurnamePatronymic(fullName)) {
            sb.append(getSurname(fullName))
                    .append(" ")
                    .append(getNameLetter(fullName))
                    .append(". ")
                    .append(getPatronymicLetter(fullName))
                    .append(".");
        }
        return sb.toString();
    }

    public String dropBrackets(String fullName) {
        return fullName.replaceAll("\\(.+\\)", "");
    }

    public boolean isCorrectNameSurnamePatronymic() {
        return isCorrectNameSurnamePatronymic(fullName);
    }

    public boolean isCorrectNameSurnamePatronymic(String fullName) {
        boolean result = false;

        fullName = dropBrackets(fullName);

        result = wordCount(fullName) == 3;
        if (result) {
            result = isFirstLettersBig(fullName);
        }

        return result;
    }

    public boolean isFirstLettersBig(String fullName) {
        boolean result = true;
        StringTokenizer stringTokenizer = new StringTokenizer(fullName);
        while (stringTokenizer.hasMoreTokens()) {
            if (Character.getType(stringTokenizer.nextToken().charAt(0)) == Character.LOWERCASE_LETTER) {
                result = false;
            }
        }
        return result;
    }

    private void checkNullFullName() {
        if (this.fullName == null) {
            this.fullName = "Полное Имя Пустое";
        }
    }

}
