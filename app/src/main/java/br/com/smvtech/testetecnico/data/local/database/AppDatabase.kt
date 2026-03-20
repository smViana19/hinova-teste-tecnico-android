package br.com.smvtech.testetecnico.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.smvtech.testetecnico.data.local.database.dao.WorkshopDao
import br.com.smvtech.testetecnico.data.local.database.model.WorkshopEntity


@Database(
    entities = [WorkshopEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workshopDao(): WorkshopDao
}