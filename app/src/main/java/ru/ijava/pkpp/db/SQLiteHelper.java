package ru.ijava.pkpp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import ru.ijava.pkpp.model.ListPersons;
import ru.ijava.pkpp.model.Person;

/**
 * Created by rele on 5/24/17.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pkpp.db";
    private final Context mContext;

    public static class PersonsTable implements BaseColumns {
/*
        SELECT
            p.id,
            p.fioname,
            p.jobname,
            p.title,
            p.location,
            p.workphone,
            p.dect,
            p.mobile,
            p.email,
            d.id " +
                "FROM phonelist AS p, departments AS d WHERE p.department = d.id ORDER BY p.fioname";
*/
        public static final String TABLE_NAME = "persons";
        public static final String COLUMN_FIONAME = "fioname";
        public static final String COLUMN_SURNAME = "surname";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PATRONYMIC = "patronymic";
        public static final String COLUMN_JOBNAME = "jobname";
        public static final String COLUMN_WORKPHONE = "workphone";
        public static final String COLUMN_DECT = "dect";
        public static final String COLUMN_MOBILE = "mobile";
        public static final String COLUMN_EMAIL = "email";

        public static  final String TYPE_PKEY = "INTEGER PRIMARY KEY";
        public static final String TYPE_TEXT = "TEXT";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PersonsTable.TABLE_NAME + " (" +
                    PersonsTable._ID + " " + PersonsTable.TYPE_PKEY + "," +
                    PersonsTable.COLUMN_FIONAME + " " + PersonsTable.TYPE_TEXT + "," +
                    PersonsTable.COLUMN_SURNAME + " " + PersonsTable.TYPE_TEXT + "," +
                    PersonsTable.COLUMN_NAME + " " + PersonsTable.TYPE_TEXT + "," +
                    PersonsTable.COLUMN_PATRONYMIC + " " + PersonsTable.TYPE_TEXT + "," +
                    PersonsTable.COLUMN_JOBNAME + " " + PersonsTable.TYPE_TEXT + "," +
                    PersonsTable.COLUMN_WORKPHONE + " " + PersonsTable.TYPE_TEXT + "," +
                    PersonsTable.COLUMN_DECT + " " + PersonsTable.TYPE_TEXT + "," +
                    PersonsTable.COLUMN_MOBILE + " " + PersonsTable.TYPE_TEXT + "," +
                    PersonsTable.COLUMN_EMAIL + " " + PersonsTable.TYPE_TEXT + ");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PersonsTable.TABLE_NAME;


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void synchronizePersons(ListPersons listPersons) {
        SQLiteDatabase db = this.getWritableDatabase();

        clearDB(db);

        for (Person person : listPersons.getPersons()) {
            insertDB(person, db);
        }

        db.close();
    }

    public ListPersons getAllPersons() {
        SQLiteDatabase db = this.getReadableDatabase();
        ListPersons listPersons = new ListPersons();

        String[] columns = {PersonsTable._ID, PersonsTable.COLUMN_FIONAME, PersonsTable.COLUMN_SURNAME,
                PersonsTable.COLUMN_NAME, PersonsTable.COLUMN_PATRONYMIC, PersonsTable.COLUMN_JOBNAME,
                PersonsTable.COLUMN_WORKPHONE, PersonsTable.COLUMN_DECT, PersonsTable.COLUMN_MOBILE,
                PersonsTable.COLUMN_EMAIL};

        Cursor cursor = db.query(PersonsTable.TABLE_NAME, columns, null, null, null, null, null);

        try {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                listPersons.addPerson(
                        new Person(cursor.getInt(0), cursor.getString(1), cursor.getString(5),
                                cursor.getString(6), cursor.getString(7), cursor.getString(8),
                                cursor.getString(9), 0)
                );
            }
        } finally {
            cursor.close();
        }
        listPersons.setContext(mContext);
        return listPersons;
    }

    private void clearDB(SQLiteDatabase db) {
        db.delete(PersonsTable.TABLE_NAME, null, null);
    }

    private void insertDB(Person person, SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(PersonsTable._ID, person.getId());
        values.put(PersonsTable.COLUMN_FIONAME, person.getFullName());
        values.put(PersonsTable.COLUMN_JOBNAME, person.getPosition());
        values.put(PersonsTable.COLUMN_WORKPHONE, person.getPhone());
        values.put(PersonsTable.COLUMN_DECT, person.getDect());
        values.put(PersonsTable.COLUMN_MOBILE, person.getMobile());
        values.put(PersonsTable.COLUMN_EMAIL, person.getEmail());
        //person.getDepartmentId()

        db.insert(PersonsTable.TABLE_NAME, null, values);
    }
}