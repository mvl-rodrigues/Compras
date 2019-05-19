package br.rodrigues.compras.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;

public abstract class MaskEditUtil {

    public static TextWatcher monetario(final EditText ediTxt) {

        return new TextWatcher() {

            // Mascara monetaria para o preço do produto
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().equals(current)){
//                    ediTxt.removeTextChangedListener(this);
//
//                    String cleanString = s.toString().replaceAll("[R$,.]", "");
//
//                    double parsed = Double.parseDouble(cleanString);
//                    String formatted = NumberFormat.getCurrencyInstance().format((parsed/100));
//
//                    current = formatted.replaceAll("[R$]", "");
//                    ediTxt.setText(current);
//
//                    ediTxt.setSelection(current.length());
//
//                    ediTxt.addTextChangedListener(this);

                    ediTxt.removeTextChangedListener(this);

                    String cleanString = s.toString()
                            .replace("$", "")
                            .replace("R", "")
                            .replace(".","")
                            .replace(",","")
                            .replace("£","")
                            .replace("€","").replace(" ","");

                    double parsed = Double.parseDouble(cleanString);

                    String formatted = NumberFormat.getCurrencyInstance().format((parsed/100));

                    current = formatted
                            .replace("$", "")
                            .replace("£","")
                            .replace("R", "")
                            .replace("€","");

                    ediTxt.setText(current);

                    ediTxt.setSelection(current.length());

                    ediTxt.addTextChangedListener(this);

                }
            }

        };
    }
}