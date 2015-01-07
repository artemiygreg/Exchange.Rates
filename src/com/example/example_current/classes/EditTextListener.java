package com.example.example_current.classes;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.example.example_current.activity.MainActivity;


/**
 * Created by Artem on 11.12.2014.
 */
public class EditTextListener implements TextWatcher {
    private EditText editText;

    public EditTextListener(EditText editText){
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        editText.clearFocus();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(editText.getText().length() > 0) {
                    ((MainActivity) App.getContext()).convertCurrent(MainActivity.positionSelectedSpinner);
                }
            }
        }, 500);
    }
}
