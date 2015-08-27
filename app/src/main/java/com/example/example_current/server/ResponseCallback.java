package com.example.example_current.server;

import com.example.example_current.model.Valute;

import java.util.List;

/**
 * Created by Admin on 16.08.15.
 */
public interface ResponseCallback {
    void onListValute(List<Valute> listValute);
    void onSavedDB();
}
