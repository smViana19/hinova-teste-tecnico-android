package br.com.smvtech.testetecnico.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smvtech.testetecnico.core.utils.ValidatorUtils
import br.com.smvtech.testetecnico.data.local.database.model.toDomain
import br.com.smvtech.testetecnico.data.local.database.model.toEntity
import br.com.smvtech.testetecnico.data.local.repository.WorkshopRepository
import br.com.smvtech.testetecnico.domain.location.LocationProvider
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
    private val appUseCase: AppUseCase,
    private val locationProvider: LocationProvider,
    private val workshopRepository: WorkshopRepository,
) : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: MutableState<Boolean> = _isLoading

    private val _searchWorkshop = mutableStateOf("")
    val searchWorkshop: MutableState<String> = _searchWorkshop

    private val _showActiveOnly = mutableStateOf(false)
    val showActiveOnly: MutableState<Boolean> = _showActiveOnly

    private val _workshops = mutableStateOf<List<Workshop>>(emptyList())
    val workshops: MutableState<List<Workshop>> = _workshops

    private val _filteredWorkshops = mutableStateOf<List<Workshop>>(emptyList())
    val filteredWorkshops: MutableState<List<Workshop>> = _filteredWorkshops

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: MutableState<String?> = _errorMessage

    private val _successMessage = mutableStateOf<String?>(null)
    val successMessage: MutableState<String?> = _successMessage

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

    private val _latitude = mutableStateOf("0.0")
    val latitude: MutableState<String> = _latitude

    private val _longitude = mutableStateOf("0.0")
    val longitude: MutableState<String> = _longitude

    private val _referralErrorMessage = mutableStateOf<String?>(null)
    val referralErrorMessage: MutableState<String?> = _referralErrorMessage

    private val _referralSuccessMessage = mutableStateOf<String?>(null)
    val referralSuccessMessage: MutableState<String?> = _referralSuccessMessage

    private val _associateNameError = mutableStateOf("")
    val associateNameError: MutableState<String> = _associateNameError

    private val _associateDocumentError = mutableStateOf("")
    val associateDocumentError: MutableState<String> = _associateDocumentError

    private val _associateEmailError = mutableStateOf("")
    val associateEmailError: MutableState<String> = _associateEmailError

    private val _associatePhoneError = mutableStateOf("")
    val associatePhoneError: MutableState<String> = _associatePhoneError

    private val _associateVehiclePlateError = mutableStateOf("")
    val associateVehiclePlateError: MutableState<String> = _associateVehiclePlateError

    private val _friendNameError = mutableStateOf("")
    val friendNameError: MutableState<String> = _friendNameError

    private val _friendPhoneError = mutableStateOf("")
    val friendPhoneError: MutableState<String> = _friendPhoneError

    private val _friendEmailError = mutableStateOf("")
    val friendEmailError: MutableState<String> = _friendEmailError

    fun updateSearchWorkshop(value: String) {
        _searchWorkshop.value = value
        applyFilters()
    }

    fun setShowActiveOnly(value: Boolean) {
        _showActiveOnly.value = value
        applyFilters()
    }

    fun getAllWorkshops() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val localWorkshops = workshopRepository.findAllWorkshops()
                if (localWorkshops.isNotEmpty()) {
                    _workshops.value = localWorkshops.map { it.toDomain() }
                    applyFilters()
                } else {
                    fetchAndSaveWorkshops()
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao carregar os dados locais: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getUserLocation() {
        viewModelScope.launch {
            val location = locationProvider.getCurrentLocation()
            location?.let {
                _latitude.value = it.latitude.toString()
                Log.d("LOCALIZACAO", "Latitude: ${it.latitude}")
                Log.d("LOCALIZACAO", "Longitude: ${it.longitude}")
                _longitude.value = it.longitude.toString()
            }
        }

    }

    fun sendReferral() {
        viewModelScope.launch {
            _isLoading.value = true
            _referralErrorMessage.value = null
            _referralSuccessMessage.value = null
            clearReferralErrors()

            if (!validateReferral()) {
                _isLoading.value = false
                return@launch
            }

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
                    _referralSuccessMessage.value = response.success
                        ?: "Indicação enviada com sucesso!"
                    clearForm()
                } else {
                    _referralErrorMessage.value = response.error.message
                }
            } catch (e: Exception) {
                _referralErrorMessage.value = "Falha ao enviar: ${e.message}"
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
        clearReferralErrors()
    }

    private fun clearReferralErrors() {
        _associateNameError.value = ""
        _associateDocumentError.value = ""
        _associateEmailError.value = ""
        _associatePhoneError.value = ""
        _associateVehiclePlateError.value = ""
        _friendNameError.value = ""
        _friendPhoneError.value = ""
        _friendEmailError.value = ""
    }

    private fun validateReferral(): Boolean {
        var isValid = true

        if (_associateName.value.isBlank()) {
            _associateNameError.value = "Nome é obrigatório"
            isValid = false
        }

        if (_associateDocument.value.isBlank()) {
            _associateDocumentError.value = "CPF é obrigatório"
            isValid = false
        } else if (!ValidatorUtils().isValidCpf(_associateDocument.value)) {
            _associateDocumentError.value = "CPF inválido"
            isValid = false
        }

        if (_associateEmail.value.isBlank()) {
            _associateEmailError.value = "E-mail é obrigatório"
            isValid = false
        }

        if (_associatePhone.value.isBlank()) {
            _associatePhoneError.value = "Telefone é obrigatório"
            isValid = false
        }

        if (_associateVehiclePlate.value.isBlank()) {
            _associateVehiclePlateError.value = "Placa é obrigatória"
            isValid = false
        }

        if (_friendName.value.isBlank()) {
            _friendNameError.value = "Nome é obrigatório"
            isValid = false
        }

        if (_friendPhone.value.isBlank()) {
            _friendPhoneError.value = "Telefone é obrigatório"
            isValid = false
        }

        if (_friendEmail.value.isBlank()) {
            _friendEmailError.value = "E-mail é obrigatório"
            isValid = false
        }

        return isValid
    }

    private suspend fun fetchAndSaveWorkshops() {
        try {
            val response =
                appUseCase.getWorkshops(associateCode = 601, associateDocument = "12345678900")
            if (response.error?.errorReturn == null) {
                val workshopsMapped = response.workshops.map { it.toEntity() }

                workshopRepository.bulkInsertWorkshops(workshops = workshopsMapped)
                val localWorkshops = workshopRepository.findAllWorkshops()
                _workshops.value = localWorkshops.map { it.toDomain() }
                applyFilters()
                Log.d("DEBUG", "Oficinas carregadas - VIEWMODEL ${response.workshops.size}")
            } else {
                _errorMessage.value = response.error.errorReturn ?: "Erro desconhecido"
            }
        } catch (e: Exception) {
            _errorMessage.value = "Erro na comunicação com o servidor: ${e.message}"
            Log.e("DEBUG", "Erro fetchAndSaveWorkshops", e)
        }
    }

    private fun applyFilters() {
        val query = _searchWorkshop.value.trim().lowercase(Locale.getDefault())
        val filtered = _workshops.value.filter { workshop ->
            val matchesActive = !_showActiveOnly.value || workshop.active
            val matchesQuery = query.isEmpty() ||
                workshop.name.lowercase(Locale.getDefault()).contains(query) ||
                workshop.address.lowercase(Locale.getDefault()).contains(query) ||
                workshop.shortDescription.lowercase(Locale.getDefault()).contains(query)

            matchesActive && matchesQuery
        }
        _filteredWorkshops.value = filtered
    }

}
