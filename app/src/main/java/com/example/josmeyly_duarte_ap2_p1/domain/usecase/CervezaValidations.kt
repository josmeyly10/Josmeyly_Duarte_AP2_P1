package com.example.josmeyly_duarte_ap2_p1.domain.usecase

import com.example.josmeyly_duarte_ap2_p1.domain.repository.CervezaRepository
import javax.inject.Inject


class CervezaValidationsUseCase @Inject constructor(
    private val repository: CervezaRepository
) {
    suspend fun validateNombre(value: String, currentId: Int = 0): Result<String> {
        return when {
            value.isBlank() -> Result.failure(
                IllegalArgumentException("El nombre de la cerveza no puede estar vacío")
            )
            repository.existsByNombre(value.trim(), currentId) -> Result.failure(
                IllegalArgumentException("Ya existe una cerveza con ese nombre")
            )
            else -> Result.success(value.trim())
        }
    }

    fun validateMarca(value: String): Result<String> {
        return when {
            value.isBlank() -> Result.failure(
                IllegalArgumentException("La marca no puede estar vacía")
            )
            else -> Result.success(value.trim())
        }
    }

    fun validatePuntuacion(value: String): Result<Int> {
        return when {
            value.isBlank() -> Result.failure(
                IllegalArgumentException("La puntuación no puede estar vacía")
            )
            value.toIntOrNull() == null -> Result.failure(
                IllegalArgumentException("La puntuación debe ser un número válido")
            )
            value.toInt() < 1 -> Result.failure(
                IllegalArgumentException("La puntuación debe ser al menos 1")
            )
            value.toInt() > 5 -> Result.failure(
                IllegalArgumentException("La puntuación no puede ser mayor a 5")
            )
            else -> Result.success(value.toInt())
        }
    }
}