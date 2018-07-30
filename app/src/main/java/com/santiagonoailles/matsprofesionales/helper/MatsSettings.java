package com.santiagonoailles.matsprofesionales.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by nahuelul on 8/7/17.
 */

public class MatsSettings {

    private static final String MATSPREFERENCES = "MatsProfesionalesPrefs";
    private static final String PROFESIONALID = "profesionalId";

    public static String getProfessionalId(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(MATSPREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getString(PROFESIONALID, "");
    }

    public static void setProfessionalId(String id, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(MATSPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(PROFESIONALID, id);

        editor.commit();
    }

    public static void saveToken(Context context, String refreshedToken) {
        SharedPreferences sharedPref = context.getSharedPreferences(MATSPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TOKEN", refreshedToken);
        editor.commit();
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(MATSPREFERENCES, Context.MODE_PRIVATE);
        String defaultValue = "";
        String email = sharedPref.getString("TOKEN", defaultValue);

        return email;
    }

    public static void saveEmail(String email, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(MATSPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("EMAIL", email);
        editor.commit();
    }

    public static String getEmail(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(MATSPREFERENCES, Context.MODE_PRIVATE);
        String defaultValue = "";
        String email = sharedPref.getString("EMAIL", defaultValue);

        return email;
    }
}
