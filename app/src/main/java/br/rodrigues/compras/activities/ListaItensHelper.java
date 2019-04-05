package br.rodrigues.compras.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.rodrigues.compras.R;
import br.rodrigues.compras.dao.ItemDAO;
import br.rodrigues.compras.model.Item;

public class ListaItensHelper {

    private ItemDAO dao;
    private ListaItensActivity activity;
    private TextView viewTotal;
    private TextView viewSubtotal;
    private ListView listaItens;
    private FloatingActionButton fab_add;
    private List<Item> itens;
    private ArrayAdapter adapter;

    public ListaItensHelper (ListaItensActivity context){
        activity = context;
        dao = new ItemDAO(activity);
        getViews();
        setupListaDeItens();
        setupFabAdd();
        setupShortClickListener();
    }

    private void getViews() {
        viewTotal = activity.findViewById(R.id.activity_lista_itens_total);
        viewSubtotal = activity.findViewById(R.id.activity_lista_itens_subtotal);
        listaItens = activity.findViewById(R.id.activity_lista_itens_listview);
        fab_add = activity.findViewById(R.id.activity_lista_itens_fab_add);
    }

    private void setupListaDeItens() {
        itens = dao.getAllItems();
        adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, itens);
        listaItens.setAdapter(adapter);
        activity.registerForContextMenu(listaItens);
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
        listaItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                updatedListItems();
                subtotalCalculation();
            }
        });
    }

    /**
     * Funcionalidades
     */

    public void subtotalCalculation() {
        Double custoSubtotal = 0.0;
        for (Item item: itens){
            if (!item.getComprado()){
                custoSubtotal = custoSubtotal + item.getPreco();
            }
        }
        viewSubtotal.setText(String.valueOf(custoSubtotal));
    }

    public void totalCalculation() {
        Double custoTotal = 0.0;
        for (Item item: itens){
            custoTotal = custoTotal + item.getPreco();
        }
        viewTotal.setText(String.valueOf(custoTotal));
    }

    public Item listaItensGetItemAtPosition (int position){
        Item item = (Item) listaItens.getItemAtPosition(position);
        return item;
    }

    public void updatedListItems() {
        itens = dao.getAllItems();
        adapter.clear();
        adapter.addAll(itens);
    }

    public void cleanList() {
        for(Item item: itens){
            if (item.getComprado()){
                item.setComprado(false);
                dao.update(item);
            }
            updatedListItems();
        }
    }

    public void deleteList() {
        dao.deleteAll();
    }
}
