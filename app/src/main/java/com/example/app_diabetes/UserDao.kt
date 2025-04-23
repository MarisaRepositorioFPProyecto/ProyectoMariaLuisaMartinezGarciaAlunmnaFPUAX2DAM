package com.example.app_diabetes.data.dao

import androidx.room.*
import com.example.app_diabetes.data.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    // aqui pongo los insert y las query a ver si me funciona
    // TODO Mirar las importaciones que parece que es el problema
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUser(username: String): User?

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>
}
