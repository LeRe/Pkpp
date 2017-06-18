package ru.ijava.pkpp.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import ru.ijava.pkpp.R;
import ru.ijava.pkpp.activities.PersonActivity;
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

        int orientation = getActivity().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {

//            Bundle bundle = new Bundle();
//            bundle.putSerializable(PersonFragment.SELECTED_PERSON, selectedPerson);
//
//            PersonFragment personFragment = new PersonFragment();
//            personFragment.setArguments(bundle);
//
//            getFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_container, personFragment)
//                    .commit();

            replacePersonFragment(selectedPerson);

        }
        else {
            Intent intent = new Intent(getActivity(), PersonActivity.class);
            intent.putExtra(PersonActivity.SELECTED_PERSON, selectedPerson);
            startActivity(intent);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listPersonsAdapter = new ListPersonsAdapter(getActivity().getBaseContext());
        setListAdapter(listPersonsAdapter);

        int orientation = getActivity().getResources().getConfiguration().orientation;
        if (listPersonsAdapter.getCount() > 0 && (orientation == Configuration.ORIENTATION_LANDSCAPE)) {
            replacePersonFragment((Person) listPersonsAdapter.getItem(0));
        }
    }

    private void replacePersonFragment(Person person) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PersonFragment.SELECTED_PERSON, person);

        PersonFragment personFragment = new PersonFragment();
        personFragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_person, personFragment)
                .commit();
    }

}
