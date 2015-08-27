package com.example.example_current.utils;

/**
 * Created by Admin on 16.08.15.
 */
public class Utilites {

    public static String makeLinkRequest(String date){
        return Vars.Server.PROTOCOL + Vars.Server.HOST +
                Vars.Server.PATH + Vars.Server.DATE_REQUEST + date;
    }
}
