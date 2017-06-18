package ru.ijava.pkpp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.ijava.pkpp.R;
import ru.ijava.pkpp.model.Person;

/**
 * Created by rele on 6/11/17.
 */

public class PersonFragment extends Fragment {
    public static final String SELECTED_PERSON = "selected_person";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_person, container, false);


        Bundle bundle = getArguments();
        if (bundle != null) {
            Person person = (Person) bundle.getSerializable(SELECTED_PERSON);

            TextView fio = (TextView) view.findViewById(R.id.fio);
            fio.setText(person.getFullName());

            int orientation = getActivity().getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT)
            {
                TextView position = (TextView) view.findViewById(R.id.position);
                position.setText(person.getPosition());
            }

            TextView phone = (TextView) view.findViewById(R.id.phone);
            phone.setText(person.getPhone());

            TextView dect = (TextView) view.findViewById(R.id.dect);
            dect.setText(person.getDect());

            TextView mobile = (TextView) view.findViewById(R.id.mobile);
            String phoneNumber = PhoneNumberUtils.formatNumberToE164("" + person.getMobile(), "RU");
            mobile.setText(person.getMobile());
            if(PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)){
                mobile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("tel:" + phoneNumber);
                        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                        startActivity(intent);
                    }
                });
            }

            TextView tvEmail = (TextView) view.findViewById(R.id.email);
            String email = person.getEmail();
            if(email != null) {
                tvEmail.setText(email);
                tvEmail.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Uri uri = Uri.parse("mailto:" + email)
                                        .buildUpon()
                                        .build();

                                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                                startActivity(Intent.createChooser(intent, "Send Email"));
                            }
                        }
                );
            }


        }

        return view;
    }

    /*


     */





}
