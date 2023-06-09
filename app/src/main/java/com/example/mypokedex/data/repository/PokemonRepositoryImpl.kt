package com.example.mypokedex.data.repository

import android.util.Log
import com.example.mypokedex.core.Constantes.POKEMON_NAO_ENCONTRADO
import com.example.mypokedex.core.Constantes.REPOSITORY_ERROR_TAG
import com.example.mypokedex.core.Resource
import com.example.mypokedex.data.mappers.toChain
import com.example.mypokedex.data.mappers.toPokemon
import com.example.mypokedex.data.mappers.toPokemonDetails
import com.example.mypokedex.data.mappers.toPokemonForms
import com.example.mypokedex.data.networking.PokemonServices
import com.example.mypokedex.domain.model.Chain
import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.model.PokemonDetails
import com.example.mypokedex.domain.model.pokemonForms.PokemonForms
import com.example.mypokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokemonRepositoryImpl(
    private val pokemonServices: PokemonServices
) : PokemonRepository {

    /**
     * Pega lista dos Pokemon com IDs entre 1 à 1010.
     * Apartira do ID 10000 a api considera como uma Forma
     * ALTERNATIVA do pokemon
     * */
    override suspend fun getPokemonList(): List<Pokemon> =
        pokemonServices.getPokemonList().results.map { it.toPokemon() }

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
        pokemonOrId: String
    ): Flow<Resource<PokemonDetails>> = flow {
        try {
            emit(Resource.Loading)
            val pokemonDetail = pokemonServices.getPokemonDetail(pokemonOrId)
            emit(Resource.Success(pokemonDetail.toPokemonDetails()))
        } catch (e: Exception) {
            Log.e(REPOSITORY_ERROR_TAG, "getPokemonDetails: ${e.stackTrace}")
            emit(Resource.Error(message = POKEMON_NAO_ENCONTRADO))
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
