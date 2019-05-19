package br.rodrigues.compras.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import br.rodrigues.compras.R;
import br.rodrigues.compras.dao.ItemDAO;
import br.rodrigues.compras.model.Item;

import static br.rodrigues.compras.util.ConstantsApp.TODOS;

public class ItensHelper {

    private ItemDAO dao;
    private ItensActivity activity;
    private TextView valorLista;
    private ListView listaItems;
    private FloatingActionButton fab_add;
    private List<Item> items;
    private ItemAdapter adapter;
    private ImageButton btn_valor;
    private List<Item> itemsToCalc;

    public ItensHelper(ItensActivity context){
        activity = context;
        dao = new ItemDAO(activity);
        getViews();
        setupFabAdd();
        setupShortClickListener();
        setupListaDeItens();
        setupBtnValorTotal();
    }

    public ItensHelper(){

    }

    /*************************************************
     * SETTINGS HELPER CLASS
     *************************************************/

    private void getViews() {
        valorLista = activity.findViewById(R.id.activity_lista_itens_total);
        listaItems = activity.findViewById(R.id.activity_lista_itens_listview);
        fab_add = activity.findViewById(R.id.activity_lista_itens_fab_add);
        btn_valor = activity.findViewById(R.id.activity_lista_items_btn_valor_total);
    }

    private void setupBtnValorTotal() {
        btn_valor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(activity)
                        .setIcon(R.drawable.ic_lista_itens_money_2)
                        .setTitle("O valor total da lista é R$" + currentMonetaryFormat(totalCalculation()))
                        .setNeutralButton("Ok",null)
                        .setCancelable(true)
                        .create().show();
            }
        });
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

    private void setupShortClickListener() {
        listaItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {

                Item itemToComprado = (Item) lista.getItemAtPosition(position);

                if (itemToComprado.getComprado()) {
                    itemToComprado.setComprado(false);
                    dao.update(itemToComprado);

                    adapter.toFristPosition(position, itemToComprado);

//                    adapter.simpleUpdate();

                } else {
                    itemToComprado.setComprado(true);
                    itemToComprado.setDataCompra(new Date().getTime());

                    adapter.toLastPosition(position, itemToComprado);

                    dao.update(itemToComprado);
                }
                subtotalCalculation();
            }
        });
    }

    /*************************************************
     * METHODS TO HELP ACTIVITY
     *************************************************/

    public String currentMonetaryFormat(double valor) {
        NumberFormat currentMonetary = NumberFormat.getCurrencyInstance();
        currentMonetary.setMinimumFractionDigits(2);
        return currentMonetary.format(valor);
    }

    public void subtotalCalculation() {
        Double custoSubtotal = 0.0;
        itemsToCalc = dao.getAllItems("Todos");
        for (Item item: itemsToCalc){
            if (!item.getComprado()){
                custoSubtotal = custoSubtotal + item.getPrecoTotal();
            }
        }
        valorLista.setText(currentMonetaryFormat(custoSubtotal));
    }

    public double totalCalculation() {
        Double custoTotal = 0.0;
        for (Item item: itemsToCalc){
            custoTotal = custoTotal + item.getPrecoTotal();
        }
        return custoTotal;
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
                        subtotalCalculation();
                    }
                }).create().show();
    }

    public void filterByCategory(String categoria) {
        items = dao.getAllItems(categoria);
        adapter.update(items);

    }

    public void searchInList(String nomeProduto) {
        adapter.clear();
        for (Item item: items){
            if (item.getNome().toLowerCase().contains(nomeProduto.toLowerCase())) {
                adapter.add(item);
            }
        }
    }
}