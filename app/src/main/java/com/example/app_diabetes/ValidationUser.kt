package com.example.app_diabetes

import android.content.Context
import android.util.Patterns
import android.widget.Toast

class ValidationUser {
    // login funcionando
    fun validarFormulario(context: Context, nombre: String, email: String, clave: String): Boolean {
        if (nombre.isEmpty() || email.isEmpty() || clave.isEmpty()) {
            Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "El email no es válido", Toast.LENGTH_SHORT).show()
            return false
        }

        val claveSegura = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$")
        if (!claveSegura.containsMatchIn(clave)) {
            Toast.makeText(
                context,
                "La clave debe tener mayúscula, minúscula, número y mínimo 6 caracteres",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        return true
    }
}