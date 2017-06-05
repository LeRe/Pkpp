package ru.ijava.pkpp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import ru.ijava.pkpp.db.MySqlHelper;
import ru.ijava.pkpp.db.SQLiteHelper;
import ru.ijava.pkpp.model.ListPersons;

/**
 * Created by rele on 5/22/17.
 */

public class SynchronizeDbTask extends AsyncTask<Object, Object, String> {
    private Context mContext;

    public SynchronizeDbTask(Context context){
        mContext = context;
    }

    @Override
    protected String doInBackground(Object... params) {
        StringBuilder log = new StringBuilder();

        MySqlHelper mySqlHelper = new MySqlHelper();
        ListPersons listPersons = mySqlHelper.getAllPersons();
        SQLiteHelper sqLiteHelper = new SQLiteHelper(mContext);
        sqLiteHelper.synchronizePersons(listPersons);

        log.append("Попытка синхронизации баз выполнена. \n" +
                Integer.toString(listPersons.getPersons().size()) + " записей прочитано");

        return log.toString();
    }

    @Override
    protected void onPostExecute(String log)
    {
        Toast.makeText(mContext, log,Toast.LENGTH_SHORT).show();
    }
}
