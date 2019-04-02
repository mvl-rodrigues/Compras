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

    public static final String TABLE = "Itens";
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

    private ContentValues getItemData(Item item) {

        ContentValues dados = new ContentValues();

        dados.put("nome", item.getNome());
        dados.put("preco", item.getPreco());
        dados.put("categoria", item.getCategoria());
        dados.put("observacao", item.getObservacao());
        dados.put("frequencia", item.getFrequencia());
        dados.put("comprado", item.getComprado());

        return dados;
    }

    public void create(Item item) {
        db = conexao.getWritableDatabase();

        ContentValues data = getItemData(item);

        db.insert(TABLE, null, data);

        db.close();
    }

    public void update(Item item) {

        db = conexao.getWritableDatabase();

        ContentValues data = getItemData(item);

        String[] params = {item.getId().toString()};

        db.update(TABLE, data, "id = ?", params);

        db.close();
    }

    public void delete (Item item){

        db = conexao.getWritableDatabase();

        String[] params = {item.getId().toString()};

        db.delete(TABLE, "id = ?", params);
    }
}
