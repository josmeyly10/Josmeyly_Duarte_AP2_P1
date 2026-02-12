package com.example.josmeyly_duarte_ap2_p1.presentacion.edit

data class EditCervezasUiState(
    val cervezaId: Int? = null,
    val nombre: String = "",
    val nombreError: String? = null,
    val marca: String = "",
    val marcaError: String? = null,
    val puntuacion: String = "",
    val puntuacionError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val success: Boolean = false
)