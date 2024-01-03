package com.dpworld;

import javassist.Loader;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getDateString(Date date, String format) {
        String rs = "";
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            rs = dateFormat.format(date);
        }
        return rs;
    }

    public static URL getResource(String resource){

        URL url ;

        //Try with the Thread Context Loader.
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if(classLoader != null){
            url = classLoader.getResource(resource);
            if(url != null){
                return url;
            }
        }

        //Let's now try with the classloader that loaded this class.
        classLoader = Loader.class.getClassLoader();
        if(classLoader != null){
            url = classLoader.getResource(resource);
            if(url != null){
                return url;
            }
        }

        //Last ditch attempt. Get the resource from the classpath.
        return ClassLoader.getSystemResource(resource);
    }



}
