package com.example.example_current.server;

import android.os.AsyncTask;
import android.util.Log;
import com.example.example_current.model.Valute;
import com.example.example_current.service.valute.ValuteService;
import com.example.example_current.service.valute.ValuteServiceImpl;
import com.example.example_current.utils.ModelHelper;
import com.example.example_current.utils.Utilites;
import com.example.example_current.utils.Vars;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Admin on 16.08.15.
 */
public class Server {

    public static class ServerHolder {
        public static Server instance = new Server();
    }

    public static Server getInstance() {
        return ServerHolder.instance;
    }
    private Server() {

    }
    public void getCurrency(String date, ResponseCallback responseCallback){
        new GetCurrencyAsyncTask(responseCallback).execute(date);
    }
    class GetCurrencyAsyncTask extends AsyncTask<String, Void, NodeList> {
        private ResponseCallback responseCallback;


        public GetCurrencyAsyncTask(ResponseCallback responseCallback){
            this.responseCallback = responseCallback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected NodeList doInBackground(String... params) {
            NodeList nodeList  = null;
            try {
                URL url = new URL(Utilites.makeLinkRequest(params[0]));
                Log.e("url", url.toString());
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = connection.getInputStream();

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document document = db.parse(inputStream);
                Element element = document.getDocumentElement();
                nodeList = element.getElementsByTagName(Vars.Valute.VALUTE);

            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
            return nodeList;
        }

        @Override
        protected void onPostExecute(NodeList result) {
            super.onPostExecute(result);
            if(result != null){
                List<Valute> listValute = ModelHelper.makeListValuteFromNodeList(result);
                ValuteService valuteService = ValuteServiceImpl.getInstance();
                valuteService.saveListValute(listValute, responseCallback);
                responseCallback.onListValute(listValute);
            }
            else {
                responseCallback.onSavedDB();
            }
        }
    }
}
