package br.com.smvtech.testetecnico.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workshops")
data class WorkshopEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "photo")
    var photo: String = "",
    @ColumnInfo(name = "description")
    var description: String = "",
    @ColumnInfo(name = "address")
    var address: String = "",
    @ColumnInfo(name = "latitude")
    var latitude: String = "",
    @ColumnInfo(name = "longitude")
    var longitude: String = "",
    @ColumnInfo(name = "phone")
    var phone: String = "",
    @ColumnInfo(name = "email")
    var email: String = "",

)