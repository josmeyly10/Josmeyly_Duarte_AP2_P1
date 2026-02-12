package com.example.josmeyly_duarte_ap2_p1.domain.usecase
import com.example.josmeyly_duarte_ap2_p1.domain.model.Cerveza
import com.example.josmeyly_duarte_ap2_p1.domain.repository.CervezaRepository
import javax.inject.Inject

class UpsertCervezaUseCase @Inject constructor(
    private val repository: CervezaRepository,
) {
    private val validations = CervezaValidationsUseCase(repository)

    suspend operator fun invoke(cerveza: Cerveza): Result<Unit> {
        val nombreResult = validations.validateNombre(
            value = cerveza.nombre,
            currentId = cerveza.cervezaId
        )
        if (nombreResult.isFailure) {
            return Result.failure(
                IllegalArgumentException(nombreResult.exceptionOrNull()?.message)
            )
        }

        val marcaResult = validations.validateMarca(value = cerveza.marca)
        if (marcaResult.isFailure) {
            return Result.failure(
                IllegalArgumentException(marcaResult.exceptionOrNull()?.message)
            )
        }

        val puntuacionResult = validations.validatePuntuacion(value = cerveza.puntuacion.toString())
        if (puntuacionResult.isFailure) {
            return Result.failure(
                IllegalArgumentException(puntuacionResult.exceptionOrNull()?.message)
            )
        }

        return runCatching {
            repository.upsert(cerveza)
        }
    }
}