package br.rodrigues.compras.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import br.rodrigues.compras.R;
import br.rodrigues.compras.model.Item;

public class FormularioHelper {

    private FormularioActivity activity;
    private EditText campoNome;
    private EditText campoPreco;
    private Spinner campoCategoria;
    private RadioGroup campoFrequencia;
    private Item itemOk;
    private EditText campoObs;

    public FormularioHelper (FormularioActivity context) {
        activity = context;
        getActivityViews();
        itemOk = new Item();
    }

    private void getActivityViews() {
        campoNome = activity.findViewById(R.id.activity_formulario_nome);
        campoPreco = activity.findViewById(R.id.activity_formulario_preco);
        campoCategoria = activity.findViewById(R.id.activity_formulario_categoria);
        campoFrequencia = activity.findViewById(R.id.activity_formulario_frequencia);
        campoObs = activity.findViewById(R.id.activity_formulario_obs);
    }

    public Item fillItem() {
//        itemOk = theItem;

//        if (theItem.getId() != null) {
//            fillOldData(theItem);
//        }

        itemOk.setCategoria(campoCategoria.getSelectedItem().toString());
        itemOk.setFrequencia(getStringRadioButton());
        itemOk.setObservacao(campoObs.getText().toString());
        checkRequiredFields();

        return itemOk;
    }

    private void fillOldData(Item oldItem) {
        campoNome.setText(oldItem.getNome());
        campoPreco.setText(String.valueOf(oldItem.getPreco()));
        campoCategoria.setSelection(getSpinnerPosition(oldItem.getCategoria()));
        campoObs.setText(oldItem.getObservacao());
        campoFrequencia.check(getIdRadioButton(oldItem.getFrequencia()));
    }

    private void checkRequiredFields() {
        itemOk.setNome(campoNome.getText().toString());
        if (itemOk.getNome().isEmpty()){
            new AlertDialog.Builder(activity)
                    .setTitle(R.string.alert_default)
                    .setMessage(R.string.activity_formulario_alert_empty_name)
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            campoNome.requestFocus();
                        }
                    })
                    .show();
        }
        if (!campoPreco.getText().toString().isEmpty()) {
            itemOk.setPreco(Double.valueOf(campoPreco.getText().toString()));
        } else {
            itemOk.setPreco(0.0);
        }
    }

    private int getSpinnerPosition (String spinnerString){
        //.getCount(): pega a quantidade de itens que o Spinner possui
        //percorre por todos os itens do spinner
        for (int i = 0; i < campoCategoria.getCount(); i++){
            //verifica se
            if (campoCategoria.getItemAtPosition(i).toString().equalsIgnoreCase(spinnerString)){
                return i;
            }
        }
        return 0;
    }

    private String getStringRadioButton (){
        //identificando o id do radioButton pelo radioGroup
        int selectedId = campoFrequencia.getCheckedRadioButtonId();
        //linkando o radioButton selecionado a variável radioButtonTipo
        RadioButton frequencia = activity.findViewById(selectedId);
        //Convertendo o valor do radioButton para String
        return (String) frequencia.getText();
    }

    private int getIdRadioButton (String radioButtonString){
        //getChildCount(): quantidade de radiobuttons no radioGroup
        //percorre os buttons do radioGroup
        for (int i = 0; i < campoFrequencia.getChildCount() ; i++){
            //getChildAt(i).getId(): pega o id de do radioButton da posição i
            RadioButton frequencia = activity.findViewById(campoFrequencia.getChildAt(i).getId());
            //verifica se a radioButtonString está em algum dos buttons do radioGroup
            if (frequencia.getText().equals(radioButtonString)){
                return campoFrequencia.getChildAt(i).getId();
            }
        }
        return 0;
    }

}