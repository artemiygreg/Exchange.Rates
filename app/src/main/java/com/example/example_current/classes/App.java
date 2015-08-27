package com.example.example_current.classes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Handler;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Арем on 11.09.14.
 */
public class App {
    private static App instance;
    private static Context context;
    private TextView tv_result;
    private Handler h;
    public ProgressDialog progressDialog;
    public static Resources resources = null;
    private static AlertDialog alert = null;
    public static HashMap<String, Object> current;
    public static HashMap<String, Object> currentHashMap;
    public static ArrayList<HashMap<String, Object>> currentArray;
    public static final String NAME = "name";
    public static final String VALUE = "value";

    public App(Context context){
        this.context = context;
        instance = this;
        resources = context.getResources();

    }
    public static Context getContext(){
        return context;
    }

    public static void initInstance(Context context) {
        if (instance == null)
        {
            instance = new App(context);
        }
    }
    public static String getStringFromResources(int id){
        return resources.getString(id);
    }

    public static App getInstance()
    {
        return instance;
    }
    
    public void Alert(String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg).
        setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        builder.show();
    }
    public static void alertLoaderStart(AnimationLoader animationLoader){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        alert = builder.create();
        alert.show();
        alert.setContentView(new AnimationLoader(context));
/*        alert.setContentView(R.layout.alert_load);
        LinearLayout linearLayout = (LinearLayout)alert.findViewById(R.id.animation);
        linearLayout.addView(animationLoader);*/
    }
    public static void alertLoaderStop(){
        alert.cancel();
    }
}
