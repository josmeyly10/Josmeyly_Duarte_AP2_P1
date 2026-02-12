package com.example.josmeyly_duarte_ap2_p1.presentacion.edit


sealed class EditCervezasUiEvent {

    data class NombreChanged(val value: String) : EditCervezasUiEvent()
    data class MarcaChanged(val value: String) : EditCervezasUiEvent()
    data class PuntuacionChanged(val value: String) : EditCervezasUiEvent()
    data object Save : EditCervezasUiEvent()
    data object Delete : EditCervezasUiEvent()
}