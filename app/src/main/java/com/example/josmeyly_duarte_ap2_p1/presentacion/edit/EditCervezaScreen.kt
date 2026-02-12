package com.example.josmeyly_duarte_ap2_p1.presentacion.edit

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditCervezaScreen(
    viewModel: EditCervezaViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.success) {
        if (state.success) {
            onNavigateBack()
        }
    }

    EditCervezasBody(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun EditCervezasBody(
    state: EditCervezasUiState,
    onEvent: (EditCervezasUiEvent) -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(all = 16.dp)
        ) {
            OutlinedTextField(
                value = state.nombre,
                onValueChange = { onEvent(EditCervezasUiEvent.NombreChanged(value = it)) },
                label = { Text(text = "Nombre") },
                placeholder = { Text(text = "Ej: Presidente, Corona, Stella") },
                isError = state.nombreError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.nombreError != null) {
                Text(
                    text = state.nombreError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(height = 12.dp))

            OutlinedTextField(
                value = state.marca,
                onValueChange = { onEvent(EditCervezasUiEvent.MarcaChanged(value = it)) },
                label = { Text(text = "Marca") },
                placeholder = { Text(text = "Origen de la cerveza") },
                isError = state.marcaError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.marcaError != null) {
                Text(
                    text = state.marcaError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(height = 12.dp))

            OutlinedTextField(
                value = state.puntuacion,
                onValueChange = { onEvent(EditCervezasUiEvent.PuntuacionChanged(value = it)) },
                label = { Text(text = "Puntuación (1-5)") },
                isError = state.puntuacionError != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            if (state.puntuacionError != null) {
                Text(
                    text = state.puntuacionError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(height = 16.dp))

            Row {
                Button(
                    onClick = { onEvent(EditCervezasUiEvent.Save) },
                    enabled = !state.isSaving
                ) {
                    Text(text = "Guardar")
                }
                Spacer(Modifier.width(width = 8.dp))
                if (!state.isNew) {
                    OutlinedButton(
                        onClick = { onEvent(EditCervezasUiEvent.Delete) },
                        enabled = !state.isDeleting
                    ) {
                        Text(text = "Eliminar")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditCervezasBodyPreview() {
    val state = EditCervezasUiState()
    MaterialTheme {
        EditCervezasBody(state = state, onEvent = {})
    }
}