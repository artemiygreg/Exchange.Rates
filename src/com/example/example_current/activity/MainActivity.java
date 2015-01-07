package com.example.example_current.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.example.example_current.R;
import com.example.example_current.classes.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends Activity {
    private App app;
    public ProgressDialog progressDialog;
    private TextView  dateUpdated, valueUsd,valueEur, valueUa, result, change_usd, change_eur, change_ua;
    private Server server;
    private Button btnRefresh, btnShowMoreCurrent;
    private ImageView imageView, imageView2, imageView3, ic_usd, ic_eur, ic_ua;
    private static AnimationLoader animationLoader;
    private static LinearLayout linearLayout;
    private static SharedPreferences preferences;
    private EditText editText;
    private SimpleAdapter adapter;
    private Spinner spinner;
    private boolean showingMore = true;
    private ActionBar actionBar;
    public static int positionSelectedSpinner = 0;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new AnimationLoader(this));
        setContentView(R.layout.main);
        dateUpdated = (TextView) findViewById(R.id.dateUpdated);
        btnRefresh = (Button) findViewById(R.id.btnRefresh);
        btnShowMoreCurrent = (Button) findViewById(R.id.btnShowMoreCurrent);
        listView = (ListView)findViewById(R.id.listView);
/*        imageView = (ImageView) findViewById(R.id.imageView);
        valueUsd = (TextView) findViewById(R.id.valueUsd);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        valueEur = (TextView) findViewById(R.id.valueEur);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        ic_usd = (ImageView) findViewById(R.id.ic_usd);
        ic_eur = (ImageView) findViewById(R.id.ic_eur);
        ic_ua = (ImageView) findViewById(R.id.ic_ua);
        valueUa = (TextView) findViewById(R.id.valueUa);
        change_usd = (TextView)findViewById(R.id.change_usd);
        change_eur = (TextView)findViewById(R.id.change_eur);
        change_ua = (TextView)findViewById(R.id.change_ua);*/
        spinner = (Spinner)findViewById(R.id.spinner);
        editText = (EditText)findViewById(R.id.editText);
        result = (TextView)findViewById(R.id.result);



        editText.addTextChangedListener(new EditTextListener(editText));

        /*animationLoader = new AnimationLoader(this);
        linearLayout = (LinearLayout)findViewById(R.id.an);
        linearLayout.addView(animationLoader);*/
        preferences = getSharedPreferences("current", MODE_PRIVATE);
        getCurrentPreferences();

        app = new App(this);
        app.server.getCurrent();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                valueUsd.setText("");
                valueEur.setText("");
                valueUa.setText("");
                dateUpdated.setText("");*/
                app.server.getCurrent();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionSelectedSpinner = position;
                if(editText.getText().length() > 0) {
                    convertCurrent(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnShowMoreCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreValueCurrent();
            }
        });
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        valueUsd.setText(savedInstanceState.getString("valueUsd", getResources().getString(R.string.notData)));
        valueEur.setText(savedInstanceState.getString("valueEur", getResources().getString(R.string.notData)));
        valueUa.setText(savedInstanceState.getString("valueUa", getResources().getString(R.string.notData)));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /*if(!valueUsd.getText().toString().equals("") && !valueEur.getText().toString().equals("") && !valueUa.getText().toString().equals("")){
            outState.putString("valueUsd", valueUsd.getText().toString());
            outState.putString("valueEur", valueEur.getText().toString());
            outState.putString("valueUa", valueUa.getText().toString());
        }*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return true;
            }
        });

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return true;
    }
    @Override
    public void onResume(){
        super.onResume();
    }
    public void showValueCurrent(String valueUSD, String valueEUR, String valueUA){
        float valueUSDd = Float.parseFloat(valueUSD.replace(",", "."));
        float valueEURd = Float.parseFloat(valueEUR.replace(",", "."));
        float valueUAd = Float.parseFloat(valueUA.replace(",", "."));
        int ic_changes_usd = (valueUSDd > Server.valueUSDYesterday)
                ?(R.drawable.ic_increase):(R.drawable.ic_lowering);
        int ic_changes_eur =(valueEURd > Server.valueEURYesterday)
                ?(R.drawable.ic_increase):(R.drawable.ic_lowering);
        int ic_changes_ua = (valueUAd > Server.valueUAYesterday)
                ?(R.drawable.ic_increase):(R.drawable.ic_lowering);
        dateUpdated.setText(dateUpdated());
        String preChangesUsd = (valueUSDd > Server.valueUSDYesterday) ? ("+ ") : ("- ");
        String preChangesEur = (valueEURd > Server.valueEURYesterday)?("+ "):("- ");
        String preChangesUa = (valueUAd > Server.valueUAYesterday)?("+ "):("- ");
        int colorChangesUSD = getResources().getColor((valueUSDd > Server.valueUSDYesterday)
                ? (R.color.colorIncrease) : (R.color.colorLowering));
        int colorChangesEUR = getResources().getColor((valueEURd > Server.valueEURYesterday)
                ? (R.color.colorIncrease) : (R.color.colorLowering));
        int colorChangesUA = getResources().getColor((valueUAd > Server.valueUAYesterday)
                ? (R.color.colorIncrease) : (R.color.colorLowering));

        /*valueUsd.setText(valueUSD);
        valueEur.setText(valueEUR);
        valueUa.setText(valueUA);
        ic_usd.setImageDrawable(getResources().getDrawable((valueUSDd >
        Server.valueUSDYesterday)?(R.drawable.ic_increase):(R.drawable.ic_lowering)));
        ic_eur.setImageDrawable(getResources().getDrawable((valueEURd >
                Server.valueEURYesterday) ? (R.drawable.ic_increase) : (R.drawable.ic_lowering)));
        ic_ua.setImageDrawable(getResources().getDrawable((valueUAd >
                Server.valueUAYesterday) ? (R.drawable.ic_increase) : (R.drawable.ic_lowering)));
        Log.e("usdYes", "" + Server.valueUSDYesterday);
        Log.e("usd", ""+valueUSDd);
        Log.e("change", Float.toString((valueUSDd > Server.valueUSDYesterday)?(valueUSDd-Server.valueUSDYesterday):((float)2.3434)));

        change_usd.setText((valueUSDd > Server.valueUSDYesterday) ? ("+ ") : ("- "));
        change_usd.append(Float.toString((valueUSDd > Server.valueUSDYesterday)?
                        (roundingNumbers(valueUSDd-Server.valueUSDYesterday)) :
                        (roundingNumbers(Server.valueUSDYesterday - valueUSDd))));
        change_eur.setText((valueEURd > Server.valueEURYesterday)?("+ "):("- "));
        change_eur.append(Float.toString((valueEURd > Server.valueEURYesterday)?
                                (roundingNumbers(valueEURd - Server.valueEURYesterday)):
                                (roundingNumbers(Server.valueEURYesterday - valueEURd))));
        change_ua.setText((valueUAd > Server.valueUAYesterday)?("+ "):("- "));
        change_ua.append(Float.toString((valueUAd > Server.valueUAYesterday)?
                                (roundingNumbers(valueUAd - Server.valueUAYesterday)):
                                (roundingNumbers(Server.valueUAYesterday - valueUAd))));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.layout_list_view_custom, R.id.valueCurrent);
        arrayAdapter.add(valueUA);*/

        ListView listView = (ListView)findViewById(R.id.listView);
        ListViewCustom[] list = new ListViewCustom[]{
          new ListViewCustom(R.drawable.ic_usd, valueUSD, preChangesUsd+Float.toString((valueUSDd > Server.valueUSDYesterday)?
                  (roundingNumbers(valueUSDd-Server.valueUSDYesterday)) :
                  (roundingNumbers(Server.valueUSDYesterday - valueUSDd))), ic_changes_usd, colorChangesUSD),
          new ListViewCustom(R.drawable.ic_eur, valueEUR, preChangesEur+Float.toString((valueEURd > Server.valueEURYesterday)?
                  (roundingNumbers(valueEURd - Server.valueEURYesterday)):
                  (roundingNumbers(Server.valueEURYesterday - valueEURd))), ic_changes_eur, colorChangesEUR),
          new ListViewCustom(R.drawable.ic_ua, valueUA, preChangesUa+Float.toString((valueUAd > Server.valueUAYesterday)?
                  (roundingNumbers(valueUAd - Server.valueUAYesterday)):
                  (roundingNumbers(Server.valueUAYesterday - valueUAd))), ic_changes_ua, colorChangesUA),
        };

        ListViewCustomAdapter adapter = new ListViewCustomAdapter(this, R.layout.layout_list_view_custom, list);
        listView.setAdapter(adapter);
    }
    private void setAdapterList(String valueUSD, String valueEUR, String valueUA){
        ListView listView = (ListView)findViewById(R.id.listView);
        ListViewCustom[] list = new ListViewCustom[]{
                new ListViewCustom(R.drawable.ic_usd, valueUSD, null, 0, 0),
                new ListViewCustom(R.drawable.ic_eur, valueEUR, null, 0, 0),
                new ListViewCustom(R.drawable.ic_ua, valueUA, null, 0, 0)
        };

        ListViewCustomAdapter adapter = new ListViewCustomAdapter(this, R.layout.layout_list_view_custom, list);
        listView.setAdapter(adapter);
    }
    public float roundingNumbers(float f){
        float newFloat = new BigDecimal(f).setScale(4, RoundingMode.HALF_UP).floatValue();
        return newFloat;
    }
    public static void startLoadAnimation(){
        linearLayout.addView(animationLoader);
        linearLayout.setVisibility(View.VISIBLE);
    }
    public static void stopLoadAnimation(){
        linearLayout.setVisibility(View.GONE);
        linearLayout.removeView(animationLoader);
    }
    public void saveCurrentPreferences(){
        SharedPreferences.Editor editorPref = preferences.edit();
        editorPref.putString("valueUsd", Server.valueUSD);
        editorPref.putString("valueEur", Server.valueEUR);
        editorPref.putString("valueUa", Server.valueUA);
        editorPref.putString("dateUpdated", dateUpdated.getText().toString());
        editorPref.apply();
    }
    public void getCurrentPreferences() {
        /*valueUsd.setText(preferences.getString("valueUsd", getResources().getString(R.string.notData)));
        valueEur.setText(preferences.getString("valueEur", getResources().getString(R.string.notData)));
        valueUa.setText(preferences.getString("valueUa", getResources().getString(R.string.notData)));*/
        setAdapterList(preferences.getString("valueUsd", getResources().getString(R.string.notData)),
                       preferences.getString("valueEur", getResources().getString(R.string.notData)),
                       preferences.getString("valueUa", getResources().getString(R.string.notData)));
        dateUpdated.setText(preferences.getString("dateUpdated", getResources().getString(R.string.notData)));
    }
    public void setAdapterSpinner(ArrayList<HashMap<String, Object>> current){

        /*ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, title);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        */
        SimpleAdapter adapter = new SimpleAdapter(this, current, R.layout.list_item_layout, new String[]{
                App.NAME, App.VALUE}, new int[]{R.id.text1, R.id.text2});
        spinner.setAdapter(adapter);
    }
    public void convertCurrent(int position){
        float valueFromEditText = Float.parseFloat(editText.getText().toString());
        String valueConvert = Server.valueCurrent[position].replace(",", ".");
        float resultConvertation = valueFromEditText * Float.parseFloat(valueConvert);
        result.setText(Float.toString(roundingNumbers(resultConvertation)));
    }
    public String dateUpdated(){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        DateFormat time = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return dateFormat.format(date) + getString(R.string.in) + time.format(date);
    }
    public void loadAllCurrents(){
        SimpleAdapter adapter = new SimpleAdapter(this, App.currentArray, R.layout.list_item_layout, new String[]{
                App.NAME, App.VALUE}, new int[]{R.id.text1, R.id.text2});
        listView.setAdapter(adapter);
    }
    private void showMoreValueCurrent(){
        if(showingMore){
            btnShowMoreCurrent.setText(getResources().getString(R.string.hiddenCurrent));
            loadAllCurrents();
            showingMore = false;
        }
        else {
            showValueCurrent(Server.valueUSD, Server.valueEUR, Server.valueUA);
            btnShowMoreCurrent.setText(getResources().getString(R.string.showMoreCurrent));
            showingMore = true;
        }
    }

}