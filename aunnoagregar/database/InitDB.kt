package com.example.Zapasy.database

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.Zapasy.ZapazyApp

class InitDB: SQLiteOpenHelper(ZapazyApp.CONTEXT, ZapazyApp.DB_NAME, null, ZapazyApp.DB_VERSION) {

    val QUERY_CREAR_ESQUEMA = "CREATE TABLE producto(" +
            "    id_producto INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    nombre TEXT NOT NULL," +
            "    barcode TEXT NOT NULL," +
            "    precio REAL DEFAULT 0.0," +
            "    existencia INTEGER DEFAULT 0," +
            "    vendido INTEGER DEFAULT 0," +
            "    perdido INTEGER DEFAULT 0," +
            "    daniado INTEGER DEFAULT 0," +
            "    UNIQUE(barcode)" +
            ");" +
            "" +
            "CREATE TABLE grupo(" +
            "    id_grupo INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    nombre TEXT NOT NULL" +
            ");" +
            "" +
            "CREATE TABLE categoria(" +
            "    id_categoria INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    nombre TEXT NOT NULL," +
            "    id_grupo INTEGER," +
            "    FOREIGN KEY (id_grupo)" +
            "       REFERENCES grupo (id_grupo)" +
            "        ON UPDATE CASCADE" +
            "        ON DELETE SET NULL" +
            ");" +
            "" +
            "CREATE TABLE productoxgrupo(" +
            "    id_producto INTEGER," +
            "    id_categoria INTEGER," +
            "    PRIMARY KEY (id_producto,id_categoria)" +
            "    FOREIGN KEY (id_producto)" +
            "       REFERENCES producto (id_producto)" +
            "        ON UPDATE CASCADE" +
            "        ON DELETE RESTRICT," +
            "    FOREIGN KEY (id_categoria)" +
            "       REFERENCES categoria (id_categoria)" +
            "        ON UPDATE CASCADE" +
            "        ON DELETE RESTRICT" +
            ");"

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(QUERY_CREAR_ESQUEMA)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}