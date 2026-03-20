package br.com.smvtech.testetecnico.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smvtech.testetecnico.domain.model.referral.Referral
import br.com.smvtech.testetecnico.domain.model.referral.ReferralRequest
import br.com.smvtech.testetecnico.domain.model.workshop.Workshop
import br.com.smvtech.testetecnico.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewmodel @Inject constructor(
    private val appUseCase: AppUseCase
) : ViewModel() {
    private val _isLoading = mutableStateOf(false)
    val isLoading: MutableState<Boolean> = _isLoading

    private val _workshops = mutableStateOf<List<Workshop>>(emptyList())
    val workshops: MutableState<List<Workshop>> = _workshops

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: MutableState<String?> = _errorMessage

    private val _successMessage = mutableStateOf<String?>(null)
    val successMessage: MutableState<String?> = _successMessage

    // Referral Form States
    private val _associateName = mutableStateOf("")
    val associateName: MutableState<String> = _associateName

    private val _associateDocument = mutableStateOf("")
    val associateDocument: MutableState<String> = _associateDocument

    private val _associateEmail = mutableStateOf("")
    val associateEmail: MutableState<String> = _associateEmail

    private val _associatePhone = mutableStateOf("")
    val associatePhone: MutableState<String> = _associatePhone

    private val _associateVehiclePlate = mutableStateOf("")
    val associateVehiclePlate: MutableState<String> = _associateVehiclePlate

    private val _friendName = mutableStateOf("")
    val friendName: MutableState<String> = _friendName

    private val _friendPhone = mutableStateOf("")
    val friendPhone: MutableState<String> = _friendPhone

    private val _friendEmail = mutableStateOf("")
    val friendEmail: MutableState<String> = _friendEmail


    fun getAllWorkshops() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response =
                    appUseCase.getWorkshops(associateCode = 601, associateDocument = "12345678900")
                if (response.error?.errorReturn == null) {
                    _workshops.value = response.workshops
                    Log.d("DEBUG", "Oficinas carregadas - VIEWMODEL ${response.workshops.size}")
                } else {
                    _errorMessage.value = response.error.errorReturn ?: "Erro desconhecido"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Falha na conexão: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun sendReferral() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _successMessage.value = null

            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val currentDate = dateFormat.format(Date())

                val referral = Referral(
                    associateCode = "601",
                    createdDate = currentDate,
                    associateDocument = _associateDocument.value,
                    associateEmail = _associateEmail.value,
                    associateName = _associateName.value,
                    associatePhone = _associatePhone.value,
                    associateVehiclePlate = _associateVehiclePlate.value,
                    friendName = _friendName.value,
                    friendPhone = _friendPhone.value,
                    friendEmail = _friendEmail.value
                )

                val request = ReferralRequest(
                    referral = referral,
                    sender = _associateEmail.value,
                    copies = emptyList()
                )

                val response = appUseCase.sendReferral(request)

                if (response.error.message == null) {
                    _successMessage.value = response.success ?: "Indicação enviada com sucesso!"
                    clearForm()
                } else {
                    _errorMessage.value = response.error.message
                }
            } catch (e: Exception) {
                _errorMessage.value = "Falha ao enviar: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun clearForm() {
        _associateName.value = ""
        _associateDocument.value = ""
        _associateEmail.value = ""
        _associateVehiclePlate.value = ""
        _friendName.value = ""
        _friendPhone.value = ""
        _friendEmail.value = ""
    }

    fun onAssociateNameChange(value: String) {
        _associateName.value = value
    }

    fun onAssociateDocumentChange(value: String) {
        _associateDocument.value = value
    }

    fun onAssociateEmailChange(value: String) {
        _associateEmail.value = value
    }

    fun onAssociateVehiclePlateChange(value: String) {
        _associateVehiclePlate.value = value
    }

    fun onFriendNameChange(value: String) {
        _friendName.value = value
    }

    fun onFriendPhoneChange(value: String) {
        _friendPhone.value = value
    }

    fun onFriendEmailChange(value: String) {
        _friendEmail.value = value
    }

}
