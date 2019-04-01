package br.rodrigues.compras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.rodrigues.compras.model.Item;
import br.rodrigues.compras.repository.BancoSQLite;

public class ItemDAO {

    private BancoSQLite conexao;
    private SQLiteDatabase db;

    public ItemDAO (Context context) {
        conexao = new BancoSQLite(context);
    }

    public List<Item> getAllItems() {
        String sql = "SELECT * FROM Itens";

        db = conexao.getReadableDatabase();

        Cursor ponteiro = db.rawQuery(sql, null);

        List<Item> itens = new ArrayList<>();

        while (ponteiro.moveToNext()){
            Item item = new Item();

            item.setId(ponteiro.getLong(ponteiro.getColumnIndex("id")));
            item.setNome(ponteiro.getString(ponteiro.getColumnIndex("nome")));
            item.setPreco(ponteiro.getDouble(ponteiro.getColumnIndex("preco")));
            item.setCategoria(ponteiro.getString(ponteiro.getColumnIndex("categoria")));
            item.setObservacao(ponteiro.getString(ponteiro.getColumnIndex("observacao")));
            item.setFrequencia(ponteiro.getString(ponteiro.getColumnIndex("frequencia")));
            item.setComprado(Boolean.parseBoolean(ponteiro.getString(ponteiro.getColumnIndex("comprado"))));

            itens.add(item);
        }
        ponteiro.close();

        return itens;
    }

    public void create(Item itemOk) {
        db = conexao.getWritableDatabase();

        ContentValues dados = getItemData(itemOk);

        db.insert("Itens", null, dados);
    }

    private ContentValues getItemData(Item itemOk) {

        ContentValues dados = new ContentValues();

        dados.put("nome", itemOk.getNome());
        dados.put("preco", itemOk.getPreco());
        dados.put("categoria", itemOk.getCategoria());
        dados.put("observacao", itemOk.getObservacao());
        dados.put("frequencia", itemOk.getFrequencia());
        dados.put("comprado", itemOk.getComprado());

        return dados;
    }

    public void update(Item itemOk) {

    }
}
