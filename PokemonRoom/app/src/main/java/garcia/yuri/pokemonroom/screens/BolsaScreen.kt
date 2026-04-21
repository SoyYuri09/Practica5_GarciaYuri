package garcia.yuri.pokemonroom.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import garcia.yuri.pokemonroom.data.PokemonEntity
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import garcia.yuri.pokemonroom.viewModel.PokemonViewModel

@Composable
fun BolsaScreen(
    pokemonViewModel: PokemonViewModel
) {

    val pokemons = pokemonViewModel.filteredPokemons

    var showDialog by remember { mutableStateOf(false) }
    var selectedPokemon by remember { mutableStateOf<PokemonEntity?>(null) }

    var expanded by remember { mutableStateOf(false) }

    val types = listOf(
        "Acero","Agua","Bicho","Dragón","Eléctrico","Fantasma","Fuego","Hada",
        "Hielo","Lucha","Normal","Planta","Psíquico","Roca","Siniestro",
        "Tierra","Veneno","Volador"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Bolsa de Pokemon",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = pokemonViewModel.searchQuery,
            onValueChange = { pokemonViewModel.onSearchQueryChange(it) },
            label = { Text("Buscar por nombre o tipo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        if (pokemonViewModel.levelUpFailed && pokemonViewModel.failedPokemonName != null) {
            Text(
                text = "${pokemonViewModel.failedPokemonName} falló al subir de nivel",
                color = Color.Red
            )
        }
        Spacer(Modifier.height(16.dp))

        //Parte para el filtro de tipo
        Box(modifier = Modifier.fillMaxWidth()) {

            OutlinedTextField(
                value = pokemonViewModel.selectedType ?: "Todos",
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo") },
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { expanded = true }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Todos") },
                    onClick = {
                        pokemonViewModel.onTypeSelected(null)
                        expanded = false
                    }
                )
                types.forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type) },
                        onClick = {
                            pokemonViewModel.onTypeSelected(type)
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        //Parte para filtrar por nivel minimo
        OutlinedTextField(
            value = pokemonViewModel.minLevel?.toString() ?: "",
            onValueChange = { pokemonViewModel.onMinLevelChange(it) },
            label = { Text("Nivel mínimo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pokemons) { pokemon ->

                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {

                        Text(
                            text = pokemon.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(text = "Tipo: ${pokemon.type}")
                        Text(text = "Nivel: ${pokemon.level}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            Button(
                                onClick = {
                                    selectedPokemon = pokemon
                                    showDialog = true
                                }
                            ) {
                                Text("Eliminar")
                            }

                            Button(
                                onClick = {
                                    pokemonViewModel.levelUpPokemon(pokemon)
                                }
                            ) {
                                Text("Subir nivel")
                            }
                        }
                    }
                }
            }
        }
    }
    //Dialogo de confirmación al eliminar
    if (showDialog && selectedPokemon != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        pokemonViewModel.deletePokemon(selectedPokemon!!)
                        showDialog = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancelar")
                }
            },
            title = { Text("Confirmar") },
            text = { Text("¿Eliminar este Pokémon?") }
        )
    }
}