package com.example.mypokedex.data.model.pokemonMove

import com.example.mypokedex.domain.model.pokemonMove.MoveLearnMethod

data class MoveLearnMethodDto(
    val name: String?,
    val url: String?
) {
    fun toMoveLearnMethod(): MoveLearnMethod =
        MoveLearnMethod(
            name = name,
            url = url
        )
}