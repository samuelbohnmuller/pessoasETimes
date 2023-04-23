package com.example.calculadoradeimc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexaoBanco extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dbT";

    public static final int DATABASE_VERSION = 1;

    public ConexaoBanco(Context context, String database_name, int i) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE organizacaoEmpresa (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "empresa VARCHAR(50)," +
                "empregado VARCHAR(50)" +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
