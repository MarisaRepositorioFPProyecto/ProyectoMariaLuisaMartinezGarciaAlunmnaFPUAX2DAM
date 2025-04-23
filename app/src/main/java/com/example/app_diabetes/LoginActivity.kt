package com.example.diabetesapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app_diabetes.R
import com.example.app_diabetes.data.AppDatabase
import com.example.app_diabetes.data.repositories.UserRepository
import com.example.app_diabetes.ui.login.LoginViewModel

class LoginActivity: AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val dao = AppDatabase.getInstance(this).userDao()
        val repo = UserRepository(dao)
        viewModel = LoginViewModel(repo)
        // Meto logs para ver que falla
        viewModel.showAllUsers { msg ->
            Log.d("DB_CHECK", msg)
        }

        val userEt = findViewById<EditText>(R.id.usernameEditText)
        val passEt = findViewById<EditText>(R.id.passwordEditText)
        val loginBtn = findViewById<Button>(R.id.loginButton)
        val createPass = findViewById<TextView>(R.id.createPass)

        // Aqui se crea cuando le doy a "crear contraseña de entrada" de momento asi hasta que haga la pantalla de registro
        createPass.setOnClickListener {
            val u = userEt.text.toString()
            val p = passEt.text.toString()
            viewModel.register(u, p) { ok ->
                Toast.makeText(this,
                    if (ok) "Genial te has registrado correctamente" else "Ohhh lo siento este usuario ya existe",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Aquí el login
        loginBtn.setOnClickListener {
            val u = userEt.text.toString()
            val p = passEt.text.toString()
            viewModel.login(u, p) { success ->
                if (success) {
                    Toast.makeText(this, "¡Holaa $u!", Toast.LENGTH_SHORT).show()
                    // startActivity(Intent(this, HomeActivity::class.java))
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Ups lo siento ¿Segurp que no eres un robot?", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

