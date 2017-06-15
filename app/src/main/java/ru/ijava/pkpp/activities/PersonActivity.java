package ru.ijava.pkpp.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ru.ijava.pkpp.R;
import ru.ijava.pkpp.fragments.PersonFragment;
import ru.ijava.pkpp.model.Person;

/**
 * Created by rele on 5/27/17.
 */

public class PersonActivity extends AppCompatActivity {
    public static final String SELECTED_PERSON = "selected_person";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        Intent intent = getIntent();
        Person person = (Person) intent.getSerializableExtra(SELECTED_PERSON);

        Bundle bundle = new Bundle();
        bundle.putSerializable(PersonFragment.SELECTED_PERSON, person);

        PersonFragment personFragment = new PersonFragment();
        personFragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, personFragment)
                .commit();
    }
}
