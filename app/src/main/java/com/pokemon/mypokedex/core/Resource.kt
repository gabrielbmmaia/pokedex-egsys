package com.pokemon.mypokedex.core

/**
 * Classe para tratamento utlizado nos UseCases
 * */
sealed class Resource<out T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String? = null, data: T? = null) : Resource<T>(data, message)
    object Loading : Resource<Nothing>()
}