package com.sonderben.sdbvideo.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private static SharedPreferences tokenPreferences;
    private static SharedPreferences profilePreferences;
    private static SharedPreferences emailUserPreferences;
    //private static SharedPreferences isMAinProfilePreference;


    private Context context;
    private static volatile Preferences preferences;


    private Preferences(Context context) {
        this.context = context;
        tokenPreferences= context.getSharedPreferences("TOKEN",Context.MODE_PRIVATE);
        profilePreferences = context.getSharedPreferences("ID_PROFILE",context.MODE_PRIVATE);
        emailUserPreferences= context.getSharedPreferences("EMAIL_USER",Context.MODE_PRIVATE);
        //isMAinProfilePreference= context.getSharedPreferences("IS_MAIN",Context.MODE_PRIVATE);
    }
    public static Preferences getPreferenceInstance(Context context){
        if(preferences==null){
            preferences=new Preferences(context);
        }
        return preferences;
    }
    public  void setTokenPreferences(String token){
        SharedPreferences.Editor editor=tokenPreferences.edit();
        editor.putString("TOKEN", token);
        editor.apply();
        editor.commit();
    }
    public String getToken(){
       return tokenPreferences.getString("TOKEN","");
    }


    public  void setIdProfile(Long idProfile){
        SharedPreferences.Editor editor= profilePreferences.edit();
        editor.putLong("ID_PROFILE", idProfile);
        editor.apply();
        editor.commit();
    }
    public Long getIdProfile(){
        return profilePreferences.getLong("ID_PROFILE",0L);
    }

    public  void setEmailUserPreferences(String email){
        SharedPreferences.Editor editor=emailUserPreferences.edit();
        editor.putString("EMAIL_USER", email);
        editor.apply();
        editor.commit();
    }
    public String getEmailUser(){
        return emailUserPreferences.getString("EMAIL_USER","");
    }

    public  void setIsMainProfilesPreferences(Boolean isMain){
        SharedPreferences.Editor editor= profilePreferences.edit();
        editor.putBoolean("IS_MAIN", isMain);
        editor.apply();
        editor.commit();
    }
    public boolean getIsMainProfilesPreferences(){
        return profilePreferences.getBoolean("IS_MAIN",false);
    }
}
