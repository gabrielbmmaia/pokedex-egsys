package com.example.mypokedex.data.repository

import com.example.mypokedex.data.local.PokemonDao
import com.example.mypokedex.data.networking.PokemonServices
import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.model.PokemonDetails
import com.example.mypokedex.domain.model.pokemonEvolution.Chain
import com.example.mypokedex.domain.model.pokemonForms.PokemonSpecie
import com.example.mypokedex.domain.repository.PokemonRepository

class PokemonRepositoryImpl(
    private val pokemonServices: PokemonServices,
    private val pokemonDao: PokemonDao
) : PokemonRepository {

    /**
     * Pega lista dos Pokemon com IDs entre 1 à 1010.
     * Apartira do ID 10000 a api considera como uma Forma
     * ALTERNATIVA do pokemon
     * */
    override suspend fun getPokemonList(): List<Pokemon> {
        val request = pokemonServices.getPokemonList().results
        pokemonDao.addPokemonList(request)

        return pokemonDao.getPokemonList().map { it.toPokemon() }
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
    override suspend fun getPokemonDetails(pokemonOrId: String): PokemonDetails =
        pokemonServices.getPokemonDetail(pokemonOrId).toPokemonDetails()

    /**
     * Pega a Specie do Pokemon a partir do seu ID
     * */
    override suspend fun getPokemonSpecie(pokemonId: Int): PokemonSpecie =
        pokemonServices.getPokemonSpecie(pokemonId).toPokemonSpecie()

    /**
     * Pega a cadeia de evoluções do Pokemon a partir do evolutionChainID
     * do Pokemon disponibilizado no request de sua Specie
     * */
    override suspend fun getPokemonEvolution(evolutionChainId: Int): Chain =
        pokemonServices.getPokemonEvolution(evolutionChainId).chain.toChain()
}
