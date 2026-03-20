package br.com.smvtech.testetecnico.domain.repository

import br.com.smvtech.testetecnico.domain.model.referral.ReferralRequest
import br.com.smvtech.testetecnico.domain.model.referral.ReferralResponse
import br.com.smvtech.testetecnico.domain.model.workshop.WorkshopResponse

interface AppRepository {
    suspend fun getWorkshops(associateCode: Int, associateDocument: String?): WorkshopResponse
    suspend fun sendReferral(referral: ReferralRequest): ReferralResponse

}