package ru.ijava.pkpp.model;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;


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
        TextView tvName = new TextView(mContext);
        tvName.setText(persons.get(position).getFullName());

        TextView tvPosition = new TextView(mContext);
        tvPosition.setText(persons.get(position).getPosition());

        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setHorizontalGravity(Gravity.LEFT);

        linearLayout.addView(tvName);
        linearLayout.addView(tvPosition);

        return linearLayout;
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
