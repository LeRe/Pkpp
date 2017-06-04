package ru.ijava.pkpp.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import ru.ijava.pkpp.model.ListPersons;
import ru.ijava.pkpp.model.Person;

/**
 * Created by rele on 5/29/17.
 */

public class ExportTask  extends AsyncTask<ListPersons, Object, String> {
    private static final String EXPORT_FOLDER = "pkpp_export";
    private static final String EXPORT_CSV_FILE = "contacts.csv";
    private static final String ENCODING_CP1251 = "Cp1251";

    Context context;

    public ExportTask(Context context) {
        this.context = context;
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private File createExportFolder() {
        if (isExternalStorageWritable()) {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), EXPORT_FOLDER);
            if (!file.exists()) {
                file.mkdir();
            }
            return file;
        } else return null;
    }

    private void export2File(String name, String data) {
        if (isExternalStorageWritable()) {
            File file = new File(createExportFolder(), name);

            if (file.exists()) { file.delete(); }

            try {
                file.createNewFile();

                FileOutputStream fileOutputStream;
                fileOutputStream = new FileOutputStream(file);

                //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                //outputStreamWriter.append(data);
                //TODO Почитать уже в конце концов про файловый ввод вывод IO
                //outputStreamWriter.flush();
                //outputStreamWriter.close();

                fileOutputStream.write(data.getBytes(ENCODING_CP1251));

                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void generateFiles(ListPersons listPersons) {
        StringBuilder strCSV = new StringBuilder();

        strCSV.append("\"Имя\",\"Рабочий телефон\",\"Телефон переносной\",\"Домашний телефон\",\"Эл. почта\"\r\n");

        for (Person person : listPersons.getPersons()) {
            if (person.getFullName().equals(null)) continue; //TODO пропускает пользователей с незаполненным полным именем, это не правильно

            strCSV.append("\"" + person.getFullName() + "\",");
            if (person.getPhone() != null && person.getPhone().trim().length() > 0)
                strCSV.append("\"" + person.getPhone() + "\",");
            else
                strCSV.append("\"\",");
            if (person.getDect() != null && person.getDect().trim().length() > 0)
                strCSV.append("\"" + person.getDect() + "\",");
            else
                strCSV.append("\"\",");
            if (person.getMobile() != null && person.getMobile().trim().length() > 0)
                strCSV.append("\"" + person.getMobile() + "\",");
            else
                strCSV.append("\"\",");
            if (person.getEmail() != null && person.getEmail().trim().length() > 0)
                strCSV.append("\"" + person.getEmail() + "\"\r\n");
            else
                strCSV.append("\"\"\r\n");

            String fileNameCSV = person.getId() + ".vcf";
            export2File(fileNameCSV, generateVcf(person));
        }

        export2File(EXPORT_CSV_FILE, strCSV.toString());
    }


    private String generateVcf(Person person) {
        StringBuilder vcf = new StringBuilder();

        vcf.append("BEGIN:VCARD\r\n");
        vcf.append("VERSION:3.0\r\n");
        vcf.append("FN:" + person.getFullName() + "\r\n");
        if (person.getPhone() != null && person.getPhone().trim().length() > 0)
            vcf.append("TEL;TYPE=WORK:" + person.getPhone() + "\r\n");
        if (person.getDect() != null && person.getDect().trim().length() > 0)
            vcf.append("TEL;TYPE=CELL:" + person.getDect() + "\r\n");
        if (person.getMobile() != null && person.getMobile().trim().length() > 0)
            vcf.append("TEL;TYPE=HOME:" + person.getMobile() + "\r\n");
        if (person.getEmail() != null && person.getEmail().trim().length() > 0)
            vcf.append("EMAIL;TYPE=INTERNET:" + person.getEmail() + "\r\n");
        vcf.append("END:VCARD\r\n");

        return vcf.toString();
    }

    @Override
    protected String doInBackground(ListPersons... listPersonses) {
        generateFiles(listPersonses[0]);
        return "Экспорт завершен";
    }


    @Override
    protected void onPostExecute(String string)
    {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }
}
