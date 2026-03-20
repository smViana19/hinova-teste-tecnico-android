package br.com.smvtech.testetecnico.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(): ViewModel() {
    private val _document = mutableStateOf("")
    val document: MutableState<String> = _document

    private val _password = mutableStateOf("")
    val password: MutableState<String> = _password
}