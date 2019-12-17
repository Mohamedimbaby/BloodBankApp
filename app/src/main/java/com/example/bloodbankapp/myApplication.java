package com.example.bloodbankapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.backendless.Backendless;

public class myApplication extends Application {
  public static SharedPreferences preferences ;
    public static SharedPreferences.Editor editor;
    private static AppCompatActivity context;

    public static void backEnd_intial(AppCompatActivity c){
context=c;
        Backendless.initApp(c,"7ACCF0C8-58A2-FF8D-FF37-A2A5B3DC1F00","BE883DE0-6447-4A32-BBBC-926C6D5B835B");
        intial_preference();
}

    public static void intial_preference( ) {

        preferences=context.getPreferences(MODE_PRIVATE);
        editor=preferences.edit();

    }
}
