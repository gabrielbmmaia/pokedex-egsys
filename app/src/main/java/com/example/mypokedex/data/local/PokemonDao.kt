package com.example.mypokedex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mypokedex.data.model.PokemonDto

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemonList(pokemonList: List<PokemonDto>)

    @Query("SELECT * FROM pokemondto")
    suspend fun getPokemonList(): List<PokemonDto>

}
