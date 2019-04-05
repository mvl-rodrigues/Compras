package br.rodrigues.compras.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.rodrigues.compras.R;
import br.rodrigues.compras.dao.ItemDAO;
import br.rodrigues.compras.model.Item;

public class FormularioActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR_NOVO = "Novo Item";
    public static final String TITLE_APPBAR_EDITA = "Editar Item";
    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        setTitle(TITLE_APPBAR_NOVO);

        helper = new FormularioHelper(this);

        Intent intent = getIntent();

        Item itemOk = (Item) intent.getSerializableExtra("itemToUpdate");

        if (itemOk != null){
            setTitle(TITLE_APPBAR_EDITA);
            helper.fillItem(itemOk);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.activity_formulario_menu_ok:

                Item itemOk = helper.getItem();

                ItemDAO dao = new ItemDAO(this);

                if (itemOk.getId() == null) {
                    itemOk.setComprado(false);
                    dao.create(itemOk);
                    Toast.makeText(this, "Item "+itemOk.getNome()+" adicionado.", Toast.LENGTH_SHORT).show();
                } else {
                    dao.update(itemOk);
                    finish();
                    Toast.makeText(this, "Item "+itemOk.getNome()+" editado.", Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_formulario_menu, menu);
        return true;
    }

}