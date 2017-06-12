package ru.ijava.pkpp.db;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import ru.ijava.pkpp.model.Department;
import ru.ijava.pkpp.model.ListDepartments;
import ru.ijava.pkpp.model.ListPersons;
import ru.ijava.pkpp.model.Person;

/**
 * Created by rele on 5/21/17.
 */
public class MySqlHelper {

    // JDBC URL, username and PASSWORD of MySQL server
    //String URL = "jdbc:mysql://blader.pkpp.net:3306/phonebook";
    //String USER = "root";
    //String PASSWORD = "df[vehrf12";
    // GRANT ALL PRIVILEGES ON phonebook.* TO monty IDENTIFIED BY 'df[vehrf12'

    //private static final String URL = "jdbc:mysql://192.168.100.1:3306/phonebook";
    private static final String URL = "jdbc:mysql://192.168.1.111:3306/phonebook?characterEncoding=cp1251";
    //private static final String URL = "jdbc:mysql://192.168.0.38:3306/phonebook?characterEncoding=cp1251";
    private static final String USER = "monty";
    private static final String PASSWORD = "df[vehrf12";

    // JDBC variables for opening and managing connection
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    private void makeQuery(String query) {
        try {
            connection = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(query);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public ListPersons getAllPersons() {
        ListPersons list = new ListPersons();
        String query = "SELECT p.id, p.fioname, p.jobname, p.title, p.location, p.workphone, p.dect, p.mobile, p.email, d.id " +
                "FROM phonelist AS p, departments AS d WHERE p.department = d.id ORDER BY p.fioname";
        makeQuery(query);
        if(resultSet != null) {
            try {
                while (resultSet.next()) {
                    Person p = new Person();
                    p.setId(resultSet.getInt(1));
                    p.setFullName(resultSet.getString(2));
                    p.setPosition(resultSet.getString(3));
                    p.setPhone(resultSet.getString(6));
                    p.setDect(resultSet.getString(7));
                    p.setMobile(resultSet.getString(8));
                    p.setEmail(resultSet.getString(9));
                    p.setDepartmentId(resultSet.getInt(10));
                    list.addPerson(p);
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        closeDbConnection();
        return list;
    }

    public ListDepartments getAllDepartments() {
//		mysql> select * from departments;
//		+----+--------+----------+
//		| id | parent | name     |
//		+----+--------+----------+
//		|  1 |      0 | default  |
//		|  2 |      0 | infotech |
//		|  3 |      0 | staff    |
//		+----+--------+----------+
//		3 rows in set (0.00 sec)
        ListDepartments list = new ListDepartments();
        String query = "SELECT d.id, d.parent, d.name FROM departments AS d";
        makeQuery(query);
        if(resultSet!=null) {
            try {
                while (resultSet.next()) {
                    list.addDepartment(new Department(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3)));
                }

            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
        }
        closeDbConnection();
        return list;
    }

    public ListPersons getPersonsByDepartment(String departmentName) {
        ListPersons list = new ListPersons();
        //departmentName = "staff";
        //departmentName = "";
        String parthQuery;
        if (!departmentName.equals(""))
        {
            parthQuery =  "and d.name = '" + departmentName + "'";
        }
        else
        {
            parthQuery = "";
        }
        String query = "SELECT p.id, p.fioname, p.jobname, p.title, p.location, p.workphone, p.dect, p.mobile, p.email, d.id " +
                "FROM phonelist AS p, departments AS d WHERE p.department = d.id " + parthQuery + " ORDER BY p.fioname";
        makeQuery(query);
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Person p = new Person();
                    p.setId(resultSet.getInt(1));
                    p.setFullName(resultSet.getString(2));
                    p.setPosition(resultSet.getString(3));
                    p.setPhone(resultSet.getString(6));
                    p.setDect(resultSet.getString(7));
                    p.setMobile(resultSet.getString(8));
                    p.setEmail(resultSet.getString(9));
                    p.setDepartmentId(resultSet.getInt(10));
                    list.addPerson(p);
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
        }
        closeDbConnection();
        return list;
    }

    public void deletePerson(Person person) {
        String query = "DELETE FROM phonelist where id = " + person.getId();
        try {
            connection = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
            statement = (Statement) connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            closeDbConnection();
        }
    }

    public void updatePerson(Person person) {

        String query = "UPDATE phonelist SET " +
                "fioname = '" + person.getFullName() + "', " +
                "jobname = '" + person.getPosition() + "', " +
                //"title = '" + person + "', " +
                //"location = '" + person + "', " +
                "workphone = '" + person.getPhone() + "', " +
                "dect = '" + person.getDect() + "', " +
                "mobile = '" + person.getMobile() + "', " +
                "email = '" + person.getEmail() + "' " +
                "where id = " + person.getId();

        try {
            connection = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
            statement = (Statement) connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            closeDbConnection();
        }
    }

    private void closeDbConnection() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
            if (statement != null) {
                statement.close();
                statement = null;

            }
            if (resultSet != null) {
                resultSet.close();
                resultSet = null;
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
