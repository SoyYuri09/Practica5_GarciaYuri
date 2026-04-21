package garcia.yuri.pokemonroom.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName =  "pokemon_table")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val name: String,
    val number: String,
    val type: String,
    val level: Int = 1,
)