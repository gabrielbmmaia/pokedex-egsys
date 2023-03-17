package com.example.mypokedex.data.repository

import com.example.mypokedex.data.networking.PokemonServices
import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.model.PokemonDetails
import com.example.mypokedex.domain.model.pokemonEvolution.Chain
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

    override suspend fun getPokemonEvolution(evolutionChainId: Int): Chain =
        pokemonServices.getPokemonEvolution(evolutionChainId).chain

}
