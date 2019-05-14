package br.rodrigues.compras.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoSQLite extends SQLiteOpenHelper {

    private static final String DATA_BASE = "Compras";
    private static final int VERSION = 3;
    private static final String TABLE = "Itens";

    public BancoSQLite(Context context) {
        super(context, DATA_BASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE +
                "(id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "preco REAL, " +
                "preco_total REAL, " +
                "quantidade INTEGER, " +
                "observacao TEXT, " +
                "categoria INTEGER, " +
                "data_compra DATE, " +
                "caminho_imagem TEXT, " +
                "comprado BOOLEAN);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String sql = "DROP TABLE IF EXISTS " + TABLE;
//        db.execSQL(sql);
//        onCreate(db);

        String sql = "";

        switch (oldVersion){
            case 1:
                sql = "ALTER TABLE " + TABLE + " ADD COLUMN quantidade;";
                db.execSQL(sql);
            case 2:
                sql = "ALTER TABLE " + TABLE + " ADD COLUMN preco_total;";
                db.execSQL(sql);
            case 3:
                //....
        }
    }

//    @Override
//    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }
}
