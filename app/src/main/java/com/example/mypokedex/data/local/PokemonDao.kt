package com.example.mypokedex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mypokedex.data.model.PokemonDto
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemonList(pokemonList: List<PokemonDto>)

    @Query("SELECT * FROM pokemondto")
    suspend fun getPokemonList(): List<PokemonDto>

    @Query("SELECT * FROM pokemondto WHERE name LIKE '%' || :searchQuery || '% ' ORDER BY roomId ASC")
    fun searchPokemonByQuery(searchQuery: String): Flow<List<PokemonDto>>
}
