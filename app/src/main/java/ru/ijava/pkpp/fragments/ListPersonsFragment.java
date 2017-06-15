package ru.ijava.pkpp.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ru.ijava.pkpp.R;
import ru.ijava.pkpp.activities.PersonActivity;
import ru.ijava.pkpp.model.ListPersons;
import ru.ijava.pkpp.model.ListPersonsAdapter;
import ru.ijava.pkpp.model.Person;

/**
 * Created by rele on 6/11/17.
 */

public class ListPersonsFragment extends ListFragment {
    ListPersonsAdapter listPersonsAdapter;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Person selectedPerson = (Person) listPersonsAdapter.getItem(position);
        Intent intent = new Intent(getActivity(), PersonActivity.class);
        intent.putExtra(PersonActivity.SELECTED_PERSON, selectedPerson);
        startActivity(intent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listPersonsAdapter = new ListPersonsAdapter(getActivity().getBaseContext());
        setListAdapter(listPersonsAdapter);
    }

}
