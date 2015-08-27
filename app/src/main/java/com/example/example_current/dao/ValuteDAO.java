package com.example.example_current.dao;

import android.database.sqlite.SQLiteDatabase;
import com.example.example_current.model.Valute;
import com.example.example_current.server.ResponseCallback;

import java.util.List;

/**
 * Created by Admin on 16.08.15.
 */
public interface ValuteDAO {
    void insertValute(List<Valute> valuteList, ResponseCallback responseCallback);
    List<Valute> getListValute();
    List<Valute> getListFavoriteValute();
    void addToFavorite(int numCode, String charCode, String idValute);
    void removeFromFavorite(int numCode);
    SQLiteDatabase getDbRead();
    SQLiteDatabase getDbWrite();
}
