package com.example.mypokedex.core.extensions

import com.example.mypokedex.core.Constantes.BASE_POKEMON_URL
import com.example.mypokedex.domain.model.Pokemon

/**
 * Utilizado para pegar o ID do pokemon a partir do final da URL disponibilizada pela API
 * */
fun String.getPokemonId(): Int {
    return this.replace(BASE_POKEMON_URL, "")
        .replace("/", "")
        .toInt()
}

/**
 * Utilizado para deixar o numero com 3 casas sendo as iniciais "0"
 * */
fun formatToPokemonNumber(pokemonId: String): String =
    pokemonId.padStart(3, '0')


/**
 * Utilizado para pegar a url da imagem do pokemon apartir de seu ID
 * */
fun getPokemonSprite(
    pokemonId: Int
): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"

fun getFormatedPokemonNumber(pokemonNumber: String): String =
    "No. $pokemonNumber"

fun getPokemonImage(pokemonId: String): String =
    "https://assets.pokemon.com/assets/cms2/img/pokedex/full/$pokemonId.png"

fun formatToMeters(pokemonAltura: Int): String {
    val altura = pokemonAltura / 10.0
    return "$altura m"
}

fun formatToKg(pokemonPeso: Int): String {
    val peso = pokemonPeso /10.0
    return "$peso kg"
}