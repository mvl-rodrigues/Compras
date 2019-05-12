package br.rodrigues.compras.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.rodrigues.compras.model.Item;
import br.rodrigues.compras.repository.BancoSQLite;
import static br.rodrigues.compras.util.ConstantsApp.*;

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
            sql = "SELECT * FROM " + TABLE + " WHERE categoria = " + CATEGORIA;
        }
        else if (CATEGORIA.equals(ACOUGUE)) {
            sql = "SELECT * FROM " + TABLE + " WHERE categoria = " + CATEGORIA;
        }
        else if (CATEGORIA.equals(HORTIFRUTI)) {
            sql = "SELECT * FROM " + TABLE + " WHERE categoria = " + CATEGORIA;
        }
        else if (CATEGORIA.equals(GRAOS)) {
            sql = "SELECT * FROM " + TABLE + " WHERE categoria = " + CATEGORIA;
        }
        else if (CATEGORIA.equals(MASSAS)) {
            sql = "SELECT * FROM " + TABLE + " WHERE categoria = " + CATEGORIA;
        }
        else if (CATEGORIA.equals(BEBIDAS)) {
            sql = "SELECT * FROM " + TABLE + " WHERE categoria = " + CATEGORIA;
        }
        else if (CATEGORIA.equals(HIGIENE)) {
            sql = "SELECT * FROM " + TABLE + " WHERE categoria = " + CATEGORIA;
        }

        /**
         * OK
         */

        db = conexao.getReadableDatabase();

        Cursor ponteiro = db.rawQuery(sql, null);

        List<Item> itens = new ArrayList<>();

        while (ponteiro.moveToNext()) {
            Item item = new Item();

            item.setId(ponteiro.getLong(ponteiro.getColumnIndex("id")));
            item.setNome(ponteiro.getString(ponteiro.getColumnIndex("nome")));
            item.setPreco(ponteiro.getDouble(ponteiro.getColumnIndex("preco")));
            item.setCategoria(ponteiro.getString(ponteiro.getColumnIndex("categoria")));

            item.setDataCompra(ponteiro.getLong(ponteiro.getColumnIndex("data_compra")));

            item.setObservacao(ponteiro.getString(ponteiro.getColumnIndex("observacao")));
            item.setComprado(Boolean.parseBoolean(ponteiro.getString(ponteiro.getColumnIndex("comprado"))));

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
        dados.put("categoria", item.getCategoria());

        dados.put("data_compra", item.getDataCompra());

        dados.put("observacao", item.getObservacao());
        dados.put("comprado", String.valueOf(item.getComprado()));

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
                "observacao TEXT, " +
                "categoria TEXT, " +
                "data_compra DATE, " +
                "caminho_imagem TEXT, " +
                "comprado BOOLEAN);";

        db.execSQL(sql);

        db.close();
    }
}
