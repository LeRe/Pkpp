package ru.ijava.pkpp.model;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ru.ijava.pkpp.R;
import ru.ijava.pkpp.db.SQLiteHelper;
import ru.ijava.pkpp.utils.PersonParser;

/**
 * Created by rele on 6/13/17.
 */

public class ListPersonsAdapter implements ListAdapter {
    private Context context;
    private ArrayList<Person> persons;

    public ListPersonsAdapter(Context context) {
        this.context = context;

        SQLiteHelper sqLiteHelper = new SQLiteHelper(this.context);
        ListPersons listPersons = sqLiteHelper.getAllPersons();

        Collections.sort(listPersons.getPersons(),
                new Comparator<Person>() {
                    @Override
                    public int compare(Person o1, Person o2) {
                        return o1.getFullName().compareTo(o2.getFullName());
                    }
                }
        );

        this.persons = listPersons.getPersons();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public int getCount() {
        return this.persons.size();
    }

    @Override
    public Object getItem(int position) {
        return persons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return persons.get(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(context, R.layout.view_list_persons, null);

        TextView tvName = (TextView) view.findViewById(R.id.namePerson);
        TextView tvPosition = (TextView) view.findViewById(R.id.positionPerson);

        PersonParser personParser = new PersonParser(persons.get(position).getFullName());

        String displayName;
        if (personParser.isCorrectNameSurnamePatronymic()) {
            displayName = personParser.getShortName();
        }
        else {
            displayName = persons.get(position).getFullName();
        }
        tvName.setText(displayName);
        tvPosition.setText(persons.get(position).getPosition());

        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return persons.size() == 0;
    }
}