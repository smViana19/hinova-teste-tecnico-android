package br.com.smvtech.testetecnico.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.smvtech.testetecnico.data.local.database.model.toDomain
import br.com.smvtech.testetecnico.data.local.repository.WorkshopRepository
import br.com.smvtech.testetecnico.domain.model.workshop.Workshop
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkshopDetailsViewmodel @Inject constructor(
    private val repository: WorkshopRepository
): ViewModel() {

    private val _workshop = mutableStateOf<Workshop?>(null)
    val workshop: MutableState<Workshop?> = _workshop

    fun getWorkshopById(workshopId: Int){
        viewModelScope.launch {
            try {
                val workshop = repository.findWorkshopById(workshopId)
                _workshop.value = workshop.toDomain()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}