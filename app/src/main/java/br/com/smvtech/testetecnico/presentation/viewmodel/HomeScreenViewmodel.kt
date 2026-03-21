package br.com.smvtech.testetecnico.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smvtech.testetecnico.data.local.database.model.WorkshopEntity
import br.com.smvtech.testetecnico.data.local.database.model.toDomain
import br.com.smvtech.testetecnico.data.local.database.model.toEntity
import br.com.smvtech.testetecnico.data.local.repository.WorkshopRepository
import br.com.smvtech.testetecnico.data.local.sharedpreferences.SharedPrefsService
import br.com.smvtech.testetecnico.domain.location.LocationProvider
import br.com.smvtech.testetecnico.domain.model.referral.Referral
import br.com.smvtech.testetecnico.domain.model.referral.ReferralRequest
import br.com.smvtech.testetecnico.domain.model.workshop.Workshop
import br.com.smvtech.testetecnico.domain.repository.AppRepository
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

    private val _latitude = mutableStateOf("0.0")
    val latitude: MutableState<String> = _latitude

    private val _longitude = mutableStateOf("0.0")
    val longitude: MutableState<String> = _longitude


    fun getAllWorkshops() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val localWorkshops = workshopRepository.findAllWorkshops()
                if (localWorkshops.isNotEmpty()) {
                    _workshops.value = localWorkshops.map { it.toDomain() }
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

    private suspend fun fetchAndSaveWorkshops() {
        try {
            val response =
                appUseCase.getWorkshops(associateCode = 601, associateDocument = "12345678900")
            if (response.error?.errorReturn == null) {
                val workshopsMapped = response.workshops.map { it.toEntity() }

                workshopRepository.bulkInsertWorkshops(workshops = workshopsMapped)
                val localWorkshops = workshopRepository.findAllWorkshops()
                _workshops.value = localWorkshops.map { it.toDomain() }
                Log.d("DEBUG", "Oficinas carregadas - VIEWMODEL ${response.workshops.size}")
            } else {
                _errorMessage.value = response.error.errorReturn ?: "Erro desconhecido"
            }
        } catch (e: Exception) {
            _errorMessage.value = "Erro na comunicação com o servidor: ${e.message}"
            Log.e("DEBUG", "Erro fetchAndSaveWorkshops", e)
        }
    }

}
