package com.example.mypokedex.data.model.pokemonMove

import com.google.gson.annotations.SerializedName

data class MoveDetailsDto(
    @SerializedName("level_learned_at")
    val levelLearned: Int?
)