package com.example.josmeyly_duarte_ap2_p1.domain.repository

import com.example.josmeyly_duarte_ap2_p1.domain.model.Borrame
import kotlinx.coroutines.flow.Flow

interface BorrameRepository {

    fun observeById(id: Int): Flow<Borrame?>
}