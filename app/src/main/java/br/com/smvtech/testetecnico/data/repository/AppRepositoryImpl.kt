package br.com.smvtech.testetecnico.data.repository

import br.com.smvtech.testetecnico.data.remote.api.AppService
import br.com.smvtech.testetecnico.domain.model.referral.ReferralRequest
import br.com.smvtech.testetecnico.domain.model.referral.ReferralResponse
import br.com.smvtech.testetecnico.domain.model.workshop.WorkshopResponse
import br.com.smvtech.testetecnico.domain.repository.AppRepository

class AppRepositoryImpl(
    private val appService: AppService
) : AppRepository {

    override suspend fun getWorkshops(
        associateCode: Int,
        associateDocument: String?
    ): WorkshopResponse {
        return try {
            val response = appService.getWorkshops(associateCode, associateDocument)
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Resposta vazia do servidor")
            } else {
                throw Exception("Erro ao buscar oficinas: ${response.code()}")
            }
        } catch (ex: Exception) {
            throw Exception("Falha ao buscar oficinas: ${ex.message}")
        }
    }

    override suspend fun sendReferral(referral: ReferralRequest): ReferralResponse {
        return try {
            val response = appService.sendReferral(referral)
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Resposta vazia do servidor")
            } else {
                throw Exception("Erro ao buscar oficinas: ${response.code()}")
            }
        } catch (ex: Exception) {
            throw Exception("Falha ao buscar oficinas: ${ex.message}")
        }
    }
}