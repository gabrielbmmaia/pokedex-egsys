package com.example.mypokedex.data.model.pokemonMove

import com.example.mypokedex.domain.model.pokemonMove.MoveDetails
import com.google.gson.annotations.SerializedName

data class MoveDetailsDto(
    @SerializedName("level_learned_at")
    val levelLearned: Int?,
    @SerializedName("move_learn_method")
    val learnMethod: MoveLearnMethodDto
) {
    fun toMoveDetails(): MoveDetails =
        MoveDetails(
            levelLearned = levelLearned,
            learnMethod = learnMethod.toMoveLearnMethod()
        )
}