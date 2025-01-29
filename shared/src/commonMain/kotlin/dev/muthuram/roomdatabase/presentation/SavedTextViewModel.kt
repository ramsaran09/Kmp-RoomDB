package dev.muthuram.roomdatabase.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dev.muthuram.roomdatabase.data.db.UserDao
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SavedTextViewModel(
    private val userDao: UserDao,
) : ViewModel() {

    private var _emailId = mutableStateOf("")
    val emailId get() =  _emailId

    private val scope = MainScope()

    fun getEmail() {
        scope.launch {
            userDao.getAllPeople().collectLatest {
                _emailId.value = it.lastOrNull()?.emailId ?: ""
            }
        }
    }
}