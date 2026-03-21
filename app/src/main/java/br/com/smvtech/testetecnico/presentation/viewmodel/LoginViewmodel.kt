package br.com.smvtech.testetecnico.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import br.com.smvtech.testetecnico.core.utils.ValidatorUtils
import br.com.smvtech.testetecnico.data.local.sharedpreferences.SharedPrefsService
import br.com.smvtech.testetecnico.data.mocks.UserMock
import br.com.smvtech.testetecnico.presentation.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(
    private val sharedPrefsService: SharedPrefsService
) : ViewModel() {
    private val _document = mutableStateOf("")
    val document: MutableState<String> = _document

    private val _password = mutableStateOf("")
    val password: MutableState<String> = _password

    private val _passwordError = mutableStateOf("")
    val passwordError: MutableState<String> = _passwordError

    private val _documentError = mutableStateOf("")
    val documentError: MutableState<String> = _documentError

    private val _isLoading = mutableStateOf(false)
    val isLoading: MutableState<Boolean> = _isLoading

    fun login(navController: NavController) {
        viewModelScope.launch {
            _documentError.value = ""
            _passwordError.value = ""
            _isLoading.value = true

            if (_document.value.isBlank()) {
                _documentError.value = "CPF é obrigatório"
                _isLoading.value = false
                _document.value = ""
                _password.value = ""
                return@launch
            }

            if (!ValidatorUtils().isValidCpf(_document.value)) {
                _documentError.value = "CPF Inválido"
                _isLoading.value = false
                return@launch
            }

            if (_password.value.isBlank()) {
                _passwordError.value = "Senha é obrigatória"
                _isLoading.value = false
                return@launch
            }

            if (_password.value.length < 8) {
                _passwordError.value = "A senha deve conter no mínimo 8 caracteres"
                _isLoading.value = false
                _password.value = ""
                return@launch
            }

            val userMock = UserMock(
                id = "3555",
                nome = "Samuel Filipe",
                codigoMobile = "555",
                cpf = "364.669.620-67",
                email = "samuelfilipe@testehinova.com.br",
                situacao = "ATIVO",
                telefone = "31-9999-5555"
            )
            sharedPrefsService.saveUserData(userMock)
            _isLoading.value = false
            navController.navigate(Screens.HOME_SCREEN.name)

        }
    }
}