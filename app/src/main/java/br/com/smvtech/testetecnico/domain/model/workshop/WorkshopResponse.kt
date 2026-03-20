package br.com.smvtech.testetecnico.domain.model.workshop

import com.google.gson.annotations.SerializedName

data class WorkshopResponse(
    @SerializedName("ListaOficinas")
    val workshops: List<Workshop>,
    @SerializedName("RetornoErro")
    val error: Error?,
    @SerializedName("Token")
    val token: String?
)

data class Error(
    @SerializedName("retornoErro")
    val errorReturn: String?,
)
