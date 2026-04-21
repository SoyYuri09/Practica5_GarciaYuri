package garcia.yuri.pokemonroom.data

class PokemonRepository(private val pokemonDao: PokemonDao){
    val allPokemons = pokemonDao.getAll()

    suspend fun add(pokemon: PokemonEntity){
        pokemonDao.add(pokemon)
    }
}