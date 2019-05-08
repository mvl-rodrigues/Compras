package br.rodrigues.compras.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.List;

import br.rodrigues.compras.R;
import br.rodrigues.compras.dao.ItemDAO;
import br.rodrigues.compras.model.Item;
import br.rodrigues.compras.util.MaskEditUtil;

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
        campoPreco.setText(new ListaItensHelper().formatarEmReais(item.getPreco()));
        campoObs.setText(item.getObservacao());
        campoCategoria.setSelection(item.getCategoria());
        campoFrequencia.check(item.getFrequencia());
        itemOk = item;
    }

    public Item getItem (){

        itemOk.setNome(campoNome.getText().toString());
        itemOk.setObservacao(campoObs.getText().toString());
        itemOk.setCategoria(campoCategoria.getSelectedItemPosition());
        itemOk.setFrequencia(campoFrequencia.getCheckedRadioButtonId());
        itemOk.setComprado(false);

        if (!campoPreco.getText().toString().isEmpty()) {
            itemOk.setPreco(Double.valueOf(campoPreco.getText().toString().replace(",",".")));
        } else {
            itemOk.setPreco(0.0);
        }

        return itemOk;
    }

    private void getActivityViews() {
        campoNome = activity.findViewById(R.id.activity_formulario_nome);
        campoPreco = activity.findViewById(R.id.activity_formulario_preco);
        campoPreco.addTextChangedListener(MaskEditUtil.monetario(campoPreco));
        campoCategoria = activity.findViewById(R.id.activity_formulario_categoria);
        campoFrequencia = activity.findViewById(R.id.activity_formulario_frequencia);
        campoObs = activity.findViewById(R.id.activity_formulario_obs);
    }

    public boolean checkingNameEmpty() {
        if (campoNome.getText().toString().isEmpty()){
            new AlertDialog.Builder(activity)
                    .setTitle(R.string.alert_default)
                    .setMessage(R.string.activity_formulario_alert_empty_name)
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            campoNome.requestFocus();
                        }
                    }).show();
            return false;
        }
        return true;
    }

    public boolean checkingNameExists(){

        ItemDAO dao = new ItemDAO(activity);

        List<Item> items = dao.getAllItems();

        for (Item i: items){

            if (i.getNome().equalsIgnoreCase(campoNome.getText().toString())) {

                new AlertDialog.Builder(activity)
                        .setTitle(R.string.alert_default)
                        .setMessage("Item já existente.")
                        .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                campoNome.requestFocus();
                            }
                        })
                        .show();
                return false;
            }
        }
        return true;
    }

    public void clearFields() {
        campoNome.setText(null);
        campoPreco.setText("0");
        campoObs.setText(null);
        campoCategoria.setSelection(0);
        campoFrequencia.check(R.id.activity_formulario_frequencia_mensal);
        campoNome.setFocusable(true);
    }

    public void inflateBtnVoltar() {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View btnVoltar = inflater.inflate(R.layout.btn_voltar_padrao, null);

        ActionBar actionBar = activity.getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setCustomView(btnVoltar);
    }
}