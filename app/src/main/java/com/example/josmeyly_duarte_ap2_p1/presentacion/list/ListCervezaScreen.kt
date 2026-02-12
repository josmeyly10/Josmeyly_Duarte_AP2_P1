package com.example.josmeyly_duarte_ap2_p1.presentacion.list
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.josmeyly_duarte_ap2_p1.domain.model.Cerveza

@Composable
fun ListCervezaScreen(
    viewModel: ListCervezasViewModel = hiltViewModel(),
    onNavigateToEdit: (Int?) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToEdit(null) }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar cerveza")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            FilterSection(
                nombreQuery = state.searchNombre,
                marcaQuery = state.searchMarca,
                onNombreChange = { viewModel.onEvent(ListCervezasUiEvent.SearchNombreChanged(it)) },
                onMarcaChange = { viewModel.onEvent(ListCervezasUiEvent.SearchMarcaChanged(it)) },
                modifier = Modifier.padding(16.dp)
            )

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                state.cervezasList.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = if (state.searchNombre.isBlank() && state.searchMarca.isBlank()) {
                                    "No hay cervezas registradas"
                                } else {
                                    "No se encontraron resultados"
                                },
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            if (state.searchNombre.isBlank() && state.searchMarca.isBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Presiona el botón + para agregar una",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = state.cervezasList,
                            key = { it.cervezaId }
                        ) { cerveza ->
                            CervezaCard(
                                cerveza = cerveza,
                                onEdit = {
                                    onNavigateToEdit(cerveza.cervezaId)
                                },
                                onDelete = {
                                    viewModel.onEvent(
                                        ListCervezasUiEvent.OnDeleteCerveza(cerveza.cervezaId)
                                    )
                                }
                            )
                        }


                        item {
                            TotalsCard(
                                totalCervezas = state.totalCervezas,
                                promedioPuntuacion = state.promedioPuntuacion
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterSection(
    nombreQuery: String,
    marcaQuery: String,
    onNombreChange: (String) -> Unit,
    onMarcaChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = nombreQuery,
            onValueChange = onNombreChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar por nombre...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Buscar")
            },
            singleLine = true,
            shape = MaterialTheme.shapes.large
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = marcaQuery,
            onValueChange = onMarcaChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar por marca...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Buscar")
            },
            singleLine = true,
            shape = MaterialTheme.shapes.large
        )
    }
}

@Composable
private fun CervezaCard(
    cerveza: Cerveza,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cerveza.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Marca: ${cerveza.marca}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "⭐ Puntuación: ${cerveza.puntuacion}/5",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Row {
                IconButton(onClick = onEdit) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
private fun TotalsCard(
    totalCervezas: Int,
    promedioPuntuacion: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Resumen",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Total de cervezas probadas: $totalCervezas",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Promedio de puntuación: ${String.format("%.2f", promedioPuntuacion)}/5",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CervezaCardPreview() {
    val cerveza = Cerveza(
        cervezaId = 1,
        nombre = "Presidente",
        marca = "República Dominicana",
        puntuacion = 4
    )
    MaterialTheme {
        CervezaCard(
            cerveza = cerveza,
            onEdit = {},
            onDelete = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TotalsCardPreview() {
    MaterialTheme {
        TotalsCard(
            totalCervezas = 15,
            promedioPuntuacion = 3.8
        )
    }
}