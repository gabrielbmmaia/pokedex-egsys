package com.example.mypokedex.data.repository

import android.util.Log
import com.example.mypokedex.data.networking.PokemonServices
import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.model.PokemonDetails
import com.example.mypokedex.domain.model.pokemonEvolution.Chain
import com.example.mypokedex.domain.model.pokemonEvolution.EvolutionRequest
import com.example.mypokedex.domain.model.pokemonForms.PokemonSpecie
import com.example.mypokedex.domain.repository.PokemonRepository

class PokemonRepositoryImpl(
    private val pokemonServices: PokemonServices
) : PokemonRepository {

    override suspend fun getPokemonList(): List<Pokemon> =
        pokemonServices.getPokemonList().results.map { it.toPokemon() }

    override suspend fun getPokemonByType(pokemonType: String): List<Pokemon> {
        val serviceReturn = pokemonServices.getPokemonFromType(pokemonType)
        val pokemonList = serviceReturn.results.map { it.results }

        return pokemonList.map { it.toPokemon() }
    }

    override suspend fun getPokemonDetails(pokemonOrId: String): PokemonDetails =
        pokemonServices.getPokemonDetail(pokemonOrId).toPokemonDetails()

    override suspend fun getPokemonSpecie(pokemonId: Int): PokemonSpecie =
        pokemonServices.getPokemonSpecie(pokemonId).toPokemonSpecie()

    override suspend fun getPokemonEvolution(evolutionChainId: Int): EvolutionRequest {
        val oi = pokemonServices.getPokemonEvolution(evolutionChainId)
        Log.e("teste", " oioioi $oi")
        val salame = oi.toEvolutionResquest()
        Log.e("teste", " salame salame $salame")
        return  salame
    }


}
