package com.example.app_diabetes.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_diabetes.data.entities.User
import com.example.app_diabetes.data.repositories.UserRepository
import kotlinx.coroutines.launch


class LoginViewModel(private val repo: UserRepository) : ViewModel() {

    fun register(username: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                repo.insert(User(username = username, password = password))
                onResult(true)
            } catch (e: Exception) {
                // Si tengo el nombre duplicado pues lo pone a false
                // TODO ver si pongo un mensaje o algo
                onResult(false)
            }
        }
    }

    fun login(username: String, password: String, onResult: (Boolean) -> Unit) {
        // TODO creo que funcionará pero no lo tengo claro
        viewModelScope.launch {
            val user = repo.getUser(username)
            onResult(user?.password == password)
        }
    }
    //TODO aqui implementar para que pueda ver los usuarios para al menos pruebas
    fun showAllUsers(onResult: (String) -> Unit) {
        viewModelScope.launch {
            val users: List<User> = repo.getAll()
            val text = if (users.isEmpty()) {
                "No hay usuarios"
            } else {
                users.joinToString("\n") { it.username }
            }
            onResult(text)
        }
    }
}
