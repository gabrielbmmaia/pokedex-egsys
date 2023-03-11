package com.example.mypokedex.core

/**
 * Função para pegar o ID do pokemon a partir do final da URL disponibilizada pela API
 * */
fun String.getPokemonId(){
    this.split("/").last().removePrefix(".png")
}

/**
 * Função para deixar o numero com 3 casas sendo as iniciais "0"
 * */
fun String.formatToImageId(){
    this.padStart(3, '0')
}