package garcia.yuri.pokemonroom.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import garcia.yuri.pokemonroom.data.PokemonEntity
import garcia.yuri.pokemonroom.data.PokemonRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch

class PokemonViewModel(private val repository: PokemonRepository): ViewModel() {
    //Cambiar para hacerlo con bd
    private val availablePokemons = listOf(
        PokemonEntity(name = "Zeraora", number = "807", type = "Electric"),
        PokemonEntity(name = "Lucario", number = "448", type = "Fight"),
        PokemonEntity(name = "Zoroark", number = "571", type = "Dark")
    )
    var wildPokemon by mutableStateOf<PokemonEntity?>(null)
        private set

    var capturedPokemons by mutableStateOf(listOf<PokemonEntity>())
        private set

    var pokemonSeEscapo by mutableStateOf(false)
        private set

    fun searchPokemon(){
        wildPokemon = availablePokemons.random()
    }

    fun releaseCapturedPokemons(){
        capturedPokemons = emptyList()
    }

    //Cambiar para hacerlo con bd
    fun capturePokemon(){
        wildPokemon?.let {
            val succes = (1..100).random()
            if(succes > 50){
                capturedPokemons = capturedPokemons + it
                pokemonSeEscapo = false
                wildPokemon = null
            } else {
                pokemonSeEscapo = true
                wildPokemon = null
            }
        }
    }

    val pokemonState: StateFlow<List<PokemonEntity>> = repository.allPokemons
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addPokemon(name: String, number: String, type: String, level: Int = 1){
        viewModelScope.launch {
            repository.add(
                PokemonEntity(
                    name = name,
                    number = number,
                    type = type,
                    level = level
                )
            )
        }
    }


}