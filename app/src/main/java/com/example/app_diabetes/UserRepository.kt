package com.example.app_diabetes.data.repositories

import com.example.app_diabetes.data.dao.UserDao
import com.example.app_diabetes.data.entities.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val dao: UserDao) {
    suspend fun insert(user: User) = dao.insert(user)
    // TODO Mirar las importaciones que parece que es el problema
    suspend fun getUser(username: String): User? = dao.getUser(username)
    suspend fun getAll(): List<User> = dao.getAll()

}
