package com.enchere.utils;

import java.security.MessageDigest;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

public class Utils {


    public static Time toTime(String valeur) {
        return Time.valueOf(valeur.split("\\.")[0]);
    }

    public static Timestamp toTimestamp(String valeur) {
        System.out.println("aaaaaaaaaaaaaaaaaaa = " + castTimeStamp(valeur));
//        return Timestamp.valueOf(castTimeStamp(valeur));
        return Timestamp.valueOf(valeur);
    }

    public static String castTimeStamp(String s) {
        s = s.replace("T", " ");
        s = s + ":00";
        return s;
    }

    public static java.util.Date toDate(String valeur) throws ParseException {
        try {
            valeur = valeur.replace("-", "/");
            String[] splitValeur = valeur.split("/");
            if (splitValeur[0].length()==4) {
                valeur = splitValeur[2] + splitValeur[1] + splitValeur[0];
                System.out.println("valeur = " + valeur);
            }
            valeur = valeur.replace("/", "");
            return (java.util.Date) new SimpleDateFormat("ddMMyyyy").parse(valeur);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static Date toDateSql(String valeur) throws ParseException {
        try {
            java.util.Date parsed = toDate(valeur);
            return new Date(parsed.getTime());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    //    Token part
    public static String encrypter(String valeur){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(valeur.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Timestamp getDateExpiration(int minute){
        Timestamp time;
        java.util.Date datecurr = new java.util.Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(datecurr);
        cal.add(Calendar.MINUTE, minute);
        java.util.Date newDate = cal.getTime();
        time = new Timestamp(newDate.getTime());
        return time;
    }
    public static String creationToken(String username, String password) throws Exception{
        String token = "";
        String motCache ="CleCache";
        String valeur = username+motCache+password;
        token = encrypter(valeur);
        return token;
    }
}
