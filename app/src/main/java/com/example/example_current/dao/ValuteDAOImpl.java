package com.example.example_current.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.example_current.model.Valute;
import com.example.example_current.server.ResponseCallback;
import com.example.example_current.utils.Vars;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 16.08.15.
 */
public class ValuteDAOImpl extends SQLiteOpenHelper implements ValuteDAO {
    private Context context;
    private SQLiteDatabase sqLiteDatabaseWrite;
    private SQLiteDatabase sqLiteDatabaseRead;
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "ValuteDB";
    private static final String TABLE_NAME_VALUTE = "valutes";
    private static final String TABLE_NAME_FAVORITE = "favorites";
    private static final String ID = "id";
    private static final String SORT_BY_CHAR_CODE = Vars.ParamsDB.CHAR_CODE + " desc";
    private static final String SORT_BY_NAME = Vars.ParamsDB.NAME + " desc";
    private static final String SORT_BY_NUM_CODE = Vars.ParamsDB.NUM_CODE + " desc";
    private static final String CREATE_TABLE_VALUTES = "create table " + TABLE_NAME_VALUTE + " ( " +
            ID + " integer primary key autoincrement, " +
            Vars.ParamsDB.ID_VALUTE + " text, " +
            Vars.ParamsDB.NUM_CODE + " integer, " +
            Vars.ParamsDB.CHAR_CODE + " text, " +
            Vars.ParamsDB.NOMINAL + " integer, " +
            Vars.ParamsDB.VALUE + " text, " +
            Vars.ParamsDB.NAME + " text)";
    private static final String CREATE_TABLE_FAVORITE = "create table " + TABLE_NAME_FAVORITE + " ( " +
            ID + " integer primary key autoincrement, " +
            Vars.ParamsDB.NUM_CODE + " integer, " +
            Vars.ParamsDB.CHAR_CODE + " text, " +
            Vars.ParamsDB.ID_VALUTE + " text)";

    public static final String DELETE_TABLE_VALUTE = "drop table if exists " + TABLE_NAME_VALUTE;
    public static final String DELETE_TABLE_FAVORITE = "drop table if exists " + TABLE_NAME_FAVORITE;

