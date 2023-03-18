package com.example.mypokedex.core.extensions

import com.example.mypokedex.core.Constantes.BASE_EVOLUTION_CHAIN_URL
import com.example.mypokedex.core.Constantes.BASE_POKEMON_SPECIE_URL
import com.example.mypokedex.core.Constantes.BASE_POKEMON_URL

/**
 * Utilizado para pegar o ID do pokemon a partir do final daa URLs
 * BASE_POKEMON_URL, BASE_EVOLUTION_CHAIN_URL, BASE_POKEMON_SPECIE_URL
 * disponibilizada pela API.
 * */
fun String.getPokemonId(): Int {
    val urlToParse =
        if (this.contains(BASE_POKEMON_URL))
            this.replace(BASE_POKEMON_URL, "")
        else if (this.contains(BASE_EVOLUTION_CHAIN_URL))
            this.replace(BASE_EVOLUTION_CHAIN_URL, "")
        else this.replace(BASE_POKEMON_SPECIE_URL, "")
    return urlToParse.replace("/", "").toInt()
}

/**
 * Apartir do id do Pokemon adiciona "0" até completar no minimo
 * 3 chars. Ex: 1 -> "001" , 10 -> "010", 100 -> "100"
 * */
fun Int.formatToPokemonNumber(): String =
    this.toString().padStart(3, '0')


/**
 * Utilizado para pegar a url da sprite do pokemon a partir de seu ID
 * */
fun getPokemonSprite(
    pokemonId: Int
): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"

/**
 * Formata o nome do pokemon removendo "-", numeros e deixa a primeira letra maiúscula
 * Ex: "charizard-mega-y" -> "Charizard Mega Y", "zygarde-50" -> "Zygarde"
 * */
fun String.formattPokemonName(): String =
    this.split("-")
        .filter { it.toIntOrNull() == null }
        .joinToString(" ") { it.replaceFirstChar { it.uppercase() } }

/**
 * Adiciona "No. " ao número do pokemon
 * */
fun getFormatedPokemonNumber(pokemonNumber: String): String =
    "No. $pokemonNumber"

/**
 * Formata altura do Pokemon para Metro
 * */
fun formatToMeters(pokemonAltura: Int): String {
    val altura = pokemonAltura / 10.0
    return "$altura m"
}

/**
 * Formata peso do Pokemon para Kg
 * */
fun formatToKg(pokemonPeso: Int): String {
    val peso = pokemonPeso / 10.0
    return "$peso kg"
}

/**
 * Pega o Id da EvolutionChain do pokemon a partir
 * da url BASE_EVOLUTION_CHAIN_URL
 * */
fun String.getEvolutionChainId(): Int {
    return this.replace(BASE_EVOLUTION_CHAIN_URL, "")
        .replace("/", "")
        .toInt()
}
