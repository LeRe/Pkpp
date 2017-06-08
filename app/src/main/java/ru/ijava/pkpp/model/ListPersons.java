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

import java.util.ArrayList;

import ru.ijava.pkpp.R;
import ru.ijava.pkpp.utils.PersonParser;


/**
 * Created by rele on 5/21/17.
 */
public class ListPersons implements ListAdapter {
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

        View view = View.inflate(mContext, R.layout.view_list_persons, null);

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
