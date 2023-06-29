package com.pokemon.mypokedex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pokemon.mypokedex.data.local.model.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemonList(pokemonList: List<PokemonEntity>)

    @Query("SELECT * FROM pokemonentity")
    fun getPokemonList(): Flow<List<PokemonEntity>>

    @Query(
        "SELECT * FROM pokemonentity " +
                "WHERE name LIKE :pokemonOrId || '%' " +
                "OR id LIKE :pokemonOrId || '%'"
    )
    fun searchPokemonList(pokemonOrId: String): Flow<List<PokemonEntity>>
}