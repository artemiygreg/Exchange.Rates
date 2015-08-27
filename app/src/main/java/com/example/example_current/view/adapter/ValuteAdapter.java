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
 * Created by Admin on 22.12.14.
 */
public class ValuteAdapter extends RecyclerView.Adapter<ValuteAdapter.ViewHolder> {
    private List<Valute> listValute;
    private OnClickListener onClickListener;
    private ValuteService valuteService;
    private Resources resources;

    public ValuteAdapter(List<Valute> listValute) {
        this.listValute = listValute;
        valuteService = ValuteServiceImpl.getInstance();
        resources = AppMain.getContext().getResources();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageButton imageButtonFavorite;
        private TextView textViewName;
        private TextView textViewCharCode;
        private TextView textViewNominal;
        private TextView textViewValue;
        private View view;

        public ViewHolder(View view){
            super(view);
            imageButtonFavorite = (ImageButton)view.findViewById(R.id.imageButtonFavorite);
            textViewName = (TextView)view.findViewById(R.id.textViewName);
            textViewCharCode = (TextView)view.findViewById(R.id.textViewCharCode);
            textViewNominal = (TextView)view.findViewById(R.id.textViewNominal);
            textViewValue = (TextView)view.findViewById(R.id.textViewValue);
            view.setOnClickListener(this);
            this.view = view;
        }

        @Override
        public void onClick(View v) {
            if(onClickListener != null)
                onClickListener.onClick(v, getPosition(), listValute.get(getPosition()));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_valute, parent, false);
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

        if(valute.isFavorite()){
            viewHolder.imageButtonFavorite.setImageDrawable(resources.getDrawable(R.drawable.star));
            viewHolder.view.setBackgroundColor(resources.getColor(R.color.bgViewFavorite));
        }
        else {
            viewHolder.imageButtonFavorite.setImageDrawable(resources.getDrawable(R.drawable.star_outline));
            viewHolder.view.setBackgroundColor(resources.getColor(R.color.colorWhite));
        }
        viewHolder.imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFavorite(valute, position);
            }
        });

    }
    private void onClickFavorite(Valute valute, int position) {
        if(valute.isFavorite()) {
            valuteService.removeFromFavorite(valute.getNumCode());
            valute.resetFavorite(false);
        }
        else {
            valuteService.addToFavorite(valute.getNumCode(), valute.getCharCode(), valute.getId());
            valute.resetFavorite(true);
        }
        notifyItemChanged(position);
    }
    @Override
    public  int getItemCount(){
        return listValute.size();
    }
    public interface OnClickListener {
        void onClick(View view, int position, Object itemObject);
    }
}
