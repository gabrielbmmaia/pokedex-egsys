package com.example.mypokedex.data.model.pokemonMove

import com.example.mypokedex.domain.model.pokemonMove.MoveLearnMethod
import com.google.gson.annotations.SerializedName

data class MoveLearnMethodDto(
    @SerializedName("name")
    val method: String?
) {
    fun toMoveLearnMethod(): MoveLearnMethod =
        MoveLearnMethod(method = method)
}