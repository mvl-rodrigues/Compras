package br.rodrigues.compras.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
    private EditText campoObs;
    private Item itemOk;

    public FormularioHelper (FormularioActivity context) {
        activity = context;
        getActivityViews();
        itemOk = new Item();
    }

    public void fillItem (Item item){
        campoNome.setText(item.getNome());
        campoPreco.setText(String.valueOf(item.getPreco()));
        campoCategoria.setSelection(getSpinnerPosition(item.getCategoria()));
        campoObs.setText(item.getObservacao());
        campoFrequencia.check(getIdRadioButton(item.getFrequencia()));
        itemOk = item;
    }

    public Item getItem (){

        if (checkRequiredFields()){
            itemOk.setNome(campoNome.getText().toString());
            itemOk.setCategoria(campoCategoria.getSelectedItem().toString());
            itemOk.setObservacao(campoObs.getText().toString());
            itemOk.setFrequencia(getStringRadioButton());
        }

        return itemOk;
    }

    private void getActivityViews() {
        campoNome = activity.findViewById(R.id.activity_formulario_nome);
        campoPreco = activity.findViewById(R.id.activity_formulario_preco);
        campoCategoria = activity.findViewById(R.id.activity_formulario_categoria);
        campoFrequencia = activity.findViewById(R.id.activity_formulario_frequencia);
        campoObs = activity.findViewById(R.id.activity_formulario_obs);
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

    public boolean checkRequiredFields() {
        
        if (campoNome.getText().toString().isEmpty()){
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
            return false;
        }
        if (!campoPreco.getText().toString().isEmpty()) {
            itemOk.setPreco(Double.valueOf(campoPreco.getText().toString()));
        } else {
            itemOk.setPreco(0.0);
        }
        return true;
    }
}