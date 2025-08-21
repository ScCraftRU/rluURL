package ru.sccraft.urlshortner;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by alexandr on 25.03.17.
 */

public class Fe {
    public static final String LOG_TAG = "Fe";
    FileInputStream fin;
    FileOutputStream fos;
    ContextWrapper a;

    public Fe(Context a) {
        this.a = new ContextWrapper(a.getApplicationContext());
    }

    public void saveFile(String name, String content){

        try {
            fos = a.openFileOutput(name, MODE_PRIVATE);
            fos.write(content.getBytes());
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
        finally{
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public String getFile(String name){
        try {
            fin = a.openFileInput(name);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            Log.d(LOG_TAG, "Из файла получен текст: " + text);
            return text;
        }
        catch(IOException ex) {
            ex.printStackTrace();
            return "File read error";
        }
        finally{

            try{
                if(fin!=null)
                    fin.close();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

    String[] list() {
        return a.fileList();
    }

    public boolean haveFile(String имя_файла) {
        String[] file = list();
        for (int i = 0; i < file.length; i++) {
            if (file[i].equals(имя_файла)) return true;
        }
        return false;
    }
}