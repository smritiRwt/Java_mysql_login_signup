package com.example.android_mysql.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {
    private static SharedPreferenceManager instance;
    private static Context ctx;

    private  static final String SHARED_PREF_NAME ="SHARED_PREF";
    private  static final  String USERNAME="username";
    private  static final  String USER_EMAIL="useremail";
    private  static final  String USER_ID="userid";

    public SharedPreferenceManager(Context context) {
        ctx=context;
    }

    public static synchronized SharedPreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceManager(context);
        }
        return instance;
    }
    public boolean userLogin(int id, String username, String email){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putInt(USER_ID,id);
        editor.putString(USER_EMAIL,email);
        editor.putString(USERNAME,username);
        editor.apply();
        return true;
    }

    public  boolean isLoggedIn(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(USERNAME,null) != null){
            return  true;
        }
        else
        {
            return false;
         }
    }


    public  boolean logOut()
    {
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getUsername(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(USERNAME,null);
    }
    public String getUserEmail(){
        SharedPreferences sharedPreferences=ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_EMAIL,null);
    }

}