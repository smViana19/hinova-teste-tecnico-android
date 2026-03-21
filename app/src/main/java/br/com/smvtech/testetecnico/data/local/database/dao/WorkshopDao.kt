package br.com.smvtech.testetecnico.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.smvtech.testetecnico.data.local.database.model.WorkshopEntity

@Dao
interface WorkshopDao {
    @Query("SELECT * FROM workshops")
    suspend fun findAllWorkshops(): List<WorkshopEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bulkInsertWorkshops(workshops: List<WorkshopEntity>)
    @Query("SELECT * FROM workshops WHERE id = :id")
    suspend fun findWorkshopById(id: Int): WorkshopEntity
}