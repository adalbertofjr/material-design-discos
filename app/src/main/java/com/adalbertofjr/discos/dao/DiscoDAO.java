package com.adalbertofjr.discos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.adalbertofjr.discos.data.DiscoContract;
import com.adalbertofjr.discos.data.DiscoDbHelper;
import com.adalbertofjr.discos.dominio.Disco;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AdalbertoF on 01/02/2016.
 */
public class DiscoDAO {
    private static final String LOG_TAG = DiscoDAO.class.getSimpleName();
    private DiscoDbHelper mDbHelper;
    private SQLiteDatabase mDb;

    public DiscoDAO(Context context) {
        this.mDbHelper = new DiscoDbHelper(context.getApplicationContext());
    }

    public void inserir(Disco disco) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(DiscoContract.COL_TITULO, disco.titulo);
            cv.put(DiscoContract.COL_ANO, disco.ano);
            cv.put(DiscoContract.COL_GRAVADORA, disco.gravadora);
            cv.put(DiscoContract.COL_CAPA, disco.capa);
            cv.put(DiscoContract.COL_CAPA_BIG, disco.capaGrande);

            db.beginTransaction();

            long idDisco = db.insert(DiscoContract.TABLE_DISCO, null, cv);

            if (idDisco != -1) {
                for (String integrante : disco.formacao) {
                    cv.clear();
                    cv.put(DiscoContract.COL_INTEGRANTE_DISCO_ID, idDisco);
                    cv.put(DiscoContract.COL_INTEGRANTE, integrante);
                    db.insert(DiscoContract.TABLE_INTEGRANTES, null, cv);
                }
                int musicaNum = 1;
                for (String musica : disco.faixas) {
                    cv.clear();
                    cv.put(DiscoContract.COL_MUSICA_DISCO_ID, idDisco);
                    cv.put(DiscoContract.COL_MUSICA_NUM, musicaNum);
                    cv.put(DiscoContract.COL_MUSICA, musica);
                    db.insert(DiscoContract.TABLE_MUSICAS, null, cv);
                }
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Inserir: " + e.getMessage());
        } finally {
            db.endTransaction();
        }
        db.close();
    }

    public boolean favorito(Disco disco) {
        boolean existe;
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT " + DiscoContract.COL_DISCO_ID +
                        " FROM " + DiscoContract.TABLE_DISCO +
                        " WHERE " + DiscoContract.COL_TITULO + " = ?",
                new String[]{disco.titulo});
        existe = c.getCount() > 0;
        c.close();
        db.close();
        return existe;
    }

    public void excluir(Disco disco) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(DiscoContract.TABLE_DISCO, DiscoContract.COL_TITULO + "=?",
                new String[]{disco.titulo});
        db.close();
    }

    public List<Disco> getDiscos() {
        List<Disco> discos = new ArrayList<>();

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursorDiscos = null;

        cursorDiscos = db.rawQuery("SELECT * FROM " + DiscoContract.TABLE_DISCO +
                " ORDER BY " + DiscoContract.COL_ANO, null);

        while (cursorDiscos.moveToNext()) {
            Disco disco = new Disco();
            disco.titulo = cursorDiscos.getString(
                    cursorDiscos.getColumnIndex(DiscoContract.COL_TITULO));
            disco.ano = cursorDiscos.getInt(
                    cursorDiscos.getColumnIndex(DiscoContract.COL_ANO));
            disco.capa = cursorDiscos.getString(
                    cursorDiscos.getColumnIndex(DiscoContract.COL_CAPA));
            disco.capaGrande = cursorDiscos.getString(
                    cursorDiscos.getColumnIndex(DiscoContract.COL_CAPA_BIG));
            disco.gravadora = cursorDiscos.getString(
                    cursorDiscos.getColumnIndex(DiscoContract.COL_GRAVADORA));

            long id = cursorDiscos.getLong(
                    cursorDiscos.getColumnIndex(DiscoContract.COL_DISCO_ID));

            //Integrantes
            Cursor cursorFormacao = db.rawQuery("SELECT * FROM " +
                    DiscoContract.TABLE_INTEGRANTES + " WHERE "
                    + DiscoContract.COL_INTEGRANTE_DISCO_ID + " = ? ", new String[]{String.valueOf(id)});

            String[] integrantes = new String[cursorFormacao.getCount()];
            int i = 0;
            while (cursorFormacao.moveToNext()) {
                integrantes[i] = cursorFormacao.getString(
                        cursorFormacao.getColumnIndex(DiscoContract.COL_INTEGRANTE));
                i++;
            }

            disco.formacao = integrantes;
            cursorFormacao.close();

            //Musicas
            Cursor cursorMusicas = db.rawQuery("SELECT * FROM " +
                    DiscoContract.TABLE_MUSICAS + " WHERE "
                    + DiscoContract.COL_MUSICA_DISCO_ID + "= ? " +
                    "ORDER BY " + DiscoContract.COL_MUSICA_NUM, new String[]{String.valueOf(id)});

            String[] musicas = new String[cursorMusicas.getCount()];
            i = 0;
            while (cursorMusicas.moveToNext()) {
                musicas[i] = cursorMusicas.getString(
                        cursorMusicas.getColumnIndex(DiscoContract.COL_MUSICA));
                i++;
            }

            disco.faixas = musicas;
            cursorMusicas.close();
            discos.add(disco);
        }

        cursorDiscos.close();
        db.close();

        return discos;
    }
}
