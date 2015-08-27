package com.example.example_current.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.example_current.R;
import com.example.example_current.classes.DividerItemDecoration;
import com.example.example_current.model.Valute;
import com.example.example_current.service.valute.ValuteService;
import com.example.example_current.service.valute.ValuteServiceImpl;
import com.example.example_current.view.adapter.FavoriteAdapter;

import java.util.List;


public class FavoriteFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView textViewInfo;
    private ValuteService valuteService;


    public FavoriteFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        valuteService = ValuteServiceImpl.getInstance();

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        textViewInfo = (TextView)view.findViewById(R.id.textViewInfo);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

        return view;
    }
    public void updateFavorite(){
        List<Valute> listValute = valuteService.getListFavoriteValute();
        if(listValute != null && listValute.size() > 0) {
            FavoriteAdapter adapter = new FavoriteAdapter(listValute);
            recyclerView.setVisibility(View.VISIBLE);
            textViewInfo.setVisibility(View.GONE);
            recyclerView.setAdapter(adapter);
        }
        else {
            recyclerView.setVisibility(View.GONE);
            textViewInfo.setVisibility(View.VISIBLE);
        }
    }
}
