package com.example.app_diabetes

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NotaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notas)

        val notaTexto = intent.getStringExtra("nota") ?: "Sin nota"

        val notaTextView = findViewById<TextView>(R.id.notaTextView)
        notaTextView.text = notaTexto
    }
}
