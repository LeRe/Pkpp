package ru.ijava.pkpp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

        TextView fio = (TextView) findViewById(R.id.fio);
        fio.setText(person.getFullName());

        TextView position = (TextView) findViewById(R.id.position);
        position.setText(person.getPosition());

        TextView phone = (TextView) findViewById(R.id.phone);
        phone.setText(person.getPhone());

        TextView dect = (TextView) findViewById(R.id.dect);
        dect.setText(person.getDect());

        TextView mobile = (TextView) findViewById(R.id.mobile);
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

        TextView tvEmail = (TextView) findViewById(R.id.email);
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

}
