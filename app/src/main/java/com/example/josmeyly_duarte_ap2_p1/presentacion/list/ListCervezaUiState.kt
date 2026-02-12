package com.example.josmeyly_duarte_ap2_p1.presentacion.list
import com.example.josmeyly_duarte_ap2_p1.domain.model.Cerveza

data class ListCervezasUiState(
    val cervezasList: List<Cerveza> = emptyList(),
    val isLoading: Boolean = false,
    val searchNombre: String = "",
    val searchMarca: String = "",
    val totalCervezas: Int = 0,
    val promedioPuntuacion: Double = 0.0
    )
