package com.example.app_diabetes

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {

    companion object {
        const val DATABASE_NAME = "miBaseDeDatos.db"
        const val DATABASE_VERSION = 2
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createUsuariosTable =   """
        CREATE TABLE usuarios (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre_usuario TEXT,
            email TEXT,
            password TEXT,
            tipo_usuario TEXT
        )
        """

        val createAccionesTable = """
            CREATE TABLE acciones (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre_accion TEXT,
                descripcion TEXT,
                categoria TEXT
            )
        """

        val createRegistrosTable = """
            CREATE TABLE registros (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuario_id INTEGER,
                nivel FLOAT,
                insulina FLOAT,
                fecha TEXT,
                hora TEXT,
                nota_libre TEXT,
                FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
            )
        """

        db?.execSQL(createUsuariosTable)
        db?.execSQL(createAccionesTable)
        db?.execSQL(createRegistrosTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Todo versiones BBDD
        db?.execSQL("DROP TABLE IF EXISTS usuarios")
        db?.execSQL("DROP TABLE IF EXISTS acciones")
        db?.execSQL("DROP TABLE IF EXISTS registros")
        onCreate(db)
    }
}
