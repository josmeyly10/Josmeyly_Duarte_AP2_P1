package com.example.josmeyly_duarte_ap2_p1.domain.repository

import com.example.josmeyly_duarte_ap2_p1.data.local.entities.CervezaEntity
import com.example.josmeyly_duarte_ap2_p1.domain.model.Cerveza
import kotlinx.coroutines.flow.Flow

interface CervezaRepository {

    suspend fun upsert(cerveza: CervezaEntity)

    suspend fun delete(id:Int)

    suspend fun getById(id:Int):CervezaEntity?

    suspend fun existsByNombre(nombre:String,excludeId:Int) :Boolean

    fun observeById(id: Int): Flow<Cerveza?>

    fun observeAll(id:Int):Flow<List<CervezaEntity>>

}
