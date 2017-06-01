package ru.ijava.pkpp.utils;


import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import ru.ijava.pkpp.model.ListPersons;
import ru.ijava.pkpp.model.Person;


/**
 * Created by rele on 5/29/17.
 */

public class ExportWizard {
    private static final String EXPORT_FOLDER = "pkpp_export";
    private static final String EXPORT_CSV_FILE = "contacts.csv";
    private static final String ENCODING_CP1251 = "Cp1251";

    Context context;

    private static String saveFolder = "contact_files";

    public ExportWizard(Context context) {
        this.context = context;
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public File createExportFolder() {
        if (isExternalStorageWritable()) {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), EXPORT_FOLDER);
            if (!file.exists()) {
                file.mkdir();
            }
            return file;
        } else return null;
    }

    public void export2File(String name, String data) {
        if (isExternalStorageWritable()) {
            File file = new File(createExportFolder(), name);

            if (file.exists()) {
                file.delete();
            }

            //TODO Проверить выдачу разрешения на доступ к записи во внешнюю память и ели отсутствует сделать запрос прав

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

    public void generateFiles(ListPersons listPersons) {
        //this.generateVcf(listPersons);
        //this.generateVcfAll(listPersons);

        export2File(EXPORT_CSV_FILE, generateCsvAll(listPersons));
        //TODO remove old string this.generateCsvAll(listPersons);
    }

    private void generateVcf(ListPersons listPersons) {
        for (Person person : listPersons.getPersons()) {
            if (person.getFullName().equals(null)) continue;

            File f = new File(saveFolder + "/vcf/" + person.getId() + ".vcf");
            FileOutputStream fop = null;
            try {
                fop = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            StringBuilder str = new StringBuilder();
            str.append("BEGIN:VCARD\r\n");
            str.append("VERSION:3.0\r\n");
            str.append("FN:" + person.getFullName() + "\r\n");
            if (person.getPhone() != null && person.getPhone().trim().length() > 0)
                str.append("TEL;TYPE=WORK:" + person.getPhone() + "\r\n");
            if (person.getDect() != null && person.getDect().trim().length() > 0)
                str.append("TEL;TYPE=CELL:" + person.getDect() + "\r\n");
            if (person.getMobile() != null && person.getMobile().trim().length() > 0)
                str.append("TEL;TYPE=HOME:" + person.getMobile() + "\r\n");
            if (person.getEmail() != null && person.getEmail().trim().length() > 0)
                str.append("EMAIL;TYPE=INTERNET:" + person.getEmail() + "\r\n");
            str.append("END:VCARD\r\n");

            try {
                fop.write(str.toString().getBytes("Cp1251"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //close the output stream and buffer reader
            try {
                fop.flush();
                fop.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateVcfAll(ListPersons listPersons) {
        String encoding = "Cp1251";

        File f = new File(saveFolder + "/contacts.vcf");
        FileOutputStream fop = null;
        try {
            fop = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (f.exists()) {

            for (Person person : listPersons.getPersons()) {

                if (person.getFullName().equals(null)) continue;

                StringBuilder str = new StringBuilder();
                str.append("BEGIN:VCARD\r\n");
                str.append("VERSION:3.0\r\n");
                str.append("FN:" + person.getFullName() + "\r\n");
                if (person.getPhone() != null && person.getPhone().trim().length() > 0)
                    str.append("TEL;TYPE=WORK:" + person.getPhone() + "\r\n");
                if (person.getDect() != null && person.getDect().trim().length() > 0)
                    str.append("TEL;TYPE=CELL:" + person.getDect() + "\r\n");
                if (person.getMobile() != null && person.getMobile().trim().length() > 0)
                    str.append("TEL;TYPE=HOME:" + person.getMobile() + "\r\n");
                if (person.getEmail() != null && person.getEmail().trim().length() > 0)
                    str.append("EMAIL;TYPE=INTERNET:" + person.getEmail() + "\r\n");
                str.append("END:VCARD\r\n");

                try {
                    fop.write(str.toString().getBytes(encoding));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        //close the output stream and buffer reader
        try {
            fop.flush();
            fop.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateCsvAll(ListPersons listPersons) {
        StringBuilder str = new StringBuilder();

        str.append("\"Имя\",\"Рабочий телефон\",\"Телефон переносной\",\"Домашний телефон\",\"Эл. почта\"\r\n");

        for (Person person : listPersons.getPersons()) {
            if (person.getFullName().equals(null)) continue; //TODO пропускает пользователей с незаполненным полным именем, это не правильно

            str.append("\"" + person.getFullName() + "\",");
            if (person.getPhone() != null && person.getPhone().trim().length() > 0)
                str.append("\"" + person.getPhone() + "\",");
            else
                str.append("\"\",");
            if (person.getDect() != null && person.getDect().trim().length() > 0)
                str.append("\"" + person.getDect() + "\",");
            else
                str.append("\"\",");
            if (person.getMobile() != null && person.getMobile().trim().length() > 0)
                str.append("\"" + person.getMobile() + "\",");
            else
                str.append("\"\",");
            if (person.getEmail() != null && person.getEmail().trim().length() > 0)
                str.append("\"" + person.getEmail() + "\"\r\n");
            else
                str.append("\"\"\r\n");
        }

        return str.toString();
    }
}
