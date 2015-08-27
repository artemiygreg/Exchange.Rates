package com.example.example_current.service.valute;

import android.content.Context;
import com.example.example_current.dao.ValuteDAO;
import com.example.example_current.dao.ValuteDAOImpl;
import com.example.example_current.model.Valute;
import com.example.example_current.server.ResponseCallback;
import com.example.example_current.utils.AppMain;

import java.util.List;

/**
 * Created by Admin on 16.08.15.
 */
public class ValuteServiceImpl implements ValuteService {
    private ValuteDAO valuteDAO;
    private Context context;

    private ValuteServiceImpl() {
        this.context = AppMain.getContext();
        valuteDAO = new ValuteDAOImpl(context);
    }
    private static class ValuteServiceImplHolder {
        private static ValuteServiceImpl instance = new ValuteServiceImpl();
    }
    public static ValuteServiceImpl getInstance() {
        return ValuteServiceImplHolder.instance;
    }
    @Override
    public void saveListValute(List<Valute> listValute, ResponseCallback responseCallback) {
            valuteDAO.insertValute(listValute, responseCallback);
    }

    @Override
    public List<Valute> getListValute() {
        List<Valute> listValute;
        listValute = valuteDAO.getListValute();
        return listValute;
    }

    @Override
    public List<Valute> getListFavoriteValute() {
        List<Valute> listValute;
        listValute = valuteDAO.getListFavoriteValute();
        return listValute;
    }

    @Override
    public void addToFavorite(int numCode, String charCode, String idValute) {
        valuteDAO.addToFavorite(numCode, charCode, idValute);
    }

    @Override
    public void removeFromFavorite(int numCode) {
        valuteDAO.removeFromFavorite(numCode);
    }
}
