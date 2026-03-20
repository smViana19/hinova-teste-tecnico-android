package br.com.smvtech.testetecnico.domain.model.referral

import com.google.gson.annotations.SerializedName

data class Referral(
    @SerializedName("CodigoAssociacao")
    val associateCode: String,
    @SerializedName("DataCriacao")
    val createdDate: String,
    @SerializedName("CpfAssociado")
    val associateDocument: String,
    @SerializedName("EmailAssociado")
    val associateEmail: String,
    @SerializedName("NomeAssociado")
    val associateName: String,
    @SerializedName("TelefoneAssociado")
    val associatePhone: String,
    @SerializedName("PlacaVeiculoAssociado")
    val associateVehiclePlate: String,
    @SerializedName("NomeAmigo")
    val friendName: String,
    @SerializedName("TelefoneAmigo")
    val friendPhone: String,
    @SerializedName("EmailAmigo")
    val friendEmail: String,
)
