package br.rodrigues.compras.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import br.rodrigues.compras.R;

import static br.rodrigues.compras.util.ConstantsApp.ACOUGUE;
import static br.rodrigues.compras.util.ConstantsApp.BEBIDAS;
import static br.rodrigues.compras.util.ConstantsApp.GRAOS;
import static br.rodrigues.compras.util.ConstantsApp.HIGIENE;
import static br.rodrigues.compras.util.ConstantsApp.HORTIFRUTI;
import static br.rodrigues.compras.util.ConstantsApp.MASSAS;
import static br.rodrigues.compras.util.ConstantsApp.OUTROS;
import static br.rodrigues.compras.util.ConstantsApp.TODOS;

public class ListaItensActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR = "Lista";
    private ListaItensHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_itens);
        setTitle(TITLE_APPBAR);
        helper = new ListaItensHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.activity_lista_itens_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.activity_lista_itens_menu_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String nomeProduto) {

                helper.searchInList(nomeProduto);

                if (nomeProduto.equals("")){
                    helper.updatedListItems();
                }

                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.activity_lista_itens_menu_renew:

                helper.cleanList();
                helper.subtotalCalculation();
                Toast.makeText(this, "Renovada", Toast.LENGTH_SHORT).show();

                break;
            case R.id.activity_lista_itens_menu_delete:

                helper.deleteList();

                break;
            case R.id.activity_lista_itens_menu_filter:

                View menuFilter = findViewById(R.id.activity_lista_itens_menu_filter);

                PopupMenu popup = new PopupMenu(ListaItensActivity.this, menuFilter);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.item_categorias, popup.getMenu());
                popup.show();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.item_categorias_todos:
                                helper.filterByCategory(TODOS);
                                Toast.makeText(ListaItensActivity.this, "Todos", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.item_categorias_outros:
                                helper.filterByCategory(OUTROS);
                                Toast.makeText(ListaItensActivity.this, "Outros", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.item_categorias_acougue:
                                helper.filterByCategory(ACOUGUE);
                                Toast.makeText(ListaItensActivity.this, "Açougue", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.item_categorias_bebidas:
                                helper.filterByCategory(BEBIDAS);
                                Toast.makeText(ListaItensActivity.this, "Bebidas", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.item_categorias_graos:
                                helper.filterByCategory(GRAOS);
                                Toast.makeText(ListaItensActivity.this, "Grãos", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.item_categorias_higiene:
                                helper.filterByCategory(HIGIENE);
                                Toast.makeText(ListaItensActivity.this, "Higiene", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.item_categorias_hortifruti:
                                helper.filterByCategory(HORTIFRUTI);
                                Toast.makeText(ListaItensActivity.this, "Hortifruti", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.item_categorias_massa:
                                helper.filterByCategory(MASSAS);
                                Toast.makeText(ListaItensActivity.this, "Massas", Toast.LENGTH_SHORT).show();
                                break;

                        }

                        return false;
                    }
                });

                break;
        }

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        helper.updatedListItems();
        helper.subtotalCalculation();
    }
}