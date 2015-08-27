package com.example.example_current.utils;

import com.example.example_current.model.Valute;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 16.08.15.
 */
public class ModelHelper {

    public static List<Valute> makeListValuteFromNodeList(NodeList nodeList){
        List<Valute> listValute = new ArrayList<Valute>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element entry = (Element) nodeList.item(i);
            Element elementName = (Element) entry.getElementsByTagName(Vars.Valute.NAME).item(0);
            Element elementNumCode  = (Element) entry.getElementsByTagName(Vars.Valute.NUM_CODE).item(0);
            Element elementValue = (Element) entry.getElementsByTagName(Vars.Valute.VALUE).item(0);
            Element elementCharCode = (Element) entry.getElementsByTagName(Vars.Valute.CHAR_CODE).item(0);
            Element elementNominal = (Element) entry.getElementsByTagName(Vars.Valute.NOMINAL).item(0);
            String id = entry.getAttribute(Vars.Valute.ID);

            Valute valute = new Valute.Builder()
                    .setId(id)
                    .setCharCode(elementCharCode.getFirstChild().getNodeValue())
                    .setNumCode(Integer.parseInt(elementNumCode.getFirstChild().getNodeValue()))
                    .setNominal(Integer.parseInt(elementNominal.getFirstChild().getNodeValue()))
                    .setName(elementName.getFirstChild().getNodeValue())
                    .setValue(Float.parseFloat(elementValue.getFirstChild().getNodeValue().replace(",", ".")))
                    .create();
            listValute.add(valute);
        }

        return listValute;
    }
}
