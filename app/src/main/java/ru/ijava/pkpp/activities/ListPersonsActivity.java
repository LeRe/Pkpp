package ru.ijava.pkpp.activities;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import ru.ijava.pkpp.R;
import ru.ijava.pkpp.db.SQLiteHelper;
import ru.ijava.pkpp.fragments.ListPersonsFragment;
import ru.ijava.pkpp.model.ListPersons;
import ru.ijava.pkpp.utils.ExportTask;
import ru.ijava.pkpp.utils.SynchronizeDbTask;

/**
 * Created by rele on 5/26/17.
 *
 *  TODO Актуализировать экспортируемое в файлы, провести экспорт в оутлук, посмотреть результат
 *  TODO и принять решение, что необходимо подправить
 *
 */
public class ListPersonsActivity extends AppCompatActivity {
    private final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    ListPersons listPersons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_persons);



        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_list,new ListPersonsFragment())
                .commit();

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
                (new SynchronizeDbTask(this)).execute(new Object());
                return true;
            case R.id.export_files:
                int permissionCheck = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if(permissionCheck != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
                else {
                    runExportTask();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    runExportTask();
                }
                return;
            }
        }
    }

    private void runExportTask() {
        if (listPersons == null) {
            SQLiteHelper sqLiteHelper = new SQLiteHelper(this);
            listPersons = sqLiteHelper.getAllPersons();
        }
        (new ExportTask(this)).execute(listPersons);
    }

    public void updateListPersonFragment() {

        //Fragment fragment = getFragmentManager().findFragmentById(R.id.listPersonsFragment);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_list, new ListPersonsFragment())
                .commit();
    }
}