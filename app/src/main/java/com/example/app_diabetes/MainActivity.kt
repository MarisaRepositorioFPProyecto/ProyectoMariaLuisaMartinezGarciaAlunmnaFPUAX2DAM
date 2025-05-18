package com.example.app_diabetes

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {

    private lateinit var dbManager: DatabaseManager
    private lateinit var validator: ValidationUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        enableEdgeToEdge()
        dbManager = DatabaseManager(this)
        validator = ValidationUser()

    }
    fun crearUsuario(view: View) {
       //val intent= Intent(this,LoginActivity::class.java).apply {}
        setContentView(R.layout.newuser)


        val usuarios = dbManager.getAllUsuarios()
        for (usuario in usuarios) {
            Log.d("Usuarios", usuario.nombre)
        }

        //startActivity(intent)
    }
    @SuppressLint("MissingInflatedId")
    fun registrarUsuario (view: View) {
        val nameEditText = findViewById<EditText>(R.id.usernameEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)

        val nombre = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val clave = passwordEditText.text.toString()

        if (!validator.validarFormulario(this, nombre, email, clave)) {
            return
        }

        dbManager.insertUsuario(nombre, email, clave, "Admin")
        Toast.makeText(this, "¡Usuario registrado con éxito!", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

    }

    @SuppressLint("MissingInflatedId")
    fun loginUsuario(view: View) {
        val nameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)

        val nombre = nameEditText.text.toString().trim()
        val clave = passwordEditText.text.toString()

        if (nombre.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa tu nombre de usuario y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        // Validar si el usuario existe
        val usuarios = dbManager.getAllUsuarios()

        // Busca al usuario por el nombre de usuario (compara por 'nombre')
        val usuarioExistente = usuarios.find { it.nombre == nombre }

        if (usuarioExistente != null) {
            // Verificar la contraseña
            if (usuarioExistente.clave == clave) {
                // Si la contraseña es correcta, iniciar sesión
                Toast.makeText(this, "¡Bienvenido, $nombre!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                // Si la contraseña es incorrecta
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Si el usuario no existe
            Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_SHORT).show()
        }
    }

}




