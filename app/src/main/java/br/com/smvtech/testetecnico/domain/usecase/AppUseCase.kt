package br.com.smvtech.testetecnico.domain.usecase

import br.com.smvtech.testetecnico.domain.model.referral.ReferralRequest
import br.com.smvtech.testetecnico.domain.model.referral.ReferralResponse
import br.com.smvtech.testetecnico.domain.model.workshop.WorkshopResponse
import br.com.smvtech.testetecnico.domain.repository.AppRepository
import javax.inject.Inject

class AppUseCase @Inject constructor(
    private val appRepository: AppRepository
) {

    suspend fun getWorkshops(associateCode: Int, associateDocument: String): WorkshopResponse {
        return appRepository.getWorkshops(associateCode, associateDocument)
    }

    suspend fun sendReferral(referral: ReferralRequest): ReferralResponse {
        return appRepository.sendReferral(referral)
    }
}