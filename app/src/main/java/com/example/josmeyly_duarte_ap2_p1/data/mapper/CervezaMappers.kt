package com.example.josmeyly_duarte_ap2_p1.data.mapper
import com.example.josmeyly_duarte_ap2_p1.data.local.entities.CervezaEntity
import com.example.josmeyly_duarte_ap2_p1.domain.model.Cerveza

fun  CervezaEntity.toCerveza(): Cerveza {
    return Cerveza(
        cervezaId = cervezaId,
        nombre = nombre,
        marca = marca,
        puntuacion = puntuacion
    )
}

fun Cerveza.toEntity(): CervezaEntity {
    return CervezaEntity(
        cervezaId = cervezaId,
        nombre = nombre,
        marca = marca,
        puntuacion = puntuacion
    )
}





