package dev.muthuram.roomdatabase.presentation

import androidx.lifecycle.ViewModel
import dev.muthuram.roomdatabase.domain.model.User
import dev.muthuram.roomdatabase.domain.usecase.GetUserUseCase
import dev.muthuram.roomdatabase.domain.usecase.UpdateUserUseCase
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : ViewModel(), KoinComponent {

    private val getUserUseCase: GetUserUseCase by inject()
    private val updateUserUseCase: UpdateUserUseCase by inject()
    private var _emailId = MutableStateFlow("")
    val emailId : StateFlow<String> =  _emailId

    private val scope = MainScope()

    fun getEmail() {
        scope.launch {
            getUserUseCase.invoke().collectLatest {
                _emailId.value = it.lastOrNull()?.emailId ?: ""
            }
        }
    }

    fun onEmailIdChange(emailId: String) {
        _emailId.value = emailId
    }

    fun onSaveUser() {
        scope.launch {
            updateUserUseCase.invoke(User(emailId = emailId.value))
        }
    }
}