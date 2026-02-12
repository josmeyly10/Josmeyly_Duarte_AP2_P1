package com.example.josmeyly_duarte_ap2_p1.domain.usecase

import com.example.josmeyly_duarte_ap2_p1.domain.repository.CervezaRepository
import javax.inject.Inject


    class DeleteCervezaUseCase @Inject constructor(
        private val repository: CervezaRepository
    ) {
        suspend operator fun invoke(id: Int) = repository.delete(id)
    }

