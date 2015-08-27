package com.example.example_current.view.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.example_current.R;
import com.example.example_current.model.Valute;
import com.example.example_current.service.valute.ValuteService;
import com.example.example_current.service.valute.ValuteServiceImpl;
import com.example.example_current.utils.AppMain;

import java.util.List;

/**
 * Created by Admin on 23.08.15.
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private List<Valute> listValute;
    private OnClickListener onClickListener;
    private ValuteService valuteService;
    private Resources resources;

    public FavoriteAdapter(List<Valute> listValute) {
        this.listValute = listValute;
        valuteService = ValuteServiceImpl.getInstance();
        resources = AppMain.getContext().getResources();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageButton imageButtonRemove;
        private TextView textViewName;
        private TextView textViewCharCode;
        private TextView textViewNominal;
        private TextView textViewValue;
        private View view;

        public ViewHolder(View view){
            super(view);
            imageButtonRemove = (ImageButton)view.findViewById(R.id.imageButtonRemove);
            textViewName = (TextView)view.findViewById(R.id.textViewName);
            textViewCharCode = (TextView)view.findViewById(R.id.textViewCharCode);
            textViewNominal = (TextView)view.findViewById(R.id.textViewNominal);
            textViewValue = (TextView)view.findViewById(R.id.textViewValue);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onClickListener != null)
                onClickListener.onClick(v, getPosition(), listValute.get(getPosition()));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_favorite_item_valute, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final Valute valute = listValute.get(position);

        viewHolder.textViewName.setText(valute.getName());
        viewHolder.textViewCharCode.setText(valute.getCharCode());
        viewHolder.textViewNominal.setText(Integer.toString(valute.getNominal()));
        viewHolder.textViewValue.setText(Float.toString(valute.getValue()));
        viewHolder.imageButtonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(valute.getNumCode(), position);
            }
        });

    }
    private void remove(int numCode, int position){
        listValute.remove(position);
        notifyDataSetChanged();
        valuteService.removeFromFavorite(numCode);
    }

    @Override
    public  int getItemCount(){
        return listValute.size();
    }

    public interface OnClickListener {
        void onClick(View view, int position, Object itemObject);
    }
}
