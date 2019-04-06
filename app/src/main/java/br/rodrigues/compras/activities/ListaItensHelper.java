package br.rodrigues.compras.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.rodrigues.compras.R;
import br.rodrigues.compras.dao.ItemDAO;
import br.rodrigues.compras.model.Item;

public class ListaItensHelper {

    private ItemDAO dao;
    private ListaItensActivity activity;
    private TextView viewTotal;
    private TextView viewSubtotal;
    private ListView listaItems;
    private FloatingActionButton fab_add;
    private List<Item> items;
    private ArrayAdapter adapter;
    private Spinner spinnerSort;
    private List<Item> itemsHelper;
    private int position = -1;

    public ListaItensHelper (ListaItensActivity context){
        activity = context;
        dao = new ItemDAO(activity);
        itemsHelper = new ArrayList<>();
        getViews();
        setupListaDeItens();
        setupFabAdd();
        setupShortClickListener();
        setupSpinnerSort();
    }

    /*************************************************
     * SETTINGS HELPER CLASS
     *************************************************/

    private void getViews() {
        viewTotal = activity.findViewById(R.id.activity_lista_itens_total);
        viewSubtotal = activity.findViewById(R.id.activity_lista_itens_subtotal);
        listaItems = activity.findViewById(R.id.activity_lista_itens_listview);
        fab_add = activity.findViewById(R.id.activity_lista_itens_fab_add);
        spinnerSort = activity.findViewById(R.id.activity_lista_itens_ordena);
    }

    private void setupSpinnerSort() {
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    sortListItems(position);
                } else {
                    updatedListItems();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupListaDeItens() {
        items = dao.getAllItems();
        adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, items);
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
                chooseUpdate();
                subtotalCalculation();
            }
        });
    }

    private void chooseUpdate() {
        if (this.position == -1){
            updatedListItems();
        } else {
            updatedItemsHelper();
        }
    }

    /*************************************************
     * METHODS TO HELP ACTIVITY
     *************************************************/

    public void sortListItems (int position){
        this.position = position;
        itemsHelper.clear();
        for (Item i: items) {
            if (i.getCategoria() == position) {
                itemsHelper.add(i);
            }
        }
        adapter.clear();
        adapter.addAll(itemsHelper);
    }

    public void subtotalCalculation() {
        Double custoSubtotal = 0.0;
        for (Item item: items){
            if (!item.getComprado()){
                custoSubtotal = custoSubtotal + item.getPreco();
            }
        }
        viewSubtotal.setText(String.valueOf(custoSubtotal));
    }

    public void totalCalculation() {
        Double custoTotal = 0.0;
        for (Item item: items){
            custoTotal = custoTotal + item.getPreco();
        }
        viewTotal.setText(String.valueOf(custoTotal));
    }

    public Item listaItensGetItemAtPosition (int position){
        Item item = (Item) listaItems.getItemAtPosition(position);
        return item;
    }

    public void updatedItemsHelper() {
        items = dao.getAllItems();
        adapter.clear();
        itemsHelper.clear();

        for (Item i: items) {
            if (i.getCategoria() == position) {
                itemsHelper.add(i);
            }
        }
        adapter.addAll(itemsHelper);
    }

    public void updatedListItems() {
        items = dao.getAllItems();
        adapter.clear();
        adapter.addAll(items);
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
}
