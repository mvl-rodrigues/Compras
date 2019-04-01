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
    public static final String TITLE_APPBAR_EDITA = "Edita Item";
    //    private RadioGroup campoFrequencia;
//    private Spinner campoCategoria;
//    private EditText campoPreco;
//    private EditText campoNome;
    private Item theItem;
    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        setTitle(TITLE_APPBAR_NOVO);
//        getActivityViews();

        theItem = new Item();
        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        theItem = (Item) intent.getSerializableExtra("atualizaItem");

        if (theItem != null){
            setTitle(TITLE_APPBAR_EDITA);
//            campoNome.setText(theItem.getNome());
//            campoPreco.setText(String.valueOf(theItem.getPreco()));
//            campoCategoria.setSelection(getSpinnerPosition(theItem.getCategoria()));
//            campoFrequencia.check(getIdRadioButton(theItem.getFrequencia()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_formulario_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ItemDAO dao = new ItemDAO(this);

        dao.create(helper.fillItem());
//        Toast.makeText(this, "Item "+ theItem.getNome() +" salvo! " + theItem.getPreco(), Toast.LENGTH_SHORT).show();

//        if (theItem.getId() == null){
//            if (helper.fillItem(theItem).getNome() == null) {
//                dao.create(helper.fillItem(theItem));
//                Toast.makeText(this, "Item "+ theItem.getNome() +" salvo! " + theItem.getPreco(), Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            if (helper.fillItem(theItem).getNome() == null) {
//                dao.update(helper.fillItem(theItem));
//                Toast.makeText(this, "Item "+ theItem.getNome() +" editado! "+ theItem.getId(), Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }
        return super.onOptionsItemSelected(item);
    }

//    private Boolean fillItem() {
//        theItem.setCategoria(campoCategoria.getSelectedItem().toString());
//        theItem.setFrequencia(getStringRadioButton());
//        return checkRequiredFields();
//    }
//
//    private Boolean checkRequiredFields() {
//        theItem.setNome(campoNome.getText().toString());
//        if (theItem.getNome().isEmpty()){
//            new AlertDialog.Builder(this)
//                    .setTitle(R.string.alert_default)
//                    .setMessage(R.string.activity_formulario_alert_empty_name)
//                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            campoNome.requestFocus();
//                        }
//                    })
//                    .show();
//            return false;
//        }
//        if (!campoPreco.getText().toString().isEmpty()) {
//            theItem.setPreco(Double.valueOf(campoPreco.getText().toString()));
//        } else {
//            theItem.setPreco(0.0);
//        }
//        return true;
//    }
//
//    public int getSpinnerPosition (String spinnerString){
//        //.getCount(): pega a quantidade de itens que o Spinner possui
//        //percorre por todos os itens do spinner
//        for (int i = 0; i < campoCategoria.getCount(); i++){
//            //verifica se
//            if (campoCategoria.getItemAtPosition(i).toString().equalsIgnoreCase(spinnerString)){
//                return i;
//            }
//        }
//        return 0;
//    }
//
//    public String getStringRadioButton (){
//        //identificando o id do radioButton pelo radioGroup
//        int selectedId = campoFrequencia.getCheckedRadioButtonId();
//        //linkando o radioButton selecionado a variável radioButtonTipo
//        RadioButton frequencia = findViewById(selectedId);
//        //Convertendo o valor do radioButton para String
//        return (String) frequencia.getText();
//    }
//
//    public int getIdRadioButton (String radioButtonString){
//        //getChildCount(): quantidade de radiobuttons no radioGroup
//        //percorre os buttons do radioGroup
//        for (int i = 0; i < campoFrequencia.getChildCount() ; i++){
//            //getChildAt(i).getId(): pega o id de do radioButton da posição i
//            RadioButton frequencia = findViewById(campoFrequencia.getChildAt(i).getId());
//            //verifica se a radioButtonString está em algum dos buttons do radioGroup
//            if (frequencia.getText().equals(radioButtonString)){
//                return campoFrequencia.getChildAt(i).getId();
//            }
//        }
//        return 0;
//    }
//
//    private void getActivityViews() {
//        campoNome = findViewById(R.id.activity_formulario_nome);
//        campoPreco = findViewById(R.id.activity_formulario_preco);
//        campoCategoria = findViewById(R.id.activity_formulario_categoria);
//        campoFrequencia = findViewById(R.id.activity_formulario_frequencia);
//    }
}