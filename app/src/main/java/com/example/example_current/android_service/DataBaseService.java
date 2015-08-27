package com.example.example_current.android_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.example.example_current.model.Valute;
import com.example.example_current.server.ResponseCallback;
import com.example.example_current.server.Server;
import com.example.example_current.service.date.DateService;
import com.example.example_current.utils.StatusValute;
import com.example.example_current.utils.Vars;
import com.example.example_current.view.activity.MainActivity;

import java.util.List;

/**
 * Created by Admin on 16.08.15.
 */
public class DataBaseService extends Service {
    private Server server;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        server = Server.getInstance();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Server server = Server.getInstance();
        server.getCurrency(DateService.getCurrentDate(), new ResponseCallback() {
            @Override
            public void onListValute(List<Valute> listValute) {

            }

            @Override
            public void onSavedDB() {
                Intent broadCastIntent = new Intent(MainActivity.BROAD_CAST_ACTION);
                broadCastIntent.putExtra(Vars.Extra.STATUS_VALUTE, StatusValute.SAVED);
                sendBroadcast(broadCastIntent);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }
}
