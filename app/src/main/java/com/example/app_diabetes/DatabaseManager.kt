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
    fun insertRegistro(usuarioId: Int, nivel: Float, insulina: Float, fecha: String, hora: String, notaLibre: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("usuario_id", usuarioId)
            put("nivel", nivel)
            put("insulina", insulina)
            put("fecha", fecha)
            put("hora", hora)
            put("nota_libre", notaLibre)
        }

        db.insert("registros", null, values)
        db.close()
    }

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
    fun getRegistrosByUsuarioId(usuarioId: Int): List<Registro> {
        val registrosList = mutableListOf<Registro>()
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM registros WHERE usuario_id = ?", arrayOf(usuarioId.toString()))

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val nivel = cursor.getFloat(cursor.getColumnIndex("nivel"))
            val insulina = cursor.getFloat(cursor.getColumnIndex("insulina"))
            val fecha = cursor.getString(cursor.getColumnIndex("fecha"))
            val hora = cursor.getString(cursor.getColumnIndex("hora"))
            val nota = cursor.getString(cursor.getColumnIndex("nota_libre"))

            val registro = Registro(id, nivel, insulina, fecha, hora, nota)
            registrosList.add(registro)
        }

        cursor.close()
        db.close()
        return registrosList
    }
@SuppressLint("Range")
fun getRegistroById(registroId: Int): Registro? {
    val db = dbHelper.readableDatabase
    val cursor = db.rawQuery("SELECT * FROM registros WHERE id = ?", arrayOf(registroId.toString()))

    var registro: Registro? = null
    if (cursor.moveToFirst()) {
        val id = cursor.getInt(cursor.getColumnIndex("id"))
        val nivel = cursor.getFloat(cursor.getColumnIndex("nivel"))
        val insulina = cursor.getFloat(cursor.getColumnIndex("insulina"))
        val fecha = cursor.getString(cursor.getColumnIndex("fecha"))
        val hora = cursor.getString(cursor.getColumnIndex("hora"))
        val nota = cursor.getString(cursor.getColumnIndex("nota_libre"))

        registro = Registro(id, nivel, insulina, fecha, hora, nota)
    }
    cursor.close()
    db.close()
    return registro
}
    fun updateRegistro(id: Int, nivel: Float, insulina: Float, fecha: String, hora: String, notaLibre: String): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nivel", nivel)
            put("insulina", insulina)
            put("fecha", fecha)
            put("hora", hora)
            put("nota_libre", notaLibre)
        }
        val rowsAffected = db.update("registros", values, "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsAffected > 0
    }

    fun deleteRegistro(id: Int): Boolean {
        val db = dbHelper.writableDatabase
        val rowsDeleted = db.delete("registros", "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted > 0
    }

    // Aquí eliminar todos los registros de un usuario
    fun deleteAllRegistrosByUsuarioId(usuarioId: Int): Boolean {
        val db = dbHelper.writableDatabase
        val rowsDeleted = db.delete("registros", "usuario_id = ?", arrayOf(usuarioId.toString()))
        db.close()
        return rowsDeleted > 0
    }

    // para eliminar completamente un usuario (y sus registros primero)
    // NOTA: al final no lo utilizo porque no tiene sentido ya que con borrar la aplicación es suficiente
    fun deleteUsuarioById(usuarioId: Int): Boolean {
        val db = dbHelper.writableDatabase
        db.delete("registros", "usuario_id = ?", arrayOf(usuarioId.toString()))
        val rowsDeleted = db.delete("usuarios", "id = ?", arrayOf(usuarioId.toString()))
        db.close()
        return rowsDeleted > 0
    }
}
