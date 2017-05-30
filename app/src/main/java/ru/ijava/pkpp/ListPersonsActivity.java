package ru.ijava.pkpp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;

import ru.ijava.pkpp.db.SQLiteHelper;
import ru.ijava.pkpp.model.ListPersons;
import ru.ijava.pkpp.model.Person;
import ru.ijava.pkpp.utils.ExportWizard;

/**
 * Created by rele on 5/26/17.
 *
 *  TODO В списке должно выводиться фамилия, инициалы и должность, или только должность если ФИО неизвестны
 *  TODO Можно попробовать выводить должность под ФИО другим цветом/шрифтом, чтобы отличалось от ФИО
 *
 *  TODO В базе необходимо раздельно хранить фамилию, имя и отчество (для последующего экспорта в оутлук)
 *  TODO а вот видимое имя, состоящее из фамилии и инициалов будем формировать при создании объекта и хранить в свойстве объекта
 *
 *  TODO Сделать нажимаемыми сотовый телефон  и емайл, отработать реакцию на них - делать звонки, создавать письма
 *      TODO необходимы валидатовы телефонных номеров и почтовых адресов
 *
 *  TODO Синхронизация баз, необходимо реализовать проверку есть ли изменения данных и по результатам проверки вносить изменения в sqlite
 *
 */
public class ListPersonsActivity extends AppCompatActivity {
    ListPersons listPersons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_persons);

        SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
        listPersons = sqLiteHelper.getAllPersons();

        Collections.sort(listPersons.getPersons(),
                new Comparator<Person>() {
                    @Override
                    public int compare(Person o1, Person o2) {
                        return o1.getFullName().compareTo(o2.getFullName());
                    }
                }
        );

        ListView listPersonsView = (ListView) findViewById(R.id.listPersonsView);
        listPersonsView.setAdapter(listPersons);
        listPersonsView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Person selectedPerson = (Person) listPersons.getItem(position);
                        Intent intent = new Intent(getApplicationContext(), PersonActivity.class);
                        intent.putExtra(PersonActivity.SELECTED_PERSON, selectedPerson);
                        startActivity(intent);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.synchronize_db:
                (new SynchronizeDbTask(getApplicationContext())).execute(new Object());
                return true;
            case R.id.export_files:
                (new ExportWizard(getApplicationContext())).createFile("aaa");
                return true;
            case R.id.settings:
                Toast.makeText(getApplicationContext(), "Настройки", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}