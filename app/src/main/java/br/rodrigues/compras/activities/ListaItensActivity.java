package br.rodrigues.compras.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.rodrigues.compras.R;
import br.rodrigues.compras.dao.ItemDAO;
import br.rodrigues.compras.model.Item;

public class ListaItensActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR = "Lista";
    private ListView listaItens;
    private FloatingActionButton fab_add;
    private ItemDAO dao = new ItemDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_itens);
        setTitle(TITLE_APPBAR);
        pegaViews();
        configuraListaDeItens();
        ConfiguraFabAdd();
    }

    private void ConfiguraFabAdd() {
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaFormulario = new Intent(ListaItensActivity.this, FormularioActivity.class);
                startActivity(vaiParaFormulario);
            }
        });
    }

    private void configuraListaDeItens() {
        List<Item> Itens = dao.pegaItens();
        ArrayAdapter<Item> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Itens);
        listaItens.setAdapter(adapter);
        registerForContextMenu(listaItens);
    }

    private void pegaViews() {
        listaItens = findViewById(R.id.activity_lista_itens_listview);
        fab_add = findViewById(R.id.activity_lista_itens_fab_add);
    }
}
