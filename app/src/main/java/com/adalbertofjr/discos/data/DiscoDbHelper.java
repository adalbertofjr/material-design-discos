package com.adalbertofjr.discos.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AdalbertoF on 01/02/2016.
 */
public class DiscoDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "discosDB";
    private static final int DB_VERSION = 1;

    public DiscoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE_DISCO = "CREATE TABLE " +
                DiscoContract.TABLE_DISCO + " (" +
                DiscoContract.COL_DISCO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DiscoContract.COL_TITULO + " TEXT NOT NULL UNIQUE, " +
                DiscoContract.COL_CAPA + " TEXT, " +
                DiscoContract.COL_CAPA_BIG + " TEXT, " +
                DiscoContract.COL_ANO + " INTEGER, " +
                DiscoContract.COL_GRAVADORA + " TEXT )";

        String SQL_CREATE_TABLE_INTEGRANTES = "CREATE TABLE " +
                DiscoContract.TABLE_INTEGRANTES + " (" +
                DiscoContract.COL_INTEGRANTE_DISCO_ID + " INTEGER, " +
                DiscoContract.COL_INTEGRANTE + " TEXT, " +
                "FOREIGN KEY (" + DiscoContract.COL_INTEGRANTE_DISCO_ID + ") " +
                "REFERENCES " + DiscoContract.TABLE_DISCO +
                "(" + DiscoContract.COL_DISCO_ID + ") " +
                "ON DELETE CASCADE )";

        String SQL_CREATE_TABLE_MUSICAS = "CREATE TABLE " +
                DiscoContract.TABLE_MUSICAS + " (" +
                DiscoContract.COL_MUSICA_DISCO_ID + " INTEGER, " +
                DiscoContract.COL_MUSICA_NUM + " INTEGER, " +
                DiscoContract.COL_MUSICA + " TEXT, " +
                "FOREIGN KEY (" + DiscoContract.COL_MUSICA_DISCO_ID + ") " +
                "REFERENCES " + DiscoContract.TABLE_DISCO +
                "(" + DiscoContract.COL_DISCO_ID + ") " +
                "ON DELETE CASCADE )";

        db.execSQL(SQL_CREATE_TABLE_DISCO);
        db.execSQL(SQL_CREATE_TABLE_INTEGRANTES);
        db.execSQL(SQL_CREATE_TABLE_MUSICAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
