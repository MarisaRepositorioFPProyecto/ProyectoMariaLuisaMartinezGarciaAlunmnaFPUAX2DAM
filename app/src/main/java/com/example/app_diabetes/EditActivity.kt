package com.example.app_diabetes

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app_diabetes.Frases
class EditActivity : AppCompatActivity() {
    private lateinit var dbManager: DatabaseManager
    // aqui edito cada fila de la tabla de datos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nuevodato)
        // aquí lo que voy a incluir son los campos de edición para cada dato
        val registroId = intent.getIntExtra("registro_id", -1)
        if (registroId != -1) {
            val registro = dbManager.getRegistrosByUsuarioId(registroId)
        }
        val fraseAleatoria = Frases.frases.random()
        findViewById<TextView>(R.id.subtitle).text = fraseAleatoria
    }
    fun crearNuevoDato(view: View) {
        val fecha = findViewById<EditText>(R.id.fechaEditText).text.toString()
        val hora = findViewById<EditText>(R.id.horaEditText).text.toString()
        val nivel = findViewById<EditText>(R.id.glucosaEditText).text.toString().toFloatOrNull() ?: 0f
        val insulina = findViewById<EditText>(R.id.insulinaEditText).text.toString().toFloatOrNull() ?: 0f
        val notas = findViewById<EditText>(R.id.notasEditText).text.toString()

        val usuarioId = getSharedPreferences("APP_PREFS", MODE_PRIVATE).getInt("usuario_id", -1)
        if (usuarioId == -1) {
            Toast.makeText(this, "Usuario no identificado", Toast.LENGTH_SHORT).show()
            return
        }

        val db = DatabaseManager(this)
        db.insertRegistro(usuarioId, nivel, insulina, fecha, hora, "$notas\nInsulina: $insulina")

        Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show()
        // me aseguro que vuelve a la anterior página
        finish()
    }

}