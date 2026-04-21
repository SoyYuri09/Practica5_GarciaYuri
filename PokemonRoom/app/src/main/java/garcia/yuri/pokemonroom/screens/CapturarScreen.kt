package garcia.yuri.pokemonroom.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import garcia.yuri.pokemonroom.viewModel.PokemonViewModel

@Composable
fun CapturarScreen(
    pokemonViewModel: PokemonViewModel,
    onBack: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {pokemonViewModel.searchPokemon() }
        ) {
            Text("Buscar en la hierva")
        }
        Spacer(modifier = Modifier.height(16.dp))
        pokemonViewModel.wildPokemon?.let { pokemon ->
            Text("¡Apareció un ${pokemon.name}!")
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { pokemonViewModel.capturePokemon() }
            ) {
                Text("Capturar")
            }
        }

        if (pokemonViewModel.pokemonSeEscapo){
            Text("El pokemon se escapó", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text("Pokémons capturados: ${pokemonViewModel.capturedPokemons.size}")

        LazyColumn{ //Estos son los de la base de datos
            items(pokemonViewModel.capturedPokemons){ pokemon ->
                Text("${pokemon.name} - ${pokemon.type}")
            }
        }
        Spacer(modifier = Modifier.height(28.dp))
        ElevatedButton( //Botón para mandar a la bolsa
            onClick = { //Acá es donde se manda a llamar a la inserción de los pokemons a la base de datos
                for(pokemon in pokemonViewModel.capturedPokemons){
                    pokemonViewModel.addPokemon(pokemon.name, pokemon.number, pokemon.type)
                }
                onBack() //cuidado con el onBack ya que en teoria cuando regreso y vuelvo a entrar a la pantalla de capturar ahora mismo aparecen los pkemons igual que ya capture y puedo volver a mandar a la bolsa (pero eso no debe ser así leugo corregir)
            }
        ) {
            Text("Enviar a la bolsa")
        }
        TextButton(
            onClick = {
                pokemonViewModel.releaseCapturedPokemons()
            }
        ) {
            Text("Liberar pokemons")
        }
    }
}