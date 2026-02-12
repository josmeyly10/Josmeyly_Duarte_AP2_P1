package com.example.josmeyly_duarte_ap2_p1.presentacion.list

import com.example.josmeyly_duarte_ap2_p1.domain.model.Cerveza


sealed interface ListCervezasUiEvent {

    data class SearchNombreChanged(val nombre: String) : ListCervezasUiEvent

    data class SearchMarcaChanged(val marca: String) : ListCervezasUiEvent

    data class OnCervezaClick(val id: Int) : ListCervezasUiEvent

    data object OnAddClick : ListCervezasUiEvent

    data class OnDeleteCerveza(val id: Int) : ListCervezasUiEvent

    data class OnSaveCerveza(val cerveza: Cerveza) : ListCervezasUiEvent
}