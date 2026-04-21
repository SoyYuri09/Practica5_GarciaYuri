package garcia.yuri.pokemonroom.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao{
    @Query("SELECT * FROM pokemon_table ORDER BY number ASC")
    fun getAll(): Flow<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(pokemon: PokemonEntity)

    //Query para el buscador de pokemon usando LIKE Y WHERE
    @Query("""
        SELECT * FROM pokemon_table
        WHERE (:type IS NULL OR type = :type)
        AND (:minLevel IS NULL OR level >= :minLevel)
        AND (
            :query IS NULL OR 
            name LIKE '%' || :query || '%' OR 
            type LIKE '%' || :query || '%'
        )
        ORDER BY number ASC
    """)
    fun filterAndSearch(
        type: String?,
        minLevel: Int?,
        query: String?
    ): Flow<List<PokemonEntity>>

    //Delete para el listado del pokemon
    @Delete
    suspend fun delete(pokemon: PokemonEntity)

    //Acá un update apra actualizar el nivel del pokemon
    @Update
    suspend fun update(pokemon: PokemonEntity)

    //Query para el filtrado de pokemon por tipo oh por nivel minimo
    @Query("""
        SELECT * FROM pokemon_table
        WHERE (:type IS NULL OR type = :type)
        AND (:minLevel IS NULL OR level >= :minLevel)
        ORDER BY number ASC
    """)
    fun filterPokemons(type: String?, minLevel: Int?): Flow<List<PokemonEntity>>
}