package br.rodrigues.compras.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoSQLite extends SQLiteOpenHelper {

    private static final String BASE = "Compras";
    private static final int VERSION = 1;

    public BancoSQLite(Context context) {
        super(context, BASE, null, VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