    public ValuteDAOImpl(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        sqLiteDatabaseRead = getReadableDatabase();
        sqLiteDatabaseWrite = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_VALUTES);
        db.execSQL(CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE_VALUTE);
        db.execSQL(DELETE_TABLE_FAVORITE);
        onCreate(db);
    }
    @Override
    public void insertValute(List<Valute> valuteList, ResponseCallback responseCallback) {
        ContentValues contentValues = new ContentValues();
        for(int i = 0; i < valuteList.size(); i++){
            Valute valute = valuteList.get(i);
            if(!issetValute(TABLE_NAME_VALUTE, valute.getNumCode())) {
                contentValues.put(Vars.ParamsDB.ID_VALUTE, valute.getId());
                contentValues.put(Vars.ParamsDB.NUM_CODE, valute.getNumCode());
                contentValues.put(Vars.ParamsDB.CHAR_CODE, valute.getCharCode());
                contentValues.put(Vars.ParamsDB.NOMINAL, valute.getNominal());
                contentValues.put(Vars.ParamsDB.VALUE, valute.getValue());
                contentValues.put(Vars.ParamsDB.NAME, valute.getName());
                long row = sqLiteDatabaseWrite.insert(TABLE_NAME_VALUTE, null, contentValues);
            }
              if(i == valuteList.size() - 1)
                  responseCallback.onSavedDB();
        }
    }

    @Override
    public List<Valute> getListValute() {
        List<Valute> listValute = new ArrayList<Valute>();
        Cursor cursor = sqLiteDatabaseRead.rawQuery("select * from " + TABLE_NAME_VALUTE, null);
        if(cursor.moveToFirst()){
            int indexNumCode = cursor.getColumnIndex(Vars.ParamsDB.NUM_CODE);
            int indexCharCode = cursor.getColumnIndex(Vars.ParamsDB.CHAR_CODE);
            int indexNominal = cursor.getColumnIndex(Vars.ParamsDB.NOMINAL);
            int indexValue = cursor.getColumnIndex(Vars.ParamsDB.VALUE);
            int indexName = cursor.getColumnIndex(Vars.ParamsDB.NAME);
            do {
                String name = cursor.getString(indexName);
                Valute valute = new Valute.Builder()
                        .setCharCode(cursor.getString(indexCharCode))
                        .setNumCode(cursor.getInt(indexNumCode))
                        .setNominal(cursor.getInt(indexNominal))
                        .setFavorite(issetValute(TABLE_NAME_FAVORITE, cursor.getInt(indexNumCode)))
                        .setName(name)
                        .setValue(Float.parseFloat(cursor.getString(indexValue)))
                        .create();
                listValute.add(valute);
            }
            while (cursor.moveToNext());
        }
        return listValute;
    }

    @Override
    public List<Valute> getListFavoriteValute() {

        String sql = TABLE_NAME_VALUTE + " as TV" + " inner join " +
                TABLE_NAME_FAVORITE + " as TF" + " on " + "TV."
                + Vars.ParamsDB.NUM_CODE +
                " = " + "TF." + Vars.ParamsDB.NUM_CODE;
        List<Valute> listValute = new ArrayList<Valute>();
        try {
            Cursor cursor = sqLiteDatabaseRead.query(sql, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int indexNumCode = cursor.getColumnIndex(Vars.ParamsDB.NUM_CODE);
                int indexCharCode = cursor.getColumnIndex(Vars.ParamsDB.CHAR_CODE);
                int indexNominal = cursor.getColumnIndex(Vars.ParamsDB.NOMINAL);
                int indexValue = cursor.getColumnIndex(Vars.ParamsDB.VALUE);
                int indexName = cursor.getColumnIndex(Vars.ParamsDB.NAME);
                do {
                    String name = cursor.getString(indexName);
                    Valute valute = new Valute.Builder()
                            .setCharCode(cursor.getString(indexCharCode))
                            .setNumCode(cursor.getInt(indexNumCode))
                            .setNominal(cursor.getInt(indexNominal))
                            .setName(name)
                            .setValue(Float.parseFloat(cursor.getString(indexValue)))
                            .create();
                    listValute.add(valute);
                }
                while (cursor.moveToNext());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
        }
        return listValute;
    }

    @Override
    public void addToFavorite(int numCode, String charCode, String idValute) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Vars.ParamsDB.NUM_CODE, numCode);
        contentValues.put(Vars.ParamsDB.CHAR_CODE, charCode);
        contentValues.put(Vars.ParamsDB.ID_VALUTE, idValute);
        long row = sqLiteDatabaseWrite.insert(TABLE_NAME_FAVORITE, null, contentValues);
        Log.e("addToFavorite", "" + row);
    }

    @Override
    public void removeFromFavorite(int numCode) {
        sqLiteDatabaseRead.delete(TABLE_NAME_FAVORITE, Vars.ParamsDB.NUM_CODE + "=" + numCode, null);
    }

    public boolean issetValute(String table, int numCode){
        boolean isset = false;
        try {
            Cursor cursor = sqLiteDatabaseRead.rawQuery("select * from " + table + " WHERE " +
                    Vars.ParamsDB.NUM_CODE + "=" + numCode, null);
            Log.e("cursor count", "" + cursor.getCount());
            isset = cursor.getCount() != 0;
        }
        catch (Exception e){
            Log.e("issetValute", "Exception: "+e.getMessage());
        }
        return isset;
    }

    @Override
    public SQLiteDatabase getDbRead() {
        return sqLiteDatabaseRead;
    }

    @Override
    public SQLiteDatabase getDbWrite() {
        return sqLiteDatabaseWrite;
    }

}
