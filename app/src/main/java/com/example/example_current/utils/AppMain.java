package com.example.example_current.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by Artem on 10.12.2014.
 */
public class AppMain extends Application {
    private static volatile Context mContext;

    @Override
    public void onCreate(){
        super.onCreate();
        mContext = getApplicationContext();
    }
    public static Context getContext(){
        return mContext;
    }
}
