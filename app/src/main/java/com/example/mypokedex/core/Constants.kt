package com.example.mypokedex.core

// Constantes da api
const val PAGING_LIMIT = "40"
const val BASE_URL = "https://pokeapi.co/api/v2/"
const val OK_HTTP = "ok_http"
const val INITIAL_PAGE = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=$PAGING_LIMIT"

// Constantes do PagingSource
const val PAGINGSOURCE_TAG = "paging_source"