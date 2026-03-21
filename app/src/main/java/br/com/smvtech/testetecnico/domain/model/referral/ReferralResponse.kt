package br.com.smvtech.testetecnico.domain.model.referral

import com.google.gson.annotations.SerializedName

data class ReferralResponse(
    @SerializedName("RetornoErro")
    val error: ReferralError,
    @SerializedName("Sucesso")
    val success: String?
)

data class ReferralError(
    @SerializedName("retornoErro")
    val message: String?
)
