package dev.muthuram.roomdatabase.domain.repository

import dev.muthuram.roomdatabase.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun upsert(user: User)
    fun getAllPeople(): Flow<List<User>>

    suspend fun saveUser(user: User)
}