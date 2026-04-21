package garcia.yuri.pokemonroom.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class PokemonDatabase : RoomDatabase(){
    abstract fun pokemonDao(): PokemonDao

    companion object{
        @Volatile
        private var INSTANCE: PokemonDatabase? = null

        fun getDatabase(context: Context): PokemonDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDatabase::class.java,
                    "pokemon_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}