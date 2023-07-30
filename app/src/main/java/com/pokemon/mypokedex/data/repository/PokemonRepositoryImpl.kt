package com.pokemon.mypokedex.data.repository

import android.util.Log
import com.pokemon.mypokedex.core.Constantes.POKEMON_ERROR_MESSAGE
import com.pokemon.mypokedex.core.Constantes.REPOSITORY_ERROR_TAG
import com.pokemon.mypokedex.core.Resource
import com.pokemon.mypokedex.data.local.PokemonDatabase
import com.pokemon.mypokedex.data.mappers.toChain
import com.pokemon.mypokedex.data.mappers.toPokemon
import com.pokemon.mypokedex.data.mappers.toPokemonDetails
import com.pokemon.mypokedex.data.mappers.toPokemonEntity
import com.pokemon.mypokedex.data.mappers.toPokemonForms
import com.pokemon.mypokedex.data.networking.PokemonServices
import com.pokemon.mypokedex.domain.model.Chain
import com.pokemon.mypokedex.domain.model.Pokemon
import com.pokemon.mypokedex.domain.model.PokemonDetails
import com.pokemon.mypokedex.domain.model.pokemonForms.PokemonForms
import com.pokemon.mypokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PokemonRepositoryImpl(
    private val pokemonServices: PokemonServices,
    private val pokemonDatabase: PokemonDatabase
) : PokemonRepository {

    /**
     * Pega a lista de pokemon salva no banco de dados
     * */
    override suspend fun getPokemonList(): Flow<List<Pokemon>> {
        return pokemonDatabase.dao.getPokemonList().map { pokemonList ->
            pokemonList.map { it.toPokemon() }
        }
    }

    override suspend fun addPokemonList(pokemonList: List<Pokemon>) {
        pokemonDatabase.dao.addPokemonList(pokemonList.map { it.toPokemonEntity() })
    }

    override suspend fun getFilteredPokemonList(): List<Pokemon> {
        return try {
            val pokemonList = pokemonServices.getPokemonList().results.map { it.toPokemon() }
            pokemonList.filter { it.id < 10000 }

        } catch (e: Exception) {
            Log.e("TAG", "synchronizePokemonList: ${e.stackTrace}")
            emptyList()
        }
    }

    override suspend fun searchPokemonList(name: String): Flow<List<Pokemon>> {
        return pokemonDatabase.dao.searchPokemonList(name).map { pokemonList ->
            pokemonList.map { it.toPokemon() }
        }
    }

    /**
     * Pega lista de Pokemon a partir de um determinado tipo
     * indicado no parâmetro
     * */
    override suspend fun getPokemonByType(pokemonType: String): List<Pokemon> {
        val serviceReturn = pokemonServices.getPokemonFromType(pokemonType)
        val pokemonList = serviceReturn.results.map { it.results }

        return pokemonList.map { it.toPokemon() }
    }

    /**
     * Pega os detalhes do Pokemon a partir de ID
     * */
    override suspend fun getPokemonDetails(
        pokemonId: Int
    ): Flow<Resource<PokemonDetails>> = flow {
        try {
            emit(Resource.Loading)
            val pokemonDetail = pokemonServices.getPokemonDetail(pokemonId)
            emit(Resource.Success(pokemonDetail.toPokemonDetails()))
        } catch (e: Exception) {
            Log.e(REPOSITORY_ERROR_TAG, "getPokemonDetails: ${e.stackTrace}")
            emit(Resource.Error(message = POKEMON_ERROR_MESSAGE))
        }
    }

    /**
     * Pega a Specie do Pokemon a partir do seu ID
     * */
    override suspend fun getPokemonForms(
        pokemonId: Int
    ): Flow<Resource<PokemonForms>> = flow {
        try {
            emit(Resource.Loading)
            val pokemonForms = pokemonServices.getPokemonSpecie(pokemonId)
            emit(Resource.Success(pokemonForms.toPokemonForms()))
        } catch (e: Exception) {
            Log.e(REPOSITORY_ERROR_TAG, "getPokemonForms: ${e.stackTrace}")
            emit(Resource.Error())
        }
    }

    /**
     * Pega a cadeia de evoluções do Pokemon a partir do evolutionChainID
     * do Pokemon disponibilizado no request de sua Specie
     * */
    override suspend fun getPokemonEvolution(
        evolutionChainId: Int
    ): Flow<Resource<Chain>> = flow {
        try {
            emit(Resource.Loading)
            val pokemonEvolutions = pokemonServices.getPokemonEvolution(evolutionChainId).chain
            emit(Resource.Success(pokemonEvolutions.toChain()))
        } catch (e: Exception) {
            Log.e(REPOSITORY_ERROR_TAG, "getPokemonEvolution: ${e.stackTrace}")
            emit(Resource.Error())
        }
    }
}
