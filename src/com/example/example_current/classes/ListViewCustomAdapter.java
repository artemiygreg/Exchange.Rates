package com.example.example_current.classes;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.example_current.R;

/**
 * Created by Admin on 22.12.14.
 */
public class ListViewCustomAdapter extends ArrayAdapter<ListViewCustom> {
    private Context context;
    private int layoutResourceId;
    private ListViewCustom data[] = null;

    public ListViewCustomAdapter(Context context, int textViewResourceId, ListViewCustom[] objects) {
        super(context, textViewResourceId, objects);
        this.layoutResourceId = textViewResourceId;
        this.context = context;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ListViewCustomHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ListViewCustomHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imageIC);
            holder.ic_changes = (ImageView)row.findViewById(R.id.ic_changes);
            holder.txtTitle = (TextView)row.findViewById(R.id.valueCurrent);
            holder.valueChange = (TextView)row.findViewById(R.id.change);

            row.setTag(holder);
        }
        else
        {
            holder = (ListViewCustomHolder)row.getTag();
        }

        ListViewCustom listViewCustom = data[position];
        holder.txtTitle.setText(listViewCustom.valueCurrent);
        holder.valueChange.setText(listViewCustom.valueChange);
        holder.valueChange.setTextColor(listViewCustom.colorChanges);
        holder.imgIcon.setImageResource(listViewCustom.icon);
        holder.ic_changes.setImageResource(listViewCustom.icon_changes);

        return row;
    }

    static class ListViewCustomHolder
    {
        ImageView imgIcon;
        ImageView ic_changes;
        TextView txtTitle;
        TextView valueChange;
    }
}
