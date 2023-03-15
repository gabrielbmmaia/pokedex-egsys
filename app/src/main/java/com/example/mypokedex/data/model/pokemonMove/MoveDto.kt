package com.example.mypokedex.data.model.pokemonMove

import com.example.mypokedex.domain.model.pokemonMove.Move

data class MoveDto(
    val name: String?,
    val url: String?
) {
    fun toMove(): Move =
        Move(
            name = name,
            url = url
        )
}