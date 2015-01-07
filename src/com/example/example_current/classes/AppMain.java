package com.example.example_current.classes;

import android.app.Application;
import android.content.Context;

/**
 * Created by Artem on 10.12.2014.
 */
public class AppMain extends Application {
    public static volatile Context mContext = null;

    @Override
    public void onCreate(){
        super.onCreate();
        mContext = getApplicationContext();
    }
}
