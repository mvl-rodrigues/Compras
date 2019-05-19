package br.rodrigues.compras.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.List;

import br.rodrigues.compras.R;
import br.rodrigues.compras.dao.ItemDAO;
import br.rodrigues.compras.model.Item;

import static br.rodrigues.compras.util.ConstantsApp.ACOUGUE;
import static br.rodrigues.compras.util.ConstantsApp.BEBIDAS;
import static br.rodrigues.compras.util.ConstantsApp.CATEGORIAS;
import static br.rodrigues.compras.util.ConstantsApp.GRAOS;
import static br.rodrigues.compras.util.ConstantsApp.HIGIENE;
import static br.rodrigues.compras.util.ConstantsApp.HORTIFRUTI;
import static br.rodrigues.compras.util.ConstantsApp.MASSAS;
import static br.rodrigues.compras.util.ConstantsApp.OUTROS;
import static br.rodrigues.compras.util.ConstantsApp.TODOS;

public class FormularioHelper {

    private FormularioActivity activity;
    private EditText campoNome;
    private EditText campoPreco;
    private Spinner campoCategoria;
    private EditText campoObs;
    private Item itemOk;

    private final List<String> Categorias = CATEGORIAS;
    private EditText campoQuantidade;

    public FormularioHelper (FormularioActivity context) {
        activity = context;
        getActivityViews();
        itemOk = new Item();

        setupSpinnerCategoria();
    }

    private void setupSpinnerCategoria() {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_dropdown_item, Categorias);
        campoCategoria.setAdapter(spinnerAdapter);

        campoCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //do something a day...
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void fillItem (Item item){
        campoNome.setText(item.getNome());
        campoPreco.setText(String.valueOf(item.getPreco()));
        campoQuantidade.setText(String.valueOf(item.getQuantidade()));
        campoObs.setText(item.getObservacao());
        campoCategoria.setSelection(item.getCategoria());
        itemOk = item;
    }

    public Item getItem (){

        itemOk.setNome(campoNome.getText().toString());
        itemOk.setObservacao(campoObs.getText().toString());
        itemOk.setCategoria(campoCategoria.getSelectedItemPosition());
        itemOk.setComprado(false);
        itemOk.setDataCompra(Calendar.getInstance().getTimeInMillis());

        itemOk.setCaminhoImagem(setIconByCategoria(CATEGORIAS.get(campoCategoria.getSelectedItemPosition())));

        if (!campoPreco.getText().toString().isEmpty()) {
            itemOk.setPreco(Double.parseDouble(campoPreco.getText().toString()
                    .replace(",",".")
                    .replace("£","")
                    .replace("$",".")
                    .replace("R$",".")));
        } else {
            itemOk.setPreco(0.0);
        }

        if (!campoQuantidade.getText().toString().isEmpty()) {
            itemOk.setQuantidade(Integer.valueOf(campoQuantidade.getText().toString()));
        } else {
            itemOk.setQuantidade(1);
        }

        itemOk.setPrecoTotal(itemOk.getPreco()*itemOk.getQuantidade());

        return itemOk;
    }

    private int setIconByCategoria(String categoria) {

        switch (categoria){
            case OUTROS:
                return R.mipmap.ic__item_image_outros;

            case ACOUGUE:
                return R.mipmap.ic__item_image_acougue;

            case HORTIFRUTI:
                return R.mipmap.ic__item_image_hortifruti;

            case BEBIDAS:
                return R.mipmap.ic__item_image_bebidas;

            case MASSAS:
                return R.mipmap.ic__item_image_massa;

            case GRAOS:
                return R.mipmap.ic__item_image_graos;

            case HIGIENE:
                return R.mipmap.ic__item_image_higiene;
        }
        return 0;
    }

    private void getActivityViews() {
        campoNome = activity.findViewById(R.id.activity_formulario_nome);
        campoPreco = activity.findViewById(R.id.activity_formulario_preco);

//        campoPreco.addTextChangedListener(MaskEditUtil.monetario(campoPreco));

        campoQuantidade = activity.findViewById(R.id.activity_formulario_quantidade);
        campoCategoria = activity.findViewById(R.id.activity_formulario_categoria);
        campoObs = activity.findViewById(R.id.activity_formulario_obs);
        campoCategoria = activity.findViewById(R.id.activity_formulario_categoria);
    }

    public boolean checkingNameEmpty() {
        if (campoNome.getText().toString().isEmpty()){
            new AlertDialog.Builder(activity)
                    .setTitle(R.string.alert_default)
                    .setIcon(android.R.drawable.ic_dialog_alert)
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

        List<Item> items = dao.getAllItems(TODOS);

        for (Item i: items){

            if (i.getNome().equalsIgnoreCase(campoNome.getText().toString())) {

                new AlertDialog.Builder(activity)
                        .setTitle(R.string.alert_default)
                        .setIcon(android.R.drawable.ic_dialog_alert)
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
        campoQuantidade.setText(null);
        campoObs.setText(null);
        campoCategoria.setSelection(0);
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