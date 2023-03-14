package com.example.mypokedex.core

object Constantes {

    const val HOME_FRAGMENT_TITLE = ""

    // Constantes da api
    const val PAGING_LIMIT = "1281"
    const val BASE_URL = "https://pokeapi.co/api/v2/"
    const val BASE_POKEMON_URL = "https://pokeapi.co/api/v2/pokemon/"
    const val OK_HTTP = "ok_http"
    const val INITIAL_PAGE = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=$PAGING_LIMIT"

    // Constantes do PagingSource
    const val PAGINGSOURCE_TAG = "paging_source"

    // Constantes UseCase
    const val USE_CASE = "use_case"
    const val POKEMON_LIST_ERROR_MESSAGE = "Error ao carregar a lista de Pokemon"
}
