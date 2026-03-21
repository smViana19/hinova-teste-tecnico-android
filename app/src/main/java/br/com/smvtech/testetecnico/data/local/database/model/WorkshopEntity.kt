package br.com.smvtech.testetecnico.data.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.smvtech.testetecnico.domain.model.workshop.Workshop

@Entity(tableName = "workshops")
data class WorkshopEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "photo")
    var photo: String = "",
    @ColumnInfo(name = "description")
    var description: String = "",
    @ColumnInfo(name = "short_description")
    var shortDescription: String = "",
    @ColumnInfo(name = "user_rating")
    var userRating: Double = 0.0,
    @ColumnInfo(name = "address")
    var address: String = "",
    @ColumnInfo(name = "latitude")
    var latitude: String = "",
    @ColumnInfo(name = "longitude")
    var longitude: String = "",
    @ColumnInfo(name = "phone")
    var phone: String? = "",
    @ColumnInfo(name = "second_phone")
    var secondPhone: String? = "",
    @ColumnInfo(name = "email")
    var email: String = "",
    @ColumnInfo(name = "active")
    var active: Boolean = false,
    @ColumnInfo(name = "associate_code")
    var associateCode: Int = 0


)

fun Workshop.toEntity() = WorkshopEntity(
    id = this.id,
    name = this.name,
    photo = this.photo,
    description = this.description,
    address = this.address,
    latitude = this.latitude,
    longitude = this.longitude,
    phone = this.phone,
    secondPhone = this.secondPhone,
    email = this.email,
    active = this.active
)

fun WorkshopEntity.toDomain() = Workshop(
    id = this.id,
    name = this.name,
    photo = this.photo,
    description = this.description,
    address = this.address,
    latitude = this.latitude,
    longitude = this.longitude,
    phone = this.phone,
    secondPhone = this.secondPhone,
    email = this.email,
    active = this.active,
    shortDescription = this.shortDescription,
    userRating = this.userRating,
    associateCode = this.associateCode

)