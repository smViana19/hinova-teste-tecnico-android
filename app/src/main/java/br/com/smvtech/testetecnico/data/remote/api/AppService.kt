package br.com.smvtech.testetecnico.data.remote.api

import br.com.smvtech.testetecnico.domain.model.referral.ReferralRequest
import br.com.smvtech.testetecnico.domain.model.referral.ReferralResponse
import br.com.smvtech.testetecnico.domain.model.workshop.WorkshopResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AppService {
    @GET("/api/oficina")
    suspend fun getWorkshops(
        @Query("codigoAssociacao") associateCode: Int = 601,
        @Query("cpfAssociado") associateDocument: String
    ): Response<WorkshopResponse>

    @POST("/api/indicacao")
    suspend fun sendReferral(@Body referral: ReferralRequest): Response<ReferralResponse>
}


