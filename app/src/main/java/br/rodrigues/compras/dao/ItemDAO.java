package br.rodrigues.compras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.rodrigues.compras.model.Item;
import br.rodrigues.compras.repository.BancoSQLite;

import static br.rodrigues.compras.util.ConstantsApp.ACOUGUE;
import static br.rodrigues.compras.util.ConstantsApp.BEBIDAS;
import static br.rodrigues.compras.util.ConstantsApp.GRAOS;
import static br.rodrigues.compras.util.ConstantsApp.HIGIENE;
import static br.rodrigues.compras.util.ConstantsApp.HORTIFRUTI;
import static br.rodrigues.compras.util.ConstantsApp.MASSAS;
import static br.rodrigues.compras.util.ConstantsApp.OUTROS;

public class ItemDAO {

    public static final String TABLE = "Itens";
    private BancoSQLite conexao;
    private SQLiteDatabase db;

    public ItemDAO(Context context) {
        conexao = new BancoSQLite(context);
    }

    public List<Item> getAllItems(String CATEGORIA) {

        String sql = "SELECT * FROM " + TABLE;

        if (CATEGORIA.equals(OUTROS)) {
            sql = "SELECT * FROM " + TABLE + " WHERE categoria = " + 0;
        }
        else if (CATEGORIA.equals(ACOUGUE)) {
            sql = "SELECT * FROM " + TABLE + " WHERE categoria = " + 1;
        }
        else if (CATEGORIA.equals(HORTIFRUTI)) {
            sql = "SELECT * FROM " + TABLE + " WHERE categoria = " + 2;
        }
        else if (CATEGORIA.equals(GRAOS)) {
            sql = "SELECT * FROM " + TABLE + " WHERE categoria = " + 3;
        }
        else if (CATEGORIA.equals(MASSAS)) {
            sql = "SELECT * FROM " + TABLE + " WHERE categoria = " + 4;
        }
        else if (CATEGORIA.equals(BEBIDAS)) {
            sql = "SELECT * FROM " + TABLE + " WHERE categoria = " + 5;
        }
        else if (CATEGORIA.equals(HIGIENE)) {
            sql = "SELECT * FROM " + TABLE + " WHERE categoria = " + 6;
        }

        db = conexao.getReadableDatabase();

        Cursor ponteiro = db.rawQuery(sql, null);

        List<Item> itens = new ArrayList<>();

        while (ponteiro.moveToNext()) {
            Item item = new Item();

            item.setId(ponteiro.getLong(ponteiro.getColumnIndex("id")));
            item.setNome(ponteiro.getString(ponteiro.getColumnIndex("nome")));
            item.setPreco(ponteiro.getDouble(ponteiro.getColumnIndex("preco")));
            item.setPrecoTotal(ponteiro.getDouble(ponteiro.getColumnIndex("preco_total")));
            item.setCategoria(ponteiro.getInt(ponteiro.getColumnIndex("categoria")));
            item.setDataCompra(ponteiro.getLong(ponteiro.getColumnIndex("data_compra")));
            item.setObservacao(ponteiro.getString(ponteiro.getColumnIndex("observacao")));
            item.setComprado(Boolean.parseBoolean(ponteiro.getString(ponteiro.getColumnIndex("comprado"))));
            item.setQuantidade(ponteiro.getInt(ponteiro.getColumnIndex("quantidade")));
            item.setCaminhoImagem(ponteiro.getInt(ponteiro.getColumnIndex("caminho_imagem")));

            itens.add(item);
        }
        ponteiro.close();
        db.close();

        return itens;
    }

    private ContentValues getItemData(Item item) {
        ContentValues dados = new ContentValues();

        dados.put("nome", item.getNome());
        dados.put("preco", item.getPreco());
        dados.put("preco_total", item.getPrecoTotal());
        dados.put("categoria", item.getCategoria());
        dados.put("data_compra", item.getDataCompra());
        dados.put("observacao", item.getObservacao());
        dados.put("comprado", String.valueOf(item.getComprado()));
        dados.put("quantidade", item.getQuantidade());
        dados.put("caminho_imagem", item.getCaminhoImagem());

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

    public void delete(Item item) {
        db = conexao.getWritableDatabase();

        String[] params = {item.getId().toString()};

        db.delete(TABLE, "id = ?", params);

        db.close();
    }

    public void deleteAll() {
        db = conexao.getWritableDatabase();

        String sql = "DROP TABLE " + TABLE;

        db.execSQL(sql);

        sql = "CREATE TABLE " + TABLE +
                "(id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "preco REAL, " +
                "quantidade INTEGER, " +
                "observacao TEXT, " +
                "categoria INTEGER, " +
                "data_compra DATE, " +
                "caminho_imagem TEXT, " +
                "comprado BOOLEAN);";

        db.execSQL(sql);

        db.close();
    }
}
