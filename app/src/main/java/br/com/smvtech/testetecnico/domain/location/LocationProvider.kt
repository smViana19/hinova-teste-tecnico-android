package br.com.smvtech.testetecnico.domain.location

import android.location.Location

interface LocationProvider {
    suspend fun getCurrentLocation(): Location?
}