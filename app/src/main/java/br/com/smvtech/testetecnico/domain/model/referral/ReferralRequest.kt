package br.com.smvtech.testetecnico.domain.model.referral

import com.google.gson.annotations.SerializedName

data class ReferralRequest (
    @SerializedName("Indicacao")
    val referral: Referral,
    @SerializedName("Remetente")
    val sender: String,
    @SerializedName("Copias")
    val copies: List<String>
)
