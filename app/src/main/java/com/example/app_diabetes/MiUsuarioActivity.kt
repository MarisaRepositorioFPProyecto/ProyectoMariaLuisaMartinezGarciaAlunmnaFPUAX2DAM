package com.example.app_diabetes


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MiUsuarioActivity : AppCompatActivity() {
    private lateinit var dbManager: DatabaseManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usuario)

    }
    fun volvarAtras (view: View) {
        setContentView(R.layout.home)
    }
    fun btnEliminarRegistros(view: View) {
        val dbManager = DatabaseManager(this)
        val usuarioId = getSharedPreferences("APP_PREFS", MODE_PRIVATE).getInt("usuario_id", -1)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Borrar todos mis datos")
            .setMessage("¿Estás seguro de que quieres borrar todos tus registros? Esta acción no se puede deshacer.")
            .setPositiveButton("Sí, borrar") { _, _ ->
                val success = dbManager.deleteAllRegistrosByUsuarioId(usuarioId)
                if (success) {
                    Toast.makeText(this, "Todos los registros han sido eliminados", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "No se pudo borrar", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

    }

}
