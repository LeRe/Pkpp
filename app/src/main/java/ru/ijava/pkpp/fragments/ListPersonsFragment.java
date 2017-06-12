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
import ru.ijava.pkpp.model.Person;

/**
 * Created by rele on 6/11/17.
 */

public class ListPersonsFragment extends ListFragment {
    public static final String KEY_LIST_PERSON = "keyListPerson";

    ListPersons listPersons;

    public ListPersonsFragment() {
        Bundle bundle = getArguments();

        if (bundle != null && bundle.containsKey(KEY_LIST_PERSON)) {
            listPersons = (ListPersons) bundle.getSerializable(KEY_LIST_PERSON);
        }
        else {
            listPersons = new ListPersons();
        }
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Person selectedPerson = (Person) listPersons.getItem(position);
        Intent intent = new Intent(getActivity(), PersonActivity.class);
        intent.putExtra(PersonActivity.SELECTED_PERSON, selectedPerson);
        startActivity(intent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListAdapter(listPersons);
    }

}
