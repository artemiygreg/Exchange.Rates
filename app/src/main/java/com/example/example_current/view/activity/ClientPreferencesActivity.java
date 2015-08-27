package com.example.example_current.view.activity;


import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.example.example_current.R;

public class ClientPreferencesActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.client_preferences);

    }

}
