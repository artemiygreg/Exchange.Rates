package com.example.example_current.service.valute;

import com.example.example_current.model.Valute;
import com.example.example_current.server.ResponseCallback;

import java.util.List;

/**
 * Created by Admin on 16.08.15.
 */
public interface ValuteService {

    void saveListValute(List<Valute> listValute, ResponseCallback responseCallback);
    List<Valute> getListValute();
    List<Valute> getListFavoriteValute();
    void addToFavorite(int numCode, String charCode, String idValute);
    void removeFromFavorite(int numCode);
}
