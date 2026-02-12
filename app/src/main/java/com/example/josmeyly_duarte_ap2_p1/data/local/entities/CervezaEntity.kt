package com.example.josmeyly_duarte_ap2_p1.data.local.entities
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cervezas")
data class CervezaEntity(
    @PrimaryKey(autoGenerate = true)
    val cervezaId: Int = 0,
    val nombre: String,
    val marca: String,
    val puntuacion: Int,

)