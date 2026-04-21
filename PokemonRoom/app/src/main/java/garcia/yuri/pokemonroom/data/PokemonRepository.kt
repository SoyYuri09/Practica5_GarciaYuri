package garcia.yuri.pokemonroom.data

class PokemonRepository(private val pokemonDao: PokemonDao){
    val allPokemons = pokemonDao.getAll()

    suspend fun add(pokemon: PokemonEntity){
        pokemonDao.add(pokemon)
    }

    suspend fun delete(pokemon: PokemonEntity){
        pokemonDao.delete(pokemon)
    }

    suspend fun update(pokemon: PokemonEntity){
        pokemonDao.update(pokemon)
    }

    fun filter(type: String?, minLevel: Int?) =
        pokemonDao.filterPokemons(type, minLevel)
}