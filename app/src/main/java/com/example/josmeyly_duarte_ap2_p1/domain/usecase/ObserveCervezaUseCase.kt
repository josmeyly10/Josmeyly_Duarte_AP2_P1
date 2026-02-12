package com.example.josmeyly_duarte_ap2_p1.domain.usecase

import com.example.josmeyly_duarte_ap2_p1.domain.model.Cerveza
import com.example.josmeyly_duarte_ap2_p1.domain.repository.CervezaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCervezaUseCase @Inject constructor(
    private val repository: CervezaRepository
) {
    operator fun invoke(id: Int): Flow<Cerveza?> {
        return repository.observeById(id)
    }
}




