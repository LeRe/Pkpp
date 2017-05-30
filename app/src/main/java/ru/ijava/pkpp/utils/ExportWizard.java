package ru.ijava.pkpp.utils;


import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ru.ijava.pkpp.model.ListPersons;
import ru.ijava.pkpp.model.Person;


/**
 * Created by rele on 5/29/17.
 */

public class ExportWizard {

    public static final String EXPORT_FOLDER_ = "pkpp_export";
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

        public File createExportFolder(){
            if(isExternalStorageWritable()) {
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS), EXPORT_FOLDER_);
                if(!file.exists()) {
                    file.mkdir();
                }
                return file;
            }
            else return null;
        }

        public void createFile(String name) {
            if(isExternalStorageWritable()) {
                name = "test_file";
                File file = new File(createExportFolder(), name);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String str = "hello, test";
                try {
                    FileOutputStream outputStream;
                    outputStream = context.openFileOutput(file.getName(), context.MODE_APPEND);
                    outputStream.write(str.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }

        public void generateFiles(ListPersons listPersons) {
            this.generateVcf(listPersons);
            this.generateVcfAll(listPersons);
            this.generateCsvAll(listPersons);
        }

        private void generateVcf(ListPersons listPersons)
        {
            for (Person person : listPersons.getPersons()) {
                if(person.getFullName().equals(null)) continue;

                File f=new File(saveFolder + "/vcf/" + person.getId() +".vcf");
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
                if(person.getPhone() != null && person.getPhone().trim().length() > 0)  str.append("TEL;TYPE=WORK:" + person.getPhone() + "\r\n");
                if(person.getDect() != null && person.getDect().trim().length() > 0)   str.append("TEL;TYPE=CELL:" + person.getDect() + "\r\n");
                if(person.getMobile() != null && person.getMobile().trim().length() > 0) str.append("TEL;TYPE=HOME:" + person.getMobile() + "\r\n");
                if(person.getEmail() != null && person.getEmail().trim().length() > 0)  str.append("EMAIL;TYPE=INTERNET:" + person.getEmail() + "\r\n");
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

        private void generateVcfAll(ListPersons listPersons)
        {
            String encoding = "Cp1251";

            File f=new File(saveFolder + "/contacts.vcf");
            FileOutputStream fop = null;
            try {
                fop = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if(f.exists()){

                for (Person person : listPersons.getPersons()) {

                    if(person.getFullName().equals(null)) continue;

                    StringBuilder str = new StringBuilder();
                    str.append("BEGIN:VCARD\r\n");
                    str.append("VERSION:3.0\r\n");
                    str.append("FN:" + person.getFullName() + "\r\n");
                    if(person.getPhone() != null && person.getPhone().trim().length() > 0)  str.append("TEL;TYPE=WORK:" + person.getPhone() + "\r\n");
                    if(person.getDect() != null && person.getDect().trim().length() > 0)   str.append("TEL;TYPE=CELL:" + person.getDect() + "\r\n");
                    if(person.getMobile() != null && person.getMobile().trim().length() > 0) str.append("TEL;TYPE=HOME:" + person.getMobile() + "\r\n");
                    if(person.getEmail() != null && person.getEmail().trim().length() > 0)  str.append("EMAIL;TYPE=INTERNET:" + person.getEmail() + "\r\n");
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

        private void generateCsvAll(ListPersons listPersons)
        {
            String encoding = "Cp1251";

            File f=new File(saveFolder + "/contacts.csv");
            FileOutputStream fop = null;
            try {
                fop = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if(f.exists()){
                StringBuilder str = new StringBuilder();
                str.append("\"Имя\",\"Рабочий телефон\",\"Телефон переносной\",\"Домашний телефон\",\"Эл. почта\"\r\n");

                try {
                    fop.write(str.toString().getBytes(encoding));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (Person person : listPersons.getPersons()) {
                    if(person.getFullName().equals(null)) continue;

                    str = new StringBuilder();

                    //TODO refactory to CSV file
                    str.append("\"" + person.getFullName() + "\",");
                    if(person.getPhone() != null && person.getPhone().trim().length() > 0)
                        str.append("\"" + person.getPhone() + "\",");
                    else
                        str.append("\"\",");
                    if(person.getDect() != null && person.getDect().trim().length() > 0)
                        str.append("\"" + person.getDect() + "\",");
                    else
                        str.append("\"\",");
                    if(person.getMobile() != null && person.getMobile().trim().length() > 0)
                        str.append("\"" + person.getMobile() + "\",");
                    else
                        str.append("\"\",");
                    if(person.getEmail() != null && person.getEmail().trim().length() > 0)
                        str.append("\"" + person.getEmail() + "\"\r\n");
                    else
                        str.append("\"\"\r\n");

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





}
