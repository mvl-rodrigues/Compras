package br.rodrigues.compras.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.rodrigues.compras.R;
import br.rodrigues.compras.dao.ItemDAO;
import br.rodrigues.compras.model.Item;

public class ListaItensActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR = "Lista";
    private ListView listaItens;
    private FloatingActionButton fab_add;
    private ItemDAO dao = new ItemDAO(this);
    private List<Item> itens;
    private ArrayAdapter<Item> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_itens);
        setTitle(TITLE_APPBAR);
        getViews();
        setupListaDeItens();
        setupFabAdd();
        setupShortClickListener();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                Item itemToDelete = (Item) listaItens.getItemAtPosition(info.position);

                ItemDAO dao = new ItemDAO(ListaItensActivity.this);

                dao.delete(itemToDelete);

                updatedListItems();

                return false;
            }
        });

        MenuItem atualizar = menu.add("Editar");
        atualizar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

                Item itemToUpdate = (Item) listaItens.getItemAtPosition(menuInfo.position);

                Intent goToUpdate = new Intent(ListaItensActivity.this, FormularioActivity.class);

                goToUpdate.putExtra("itemToUpdate", itemToUpdate);

                startActivity(goToUpdate);
                return false;
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
            }
        });
    }

    private void setupFabAdd() {
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaFormulario = new Intent(ListaItensActivity.this, FormularioActivity.class);
                startActivity(vaiParaFormulario);
            }
        });
    }

    private void setupListaDeItens() {
        itens = dao.getAllItems();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itens);
        listaItens.setAdapter(adapter);
        registerForContextMenu(listaItens);
    }

    private void getViews() {
        listaItens = findViewById(R.id.activity_lista_itens_listview);
        fab_add = findViewById(R.id.activity_lista_itens_fab_add);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatedListItems();
    }

    private void updatedListItems() {
        itens = dao.getAllItems();
        adapter.clear();
        adapter.addAll(itens);
    }
}