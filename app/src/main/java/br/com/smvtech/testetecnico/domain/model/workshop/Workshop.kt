package br.com.smvtech.testetecnico.domain.model.workshop

data class Workshop(
    val id: Int,
    val name: String,
    val description: String,
    val shortDescription: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val photo: String,
    val userRating: Double,
    val associateCode: Int,
    val email: String,
    val phone: String?,
    val secondPhone: String?,
    val active: Boolean,
)