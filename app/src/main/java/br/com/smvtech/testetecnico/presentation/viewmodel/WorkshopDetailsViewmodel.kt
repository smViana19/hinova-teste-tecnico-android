package br.com.smvtech.testetecnico.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class WorkshopDetailsViewmodel @Inject constructor(

): ViewModel() {

    fun getWorkshopById(){
        viewModelScope.launch {

        }
    }
}