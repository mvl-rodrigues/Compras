package br.rodrigues.compras.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import br.rodrigues.compras.R;
import br.rodrigues.compras.dao.ItemDAO;
import br.rodrigues.compras.model.Item;
import static br.rodrigues.compras.util.ConstantsApp.TODOS;

public class ListaItensHelper {

    private ItemDAO dao;
    private ListaItensActivity activity;
    private TextView viewTotal;
    private TextView viewSubtotal;
    private ListView listaItems;
    private FloatingActionButton fab_add;
    private List<Item> items;
    private ItemAdapter adapter;
    private Button btn_filtrar;

    public ListaItensHelper (ListaItensActivity context){
        activity = context;
        dao = new ItemDAO(activity);
        getViews();
        setupFabAdd();
        setupShortClickListener();
        setupListaDeItens();
        setupBtnFiltrar();
    }

    public ListaItensHelper (){

    }

    /*************************************************
     * SETTINGS HELPER CLASS
     *************************************************/

    private void getViews() {
        viewTotal = activity.findViewById(R.id.activity_lista_itens_total);
        viewSubtotal = activity.findViewById(R.id.activity_lista_itens_subtotal);
        listaItems = activity.findViewById(R.id.activity_lista_itens_listview);
        fab_add = activity.findViewById(R.id.activity_lista_itens_fab_add);

        btn_filtrar = activity.findViewById(R.id.activity_lista_itens_btn_filtrar);
    }

    private void setupListaDeItens() {
        items = dao.getAllItems(TODOS);
        adapter = new ItemAdapter(activity, items);
        listaItems.setAdapter(adapter);
        activity.registerForContextMenu(listaItems);
    }

    private void setupFabAdd() {
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaFormulario = new Intent(activity, FormularioActivity.class);
                activity.startActivity(vaiParaFormulario);
            }
        });
    }

    private void setupBtnFiltrar() {
        btn_filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.registerForContextMenu(btn_filtrar);
                activity.openContextMenu(btn_filtrar);
                activity.unregisterForContextMenu(btn_filtrar);
            }
        });
    }

    private void setupShortClickListener() {
        listaItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {

                Item itemToComprado = (Item) lista.getItemAtPosition(position);

                if (itemToComprado.getComprado()) {
                    itemToComprado.setComprado(false);
                    dao.update(itemToComprado);
                } else {
                    itemToComprado.setComprado(true);
                    dao.update(itemToComprado);
                }
                adapter.simpleUpdate();
                subtotalCalculation();
            }
        });
    }

    /*************************************************
     * METHODS TO HELP ACTIVITY
     *************************************************/

    public String formatarEmReais(double valor) {
        Locale REAL_BR = new Locale("pt", "BR");
        NumberFormat valorEmReais = NumberFormat.getInstance(REAL_BR);
        valorEmReais.setMinimumFractionDigits(2);
        return valorEmReais.format(valor);
    }

    public void subtotalCalculation() {
        Double custoSubtotal = 0.0;
        for (Item item: items){
            if (!item.getComprado()){
                custoSubtotal = custoSubtotal + item.getPreco();
            }
        }
        viewSubtotal.setText(formatarEmReais(custoSubtotal));
    }

    public void totalCalculation() {
        Double custoTotal = 0.0;
        for (Item item: items){
            custoTotal = custoTotal + item.getPreco();
        }
        viewTotal.setText(formatarEmReais(custoTotal));
    }

    public Item listaItensGetItemAtPosition (int position){
        Item item = (Item) listaItems.getItemAtPosition(position);
        return item;
    }

    public void updatedListItems() {
        items = dao.getAllItems(TODOS);
        adapter.update(items);
    }

    public void cleanList() {
        for(Item item: items){
            if (item.getComprado()){
                item.setComprado(false);
                dao.update(item);
            }
            updatedListItems();
        }
    }

    public void deleteList() {

        new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Atenção!")
                .setMessage("Você realmente deseja deletar toda a lista?")
                .setNegativeButton("Não", null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.deleteAll();
                        Toast.makeText(activity, "Lista deletada", Toast.LENGTH_SHORT).show();
                        updatedListItems();
                    }
                }).create().show();
    }

    public void filterByCategory(String categoria) {
        items = dao.getAllItems(categoria);
        adapter.update(items);

    }
}