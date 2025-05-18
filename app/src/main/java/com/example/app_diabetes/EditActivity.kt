package com.example.app_diabetes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nuevodato)
        // aquí lo que voy a incluir son los campos de edición para cada dato
        // TODO obtener los valores y guardarlos en la BBDD
    }
}