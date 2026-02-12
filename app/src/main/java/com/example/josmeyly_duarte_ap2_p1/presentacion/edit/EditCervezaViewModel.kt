package com.example.josmeyly_duarte_ap2_p1.presentacion.edit
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.josmeyly_duarte_ap2_p1.domain.model.Cerveza
import com.example.josmeyly_duarte_ap2_p1.domain.usecase.CervezaValidationsUseCase
import com.example.josmeyly_duarte_ap2_p1.domain.usecase.DeleteCervezaUseCase
import com.example.josmeyly_duarte_ap2_p1.domain.usecase.GetCervezaUseCase
import com.example.josmeyly_duarte_ap2_p1.domain.usecase.UpsertCervezaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditCervezaViewModel @Inject constructor(
    private val getCervezaUseCase: GetCervezaUseCase,
    private val upsertCervezaUseCase: UpsertCervezaUseCase,
    private val deleteCervezaUseCase: DeleteCervezaUseCase,
    private val validations: CervezaValidationsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(EditCervezasUiState())
    val state: StateFlow<EditCervezasUiState> = _state.asStateFlow()

    private val cervezaId: Int = savedStateHandle.get<Int>("cervezaId") ?: 0

    init {
        if (cervezaId > 0) {
            onLoad(cervezaId)
        }
    }

    fun onEvent(event: EditCervezasUiEvent) {
        when (event) {
            is EditCervezasUiEvent.NombreChanged -> _state.update {
                it.copy(
                    nombre = event.value,
                    nombreError = null
                )
            }

            is EditCervezasUiEvent.MarcaChanged -> _state.update {
                it.copy(
                    marca = event.value,
                    marcaError = null
                )
            }

            is EditCervezasUiEvent.PuntuacionChanged -> _state.update {
                it.copy(
                    puntuacion = event.value,
                    puntuacionError = null
                )
            }

            EditCervezasUiEvent.Save -> onSave()

            EditCervezasUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int) {
        viewModelScope.launch {
            val cerveza = getCervezaUseCase(id)
            if (cerveza != null) {
                _state.update {
                    it.copy(
                        cervezaId = cerveza.cervezaId,  // ✅ Aquí está el cambio
                        nombre = cerveza.nombre,
                        marca = cerveza.marca,
                        puntuacion = cerveza.puntuacion.toString(),
                        isNew = false
                    )
                }
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {
            val nombre = _state.value.nombre
            val marca = _state.value.marca
            val puntuacion = _state.value.puntuacion
            val currentId = _state.value.cervezaId ?: 0

            val nombreValidation = validations.validateNombre(nombre, currentId)
            val marcaValidation = validations.validateMarca(marca)
            val puntuacionValidation = validations.validatePuntuacion(puntuacion)

            if (
                nombreValidation.isFailure ||
                marcaValidation.isFailure ||
                puntuacionValidation.isFailure
            ) {
                _state.update {
                    it.copy(
                        nombreError = nombreValidation.exceptionOrNull()?.message,
                        marcaError = marcaValidation.exceptionOrNull()?.message,
                        puntuacionError = puntuacionValidation.exceptionOrNull()?.message
                    )
                }
                return@launch
            }

            _state.update { it.copy(isSaving = true) }

            val cerveza = Cerveza(
                cervezaId = currentId,
                nombre = nombre,
                marca = marca,
                puntuacion = puntuacion.toInt()
            )

            val result = upsertCervezaUseCase(cerveza)

            result.onSuccess {
                _state.update {
                    it.copy(
                        isSaving = false,
                        success = true
                    )
                }
            }.onFailure {
                _state.update { it.copy(isSaving = false) }
            }
        }
    }

    private fun onDelete() {
        val id = _state.value.cervezaId ?: return

        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }

            deleteCervezaUseCase(id)

            _state.update {
                it.copy(
                    isDeleting = false,
                    success = true
                )
            }
        }
    }
}