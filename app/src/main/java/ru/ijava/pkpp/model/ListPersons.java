package ru.ijava.pkpp.model;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import ru.ijava.pkpp.R;
import ru.ijava.pkpp.utils.PersonParser;


/**
 * Created by rele on 5/21/17.
 */
public class ListPersons {
    Context mContext;
    public void setContext(Context context)
    {
        this.mContext = context;
    }

    private ArrayList<Person> persons;

    public ListPersons()
    {
        persons = new ArrayList<Person>();
    }

    public void addPerson(Person p)
    {
        persons.add(p);
    }

    public ArrayList<Person> getPersons()
    {
        return this.persons;
    }
}
