package br.rodrigues.compras.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import br.rodrigues.compras.R;

public class FormularioActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR = "Novo Item";
    private RadioGroup campoFrequencia;
    private Spinner campoCategoria;
    private EditText campoPreco;
    private EditText campoNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        setTitle(TITLE_APPBAR);
        pegaViews();

    }

    private void pegaViews() {
        campoNome = findViewById(R.id.activity_formulario_nome);
        campoPreco = findViewById(R.id.activity_formulario_preco);
        campoCategoria = findViewById(R.id.activity_formulario_categoria);
        campoFrequencia = findViewById(R.id.activity_formulario_frequencia);
    }
}
