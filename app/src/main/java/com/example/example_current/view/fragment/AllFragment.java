package com.example.example_current.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.example_current.R;
import com.example.example_current.classes.DividerItemDecoration;
import com.example.example_current.model.Valute;
import com.example.example_current.service.valute.ValuteService;
import com.example.example_current.service.valute.ValuteServiceImpl;
import com.example.example_current.view.adapter.ValuteAdapter;

import java.util.List;


public class AllFragment extends Fragment {
    private RecyclerView recyclerView;
    private ValuteService valuteService;

    public AllFragment() {
        super();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        valuteService =  ValuteServiceImpl.getInstance();

        return view;
    }
    public void updateFragment(){
        List<Valute> listValute = valuteService.getListValute();
        if(listValute != null && listValute.size() != 0){
            ValuteAdapter valuteAdapter = new ValuteAdapter(listValute);
            recyclerView.setAdapter(valuteAdapter);
        }
    }
}
