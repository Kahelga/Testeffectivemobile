package com.example.testEffectiveMobile.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testEffectiveMobile.shared.user.auth.domain.usecase.AuthUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class AuthViewModel(
    private val authUseCase: AuthUseCase,
) : ViewModel() {

   private val _state = MutableStateFlow<AuthState>(AuthState.Initial)
    val state: StateFlow<AuthState> = _state


    fun authorize(login: String, password: String) {
        viewModelScope.launch {
            try {
                val response = authUseCase(login, password)
                _state.value = AuthState.Success(response)
            } catch (ce: CancellationException) {
                throw ce
            } catch (e: Exception) {
                _state.value = AuthState.Failure(e.localizedMessage.orEmpty())
                delay(1000)
                _state.value = AuthState.Initial
            }
        }
    }
}