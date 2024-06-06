package com.example.do_an_cs3;


import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public final class MemoryData {
    public static void saveData (String data, Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput ("datata.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getData (Context context) {
        String data = "";
        try {
            FileInputStream fis= context.openFileInput ("datata.txt");
            InputStreamReader isr = new InputStreamReader (fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb =  new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) { sb.append(line);{
                sb.append(line);
            }
            }

            data =  sb.toString();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return data;
    }
    public static void savePass(String data, Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput ("passwo.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPass(Context context) {
        String data = "";
        try {
            FileInputStream fis= context.openFileInput ("passwo.txt");
            InputStreamReader isr = new InputStreamReader (fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb =  new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) { sb.append(line);{
                sb.append(line);
            }
            }

            data =  sb.toString();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return data;
    }
}