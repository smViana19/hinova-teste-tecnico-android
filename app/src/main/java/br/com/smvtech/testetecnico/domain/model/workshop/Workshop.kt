package br.com.smvtech.testetecnico.domain.model.workshop

import com.google.gson.annotations.SerializedName

data class Workshop(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Nome")
    val name: String,
    @SerializedName("Descricao")
    val description: String,
    @SerializedName("DescricaoCurta")
    val shortDescription: String,
    @SerializedName("Endereco")
    val address: String,
    @SerializedName("Latitude")
    val latitude: String,
    @SerializedName("Longitude")
    val longitude: String,
    @SerializedName("Foto")
    val photo: String,
    @SerializedName("AvaliacaoUsuario")
    val userRating: Double,
    @SerializedName("CodigoAssociacao")
    val associateCode: Int,
    @SerializedName("Email")
    val email: String,
    @SerializedName("Telefone1")
    val phone: String?,
    @SerializedName("Telefone2")
    val secondPhone: String?,
    @SerializedName("Ativo")
    val active: Boolean,
)