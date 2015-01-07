package com.example.example_current.classes;

/**
 * Created by Admin on 22.12.14.
 */
public class ListViewCustom {
    public int icon;
    public int icon_changes;
    public int colorChanges;
    public String valueCurrent;
    public String valueChange;

    public ListViewCustom(){
        super();
    }

    public ListViewCustom(int icon, String valueCurrent, String valueChange, int icon_changes, int colorChanges){
        super();
        this.icon = icon;
        this.icon_changes = icon_changes;
        this.valueChange = valueChange;
        this.valueCurrent = valueCurrent;
        this.colorChanges = colorChanges;
    }
}
