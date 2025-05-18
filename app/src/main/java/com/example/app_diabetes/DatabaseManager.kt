package com.example.app_diabetes

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class DatabaseManager(context: Context) {

    private val dbHelper = MyDatabaseHelper(context)

    // la función para insertar un usuario
    fun insertUsuario(nombre: String, email: String, password: String, tipoUsuario: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre_usuario", nombre)
            put("email", email)
            put("password", password)
            put("tipo_usuario", tipoUsuario)
        }

        db.insert("usuarios", null, values)
        db.close()
    }

    // Aqufunción para insertar una acción
    fun insertAccion(nombreAccion: String, descripcion: String, categoria: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre_accion", nombreAccion)
            put("descripcion", descripcion)
            put("categoria", categoria)
        }

        db.insert("acciones", null, values)
        db.close()
    }

    // función para insertar un registro
    fun insertRegistro(usuarioId: Int, nivel: Float, fecha: String, hora: String, notaLibre: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("usuario_id", usuarioId)
            put("nivel", nivel)
            put("fecha", fecha)
            put("hora", hora)
            put("nota_libre", notaLibre)
        }

        db.insert("registros", null, values)
        db.close()
    }


    // Leer todos los usuarios
//    @SuppressLint("Range")
//    fun getAllUsuarios(): List<String> {
//        val usuariosList = mutableListOf<String>()
//        val db = dbHelper.readableDatabase
//        val cursor: Cursor = db.rawQuery("SELECT * FROM usuarios", null)
//
//        while (cursor.moveToNext()) {
//            val nombre = cursor.getString(cursor.getColumnIndex("nombre_usuario"))
//            usuariosList.add(nombre)
//        }
//
//        cursor.close()
//        db.close()
//        return usuariosList
//    }
    @SuppressLint("Range")
    fun getAllUsuarios(): List<Usuario> {
        val usuariosList = mutableListOf<Usuario>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM usuarios", null)

        // aquí compruebo si el "cursor" tiene datos
        if (cursor.moveToFirst()) {
            // esto es para conseguir el índice de las columnas para luego usarlos
            val usuarioIdIndex = cursor.getColumnIndex("id")
            val nombreIndex = cursor.getColumnIndex("nombre_usuario")
            val emailIndex = cursor.getColumnIndex("email")
            val passwordIndex = cursor.getColumnIndex("password")

            // Compruebo que todos los índices anteriores están ok
            if (usuarioIdIndex != -1 && nombreIndex != -1 && emailIndex != -1 && passwordIndex != -1) {
                // hago get para traerme los datos y crear objetos de usuario
                do {
                    val id = cursor.getInt(usuarioIdIndex)
                    val nombre = cursor.getString(nombreIndex)
                    val email = cursor.getString(emailIndex)
                    val clave = cursor.getString(passwordIndex)

                    val usuario = Usuario(id, nombre, email, clave)
                    usuariosList.add(usuario)
                } while (cursor.moveToNext())
            } else {
                // Aquí mando un log si alguna columna no existe para tenerlo registrado
                Log.e("Database", "Algunas columnas no existen en la tabla usuarios")
            }
        }

        cursor.close()
        db.close()
        return usuariosList
    }

    // Con esto leo todas las acciones
    @SuppressLint("Range")
    fun getAllAcciones(): List<String> {
        val accionesList = mutableListOf<String>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM acciones", null)

        while (cursor.moveToNext()) {
            val accion = cursor.getString(cursor.getColumnIndex("nombre_accion"))
            accionesList.add(accion)
        }

        cursor.close()
        db.close()
        return accionesList
    }

    // esto lo utilizo para leer los registros por usuario
    @SuppressLint("Range")
    fun getRegistrosByUsuarioId(usuarioId: Int): List<String> {
        val registrosList = mutableListOf<String>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM registros WHERE usuario_id = ?", arrayOf(usuarioId.toString()))

        while (cursor.moveToNext()) {
            val registro = cursor.getString(cursor.getColumnIndex("nota_libre"))
            registrosList.add(registro)
        }

        cursor.close()
        db.close()
        return registrosList
    }
}
