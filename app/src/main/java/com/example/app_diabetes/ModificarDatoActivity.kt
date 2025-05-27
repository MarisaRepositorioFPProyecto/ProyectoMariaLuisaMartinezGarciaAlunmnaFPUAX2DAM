package com.example.app_diabetes

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ModificarDatoActivity : AppCompatActivity() {

    private lateinit var dbManager: DatabaseManager

    private lateinit var fechaEditText: EditText
    private lateinit var horaEditText: EditText
    private lateinit var glucosaEditText: EditText
    private lateinit var insulinaEditText: EditText
    private lateinit var notasEditText: EditText

    private var registroId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editar)
        val fraseAleatoria = Frases.frases.random()
        findViewById<TextView>(R.id.subtitle).text = fraseAleatoria
        fechaEditText = findViewById(R.id.fechaEditText)
        horaEditText = findViewById(R.id.horaEditText)
        glucosaEditText = findViewById(R.id.glucosaEditText)
        insulinaEditText = findViewById(R.id.insulinaEditText)
        notasEditText = findViewById(R.id.notasEditText)

        Log.d("aqui notasEditText", notasEditText.toString())

        dbManager = DatabaseManager(this)

        registroId = intent.getIntExtra("registro_id", -1)

        if (registroId != -1) {
            cargarDatosRegistro(registroId)
        }
    }


    private fun cargarDatosRegistro(id: Int) {
        val registro = dbManager.getRegistroById(id)
        registro?.let {
            fechaEditText.setText(it.fecha)
            horaEditText.setText(it.hora)
            glucosaEditText.setText(it.nivel.toString())
            insulinaEditText.setText(it.insulina.toString())
            notasEditText.setText(it.notaLibre)
        }
    }

    // Aquí crearás la función para actualizar el registro
    fun guardarCambios(view: View) {
        val fecha = fechaEditText.text.toString()
        val hora = horaEditText.text.toString()
        val nivel = glucosaEditText.text.toString().toFloatOrNull() ?: 0f
        val insulina = insulinaEditText.text.toString().toFloatOrNull() ?: 0f
        val notas = notasEditText.text.toString()

        // Actualiza en la base de datos
        val exito = dbManager.updateRegistro(registroId, nivel, insulina, fecha, hora, notas)
        if (exito) {
            Toast.makeText(this, "Registro actualizado", Toast.LENGTH_SHORT).show()
            finish() // Cierra la actividad y vuelve atrás
        } else {
            Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
        }
    }

    fun eliminarDato(view: View) {
        val exito = dbManager.deleteRegistro(registroId)
        if (exito) {
            Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show()
        }
    }

    fun volverAtras(view: View) {
        finish()
    }
}
