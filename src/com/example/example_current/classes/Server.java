package com.example.example_current.classes;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.example.example_current.R;
import com.example.example_current.activity.MainActivity;
import org.apache.http.NameValuePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Арем on 11.09.14.
 */
public class Server {
    private App app = App.getInstance();
    private String protocol = "http";
    private String host = "cbr.ru";
    private String getPath = "/scripts/XML_daily.asp?";
    private Boolean connect = false;
    private String method;
    private Context context;
    private Boolean status_request;
    private ProgressDialog prog;
    public static String nameCurrent[];
    public static String valueCurrent[];
    private static String codeCurrent[];
    private static String idCurrent[];
    public static String valueUSD, valueEUR, valueUA;
    public static float valueUSDYesterday, valueEURYesterday, valueUAYesterday;

    public Server(Context context){
        this.context = context;
    }

    public Boolean connections() throws Exception {
        try {
            URLConnection connection = new URL(protocol, host, 80, "").openConnection();
            Log.e("url", connection.getURL().toString());
            connection.setConnectTimeout(10000);
            connection.connect();
            connect = true;
            Log.e("connect", "succes");
        } catch (Exception e) {
            connect = false;
            Log.e("connect", "failed");
            Log.e("exception", e.getMessage());
        }
        return connect;
    }
    public Boolean send(List<NameValuePair> request_array){
            //this.host = host;
            //request = protocol + host + path + "&text="+text_translate+"&lang=ru-en&format=plain&options=1";
            new AsyncTaskSend(request_array).execute();

        return status_request;
    }
    private void quertYesterdayData(){

        try {
            String res;
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date(cal.getTimeInMillis());
            URL url = new URL(protocol + "://" + host + getPath + "date_req=" + dateFormat.format(date));
            Log.e("request", url.toString());
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            InputStream is = connection.getInputStream();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(is);

            Element element = document.getDocumentElement();
            NodeList nodeList = element.getElementsByTagName("Valute");
            Log.e("nodeList", "" + nodeList.getLength());

            nameCurrent = new String[nodeList.getLength()];
            valueCurrent = new String[nodeList.getLength()];
            codeCurrent = new String[nodeList.getLength()];
            idCurrent = new String[nodeList.getLength()];

            for (int i = 0; i < nodeList.getLength(); i++) {

                Element entry = (Element) nodeList.item(i);
                Element EnameCurrent = (Element) entry.getElementsByTagName("Name").item(0);
                Element EidCurrent  = (Element) entry.getElementsByTagName("NumCode").item(0);
                Element EvalueCurrent = (Element) entry.getElementsByTagName("Value").item(0);
                Element EcodeCurrent = (Element) entry.getElementsByTagName("CharCode").item(0);

                nameCurrent[i] = EnameCurrent.getFirstChild().getNodeValue();
                idCurrent[i] = EidCurrent.getFirstChild().getNodeValue();
                valueCurrent[i] = EvalueCurrent.getFirstChild().getNodeValue();
                codeCurrent[i] = EcodeCurrent.getFirstChild().getNodeValue();

                saveYesterdayData();
            }
        }
        catch (Exception e){
            Log.e("queryData", "Exception: " + e.getMessage());
        }
    }
    private void queryData(){
        try {
            String res;
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            URL url = new URL(protocol + "://" + host + getPath + "date_req=" + dateFormat.format(date));
            Log.e("request", url.toString());
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            InputStream is = connection.getInputStream();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(is);

            Element element = document.getDocumentElement();
            NodeList nodeList = element.getElementsByTagName("Valute");
            Log.e("nodeList", "" + nodeList.getLength());

            nameCurrent = new String[nodeList.getLength()];
            valueCurrent = new String[nodeList.getLength()];
            codeCurrent = new String[nodeList.getLength()];
            idCurrent = new String[nodeList.getLength()];

            for (int i = 0; i < nodeList.getLength(); i++) {

                Element entry = (Element) nodeList.item(i);
                Element EnameCurrent = (Element) entry.getElementsByTagName("Name").item(0);
                Element EidCurrent  = (Element) entry.getElementsByTagName("NumCode").item(0);
                Element EvalueCurrent = (Element) entry.getElementsByTagName("Value").item(0);
                Element EcodeCurrent = (Element) entry.getElementsByTagName("CharCode").item(0);

                nameCurrent[i] = EnameCurrent.getFirstChild().getNodeValue();
                idCurrent[i] = EidCurrent.getFirstChild().getNodeValue();
                valueCurrent[i] = EvalueCurrent.getFirstChild().getNodeValue();
                codeCurrent[i] = EcodeCurrent.getFirstChild().getNodeValue();
            }
        }
        catch (Exception e){
            Log.e("queryData", "Exception: " + e.getMessage());
        }
    }
    public Boolean getCurrent(){
        this.method = "getCurrent";

        List<NameValuePair> request_array = new ArrayList<NameValuePair>();
//        request_array.add(new BasicNameValuePair("date_req", "18/09/2014"));
        Log.e("request_array", request_array.toString());

        return send(request_array);
    }
    public Boolean saveYesterdayData(){
        Log.e("saveYesterdayData", "started");
        try {
            for(int i = 0; i < nameCurrent.length; i++) {
                if (codeCurrent[i].equals("USD")) {
                    Log.e("code", codeCurrent[i]);
                    valueUSDYesterday = Float.parseFloat(valueCurrent[i].replace(",", "."));
                    Log.e("valueUSDYesterday", ""+valueUSDYesterday);
                } else if (codeCurrent[i].equals("EUR")) {
                    Log.e("code", codeCurrent[i]);
                    valueEURYesterday = Float.parseFloat(valueCurrent[i].replace(",", "."));
                    Log.e("valueEURYesterday", ""+valueEURYesterday);
                } else if (codeCurrent[i].equals("UAH")) {
                    Log.e("code", codeCurrent[i]);
                    valueUAYesterday = Float.parseFloat(valueCurrent[i].replace(",", "."));
                    Log.e("valueUAYesterday", ""+valueUAYesterday);
                }
            }
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public Boolean showData(){
        Log.e("showData", "started");
        try {
            App.currentArray = new ArrayList<HashMap<String, Object>>();
            ArrayList<HashMap<String, Object>> currentArray = new ArrayList<HashMap<String, Object>>();
            for(int i = 0; i < nameCurrent.length; i++) {
                App.current = new HashMap<String, Object>();
                App.current.put(App.NAME, codeCurrent[i]);
                App.current.put(App.VALUE, valueCurrent[i]);
                App.currentHashMap = new HashMap<String, Object>();
                App.currentHashMap.put(App.NAME, nameCurrent[i]);
                App.currentHashMap.put(App.VALUE, valueCurrent[i]);
                currentArray.add(App.current);
                App.currentArray.add(App.currentHashMap);
                if(codeCurrent[i].equals("USD")){
                    Log.e("code", codeCurrent[i]);
                    valueUSD = valueCurrent[i];
                }
                else if(codeCurrent[i].equals("EUR")){
                    Log.e("code", codeCurrent[i]);
                    valueEUR = valueCurrent[i];
                }
                else if(codeCurrent[i].equals("UAH")){
                    Log.e("code", codeCurrent[i]);
                    valueUA = valueCurrent[i];
                }
                Log.e("name", nameCurrent[i]);
                Log.e("code", codeCurrent[i]);
                Log.e("id", idCurrent[i]);
                Log.e("value", valueCurrent[i]);
            }
            ((MainActivity)App.getContext()).showValueCurrent(valueUSD, valueEUR, valueUA);
            ((MainActivity)App.getContext()).setAdapterSpinner(currentArray);

            return true;

        } catch (Exception e) {
            Log.e("showData", "Exception: " + e.getMessage());
        }
        return false;
    }
    class AsyncTaskSend extends AsyncTask<Integer, Integer, Boolean> {

        List<NameValuePair> request_array;

        public AsyncTaskSend(List<NameValuePair> request_array){
            this.request_array = request_array;
        }
        @Override
        protected void onPreExecute(){
//            app.AlertProgress();
//            MainActivity.startLoadAnimation();
            App.alertLoaderStart(new AnimationLoader(App.getContext()));
        }
        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
                Log.e("AsyncTask","run");
                if(connections()) {
                    quertYesterdayData();
                    queryData();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            Log.e("onPost", "run");
            if(result != null) {
//                MainActivity.stopLoadAnimation();
                App.alertLoaderStop();
                Log.e("result", result.toString());
                if(result) {
                    //app.Alert("OnPost", "Succes");
                    status_request = showData();
                    ((MainActivity) App.getContext()).saveCurrentPreferences();
                    //prog.cancel();
                }
                else {
                    Toast.makeText(App.getContext(), App.getContext().getResources().getString(R.string.notConnectionToInternet), Toast.LENGTH_LONG).show();
                    ((MainActivity) App.getContext()).getCurrentPreferences();
                }
            }
        }
    }
}
