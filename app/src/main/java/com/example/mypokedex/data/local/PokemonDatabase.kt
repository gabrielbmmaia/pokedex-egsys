package com.example.mypokedex.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mypokedex.core.Constantes.POKEMON_DATABASE
import com.example.mypokedex.data.model.PokemonDto

@Database(
    entities = [PokemonDto::class],
    version = 1,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract val dao: PokemonDao

    companion object {
        @Volatile
        private var INSTANCE: PokemonDatabase? = null
        fun getInstance(context: Context): PokemonDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PokemonDatabase::class.java,
                        POKEMON_DATABASE
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
