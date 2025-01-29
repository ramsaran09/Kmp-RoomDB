package dev.muthuram.roomdatabase.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.muthuram.roomdatabase.data.db.UserDao
import dev.muthuram.roomdatabase.domain.model.User
import dev.muthuram.roomdatabase.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val dataStore: DataStore<Preferences>
) : UserRepository{

    override suspend fun upsert(user: User) {
        userDao.upsert(user)
    }

    override fun getAllPeople() = userDao.getAllPeople()

    override suspend fun saveUser(user: User) {
        dataStore.edit { dataStore ->
            val counterKey = stringPreferencesKey("emailId")
            dataStore[counterKey] = user.emailId
        }
    }


}