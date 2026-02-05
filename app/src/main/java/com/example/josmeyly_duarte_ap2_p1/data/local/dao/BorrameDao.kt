package com.example.josmeyly_duarte_ap2_p1.data.local.dao

import com.example.josmeyly_duarte_ap2_p1.data.local.entities.BorrameEntity

interface BorrameDao {
    suspend fun upsert(asignatura: BorrameEntity)
}