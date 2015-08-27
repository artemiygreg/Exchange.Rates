package com.example.example_current.service.preferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.example.example_current.R;
import com.example.example_current.utils.AppMain;
import com.example.example_current.utils.Vars;

/**
 * Created by Admin on 25.08.15.
 */
public class ClientPreferences {
    private SharedPreferences sharedPreferences;
    private static final String NAME_PREFS = "client_references";

    private static class ClientPreferencesHolder {
        private static ClientPreferences clientPreferences = new ClientPreferences();
    }

    public static ClientPreferences getInstance(){
        return ClientPreferencesHolder.clientPreferences;
    }
    private ClientPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AppMain.getContext());
    }

    public void setSortValute(SortValute sortValute){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppMain.getContext().getString(R.string.sortValute), sortValute.name());
        editor.apply();
    }

    public SortValute getSortValute(){
        String sort = sharedPreferences.getString(AppMain.getContext().getString(R.string.sortValute), Vars.ClientPreferences.DEFAULT);
        return SortValute.valueOf(sort);
    }

    public enum SortValute {
        CHAR_CODE, NUM_CODE, NAME, DEFAULT;
    }
}
