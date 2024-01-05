package com.dpworld;

import javassist.Loader;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    public static String getControlNumber(){
        String rs = "";
        Date today = new Date();
        SimpleDateFormat dateFormat = null;
        try {
            dateFormat = new SimpleDateFormat("yyMMdd");
            String yyMMdd = dateFormat.format(today);
            dateFormat = new SimpleDateFormat("mmssSS");
            String HHmmssSS = dateFormat.format(today);
            rs = String.format("%s%s",yyMMdd,HHmmssSS);
        }catch (Exception ex){
            System.out.println("Method getControlNumber " + ex.getMessage());
        }

        return rs;
    }

    public static String getFilename(Invoice invoice) {
        String rs ="";
        Long draftNbr = invoice.get_draft_nbr();
        String lineOper = invoice.get_payeeid();
        String vesselVoyage = invoice.get_vessel_visit_id();
        if (vesselVoyage != null && vesselVoyage.length() > 4) {
            vesselVoyage = vesselVoyage.substring(vesselVoyage.length() - 4);
        }
        rs = String.format("%s%s%s",lineOper,vesselVoyage,draftNbr);

        return rs;
    }


}
