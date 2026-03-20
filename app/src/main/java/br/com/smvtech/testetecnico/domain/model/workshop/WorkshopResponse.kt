package br.com.smvtech.testetecnico.domain.model.workshop

data class WorkshopResponse(
    val workshops: List<Workshop>,
    val error: Error?,
    val token: String?
)

data class Error(
    val errorReturn: String?,
)
