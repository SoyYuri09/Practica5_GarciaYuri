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
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class PokemonViewModel(private val repository: PokemonRepository): ViewModel() {
    //Cambiar para hacerlo con bd
    private val availablePokemons = listOf(
        PokemonEntity(name = "Zeraora", number = "807", type = "Eléctrico"),
        PokemonEntity(name = "Lucario", number = "448", type = "Lucha"),
        PokemonEntity(name = "Zoroark", number = "571", type = "Siniestro")
    )
    var wildPokemon by mutableStateOf<PokemonEntity?>(null)
        private set

    var capturedPokemons by mutableStateOf(listOf<PokemonEntity>())
        private set

    var pokemonSeEscapo by mutableStateOf(false)
        private set

    var levelUpFailed by mutableStateOf(false)
        private set

    var selectedType by mutableStateOf<String?>(null)
        private set

    var minLevel by mutableStateOf<Int?>(null)
        private set

    var filteredPokemons by mutableStateOf<List<PokemonEntity>>(emptyList())
        private set

    var failedPokemonName by mutableStateOf<String?>(null)
        private set

    fun applyFilters() {
        viewModelScope.launch {
            repository.filter(selectedType, minLevel).collect {
                filteredPokemons = it
            }
        }
    }

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

    //Función para eliminar el pokemon
    fun deletePokemon(pokemon: PokemonEntity){
        viewModelScope.launch {
            repository.delete(pokemon)
        }
    }

    //Función que permite subir de nivel a un pokemon
    fun levelUpPokemon(pokemon: PokemonEntity){
        if(pokemon.level >= 100) return

        val success = (1..100).random()

        if(success <= 70){
            val updatedPokemon = pokemon.copy(level = pokemon.level + 1)
            viewModelScope.launch {
                repository.update(updatedPokemon)
            }
            levelUpFailed = false
            failedPokemonName = null
        } else {
            levelUpFailed = true
            failedPokemonName = pokemon.name
        }
    }

    fun onTypeSelected(type: String?) {
        selectedType = type
        applyFilters()
    }

    fun onMinLevelChange(level: String) {
        minLevel = level.toIntOrNull()
        applyFilters()
    }

    init {
        applyFilters()
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