package com.example.josmeyly_duarte_ap2_p1.domain.repository

import com.example.josmeyly_duarte_ap2_p1.data.local.entities.CervezaEntity
import com.example.josmeyly_duarte_ap2_p1.domain.model.Cerveza
import kotlinx.coroutines.flow.Flow

interface CervezaRepository {
    suspend fun upsert(cerveza: Cerveza)
    suspend fun delete(id: Int)
    suspend fun getById(id: Int): Cerveza?
    suspend fun existsByNombre(nombre: String, excludeId: Int = 0): Boolean
    fun observeById(id: Int): Flow<Cerveza?>
    fun observeAll(): Flow<List<Cerveza>>
}