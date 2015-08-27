package com.example.example_current.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.example.example_current.utils.StatusValute;
import com.example.example_current.utils.Vars;

/**
 * Created by Admin on 23.08.15.
 */
public class MyReceiver extends BroadcastReceiver {
    private StatusValuteCallback statusValuteCallback;

    public MyReceiver(StatusValuteCallback statusValuteCallback) {
        this.statusValuteCallback = statusValuteCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("onReceive", "launch");
        StatusValute statusValute = (StatusValute)intent.getSerializableExtra(Vars.Extra.STATUS_VALUTE);
        switch (statusValute){
            case DOWNLOADED:
                statusValuteCallback.onDownloaded();
                break;
            case IN_PROCESSING:
                statusValuteCallback.inProcessing();
                break;
            case SAVED:
                Log.e("statusValute", "saved");
                statusValuteCallback.saved();
                break;
            case NOT_ACCESS:
                statusValuteCallback.notAccess();
            default:
                break;
        }
    }
    public interface StatusValuteCallback {
        void onDownloaded();
        void inProcessing();
        void saved();
        void notAccess();
    }
}
