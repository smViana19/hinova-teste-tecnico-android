package br.com.smvtech.testetecnico.data.local.repository

import br.com.smvtech.testetecnico.data.local.database.dao.WorkshopDao
import br.com.smvtech.testetecnico.data.local.database.model.WorkshopEntity
import javax.inject.Inject

class WorkshopRepository @Inject constructor(
    private val workshopDao: WorkshopDao
) {

    suspend fun findAllWorkshops(): List<WorkshopEntity> {
        return workshopDao.findAllWorkshops()
    }

    suspend fun findWorkshopById(id: Int): WorkshopEntity {
        return workshopDao.findWorkshopById(id)
    }

    suspend fun bulkInsertWorkshops(workshops: List<WorkshopEntity>) {
        workshopDao.bulkInsertWorkshops(workshops)
    }
}