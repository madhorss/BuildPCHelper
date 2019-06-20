package com.example.best.buildpc;
import java.text.DecimalFormat;
import java.text.ParseException;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class NumTextWatcher implements TextWatcher {

    private DecimalFormat dfnd;

    private EditText et;

    public NumTextWatcher(EditText et)
    {
        dfnd = new DecimalFormat("#,###");
        this.et = et;
    }

    @Override
    public void afterTextChanged(Editable s)
    {
        et.removeTextChangedListener(this);

        try {
            int inilen, endlen;
            inilen = et.getText().length();

            String v = s.toString().replace(",", "");
            Number n = dfnd.parse(v);
            int cp = et.getSelectionStart();
            et.setText(dfnd.format(n));

            endlen = et.getText().length();
            int sel = (cp + (endlen - inilen));
            if (sel > 0 && sel <= et.getText().length()) {
                et.setSelection(sel);
            }
        } catch (NumberFormatException nfe) {
        } catch (ParseException e) {
        }
        et.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
    }

}