package com.example.app_diabetes

import com.example.app_diabetes.EditActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
class HomeActivity : AppCompatActivity() {
    private lateinit var dbManager: DatabaseManager

    private lateinit var tableLayout: TableLayout
    private lateinit var btnEdit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        dbManager = DatabaseManager(this)
//        val usuarioslog = dbManager.getAllUsuarios()
        //log de prueba
//        for (usuario in usuarioslog) {
//            Log.d("Usuarios", usuario.nombre)
//        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        tableLayout = findViewById(R.id.tableLayout)
        btnEdit = findViewById(R.id.btnEdit)

        // Datos de ejemplo para probar
//        val usuarios = listOf(
//            Usuario(1, 101, 75.0f, "2025-05-04", "08:30", "Nota 1"),
//            Usuario(2, 102, 80.0f, "2025-05-05", "09:00", "Nota 2")
//        )
//
//        // para llenar la tabla uso un for
//        for (usuario in usuarios) {
//            val row = TableRow(this)
//
//            val tvId = TextView(this).apply { text = usuario.id.toString() }
//            val tvUsuarioId = TextView(this).apply { text = usuario.usuarioId.toString() }
//            val tvNivel = TextView(this).apply { text = usuario.nivel.toString() }
//            val tvFecha = TextView(this).apply { text = usuario.fecha }
//            val tvHora = TextView(this).apply { text = usuario.hora }
//            val tvNota = TextView(this).apply { text = usuario.notaLibre }
//
//            row.apply {
//                addView(tvId)
//                addView(tvUsuarioId)
//                addView(tvNivel)
//                addView(tvFecha)
//                addView(tvHora)
//                addView(tvNota)
//            }
//
//            tableLayout.addView(row)
//        }

        // Configurar el botón de editar
//        btnEdit.setOnClickListener {
//            // Aquí puedes poner el código para ir a otra actividad donde puedas editar los datos
//            val intent = Intent(this, com.example.app_diabetes.EditActivity::class.java)
//            startActivity(intent)
//        }
    }
}