package com.example.josmeyly_duarte_ap2_p1.presentacion.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.josmeyly_duarte_ap2_p1.domain.model.Cerveza
import com.example.josmeyly_duarte_ap2_p1.domain.repository.CervezaRepository
import com.example.josmeyly_duarte_ap2_p1.domain.usecase.DeleteCervezaUseCase
import com.example.josmeyly_duarte_ap2_p1.domain.usecase.UpsertCervezaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListCervezasViewModel @Inject constructor(
    private val repository: CervezaRepository,
    private val deleteCervezasUseCase: DeleteCervezaUseCase,
    private val upsertCervezasUseCase: UpsertCervezaUseCase
) : ViewModel() {

    private val _searchNombre = MutableStateFlow("")
    private val _searchMarca = MutableStateFlow("")
    private val _state = MutableStateFlow(ListCervezasUiState())
    val state: StateFlow<ListCervezasUiState> = _state.asStateFlow()

    init {
        loadCervezas()
    }

    fun onEvent(event: ListCervezasUiEvent) {
        when (event) {
            is ListCervezasUiEvent.SearchNombreChanged -> {
                _searchNombre.value = event.nombre
                _state.value = _state.value.copy(searchNombre = event.nombre)
            }
            is ListCervezasUiEvent.SearchMarcaChanged -> {
                _searchMarca.value = event.marca
                _state.value = _state.value.copy(searchMarca = event.marca)
            }
            is ListCervezasUiEvent.OnCervezaClick -> {
                // Navegación manejada por el Screen
            }
            is ListCervezasUiEvent.OnAddClick -> {
                // Navegación manejada por el Screen
            }
            is ListCervezasUiEvent.OnDeleteCerveza -> {
                deleteCerveza(event.id)
            }
            is ListCervezasUiEvent.OnSaveCerveza -> {
                saveCerveza(event.cerveza)
            }
        }
    }

    private fun loadCervezas() {
        viewModelScope.launch {
            combine(
                repository.observeAll(),
                _searchNombre,
                _searchMarca
            ) { cervezasList, nombre, marca ->
                val filteredList = cervezasList.filter {
                    val matchesNombre = nombre.isBlank() ||
                            it.nombre.contains(nombre, ignoreCase = true)
                    val matchesMarca = marca.isBlank() ||
                            it.marca.contains(marca, ignoreCase = true)
                    matchesNombre && matchesMarca
                }

                val total = filteredList.size
                val promedio = if (filteredList.isNotEmpty()) {
                    filteredList.map { it.puntuacion }.average()
                } else {
                    0.0
                }

                Triple(filteredList, total, promedio)
            }.collect { (filteredCervezas, total, promedio) ->
                _state.value = _state.value.copy(
                    cervezasList = filteredCervezas,
                    isLoading = false,
                    totalCervezas = total,
                    promedioPuntuacion = promedio
                )
            }
        }
    }

    private fun saveCerveza(cerveza: Cerveza) {
        viewModelScope.launch {
            upsertCervezasUseCase(cerveza)
        }
    }

    private fun deleteCerveza(id: Int) {
        viewModelScope.launch {
            deleteCervezasUseCase(id)
        }
    }
}