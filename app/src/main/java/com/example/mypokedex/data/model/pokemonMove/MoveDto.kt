package com.example.mypokedex.data.model.pokemonMove

import com.example.mypokedex.domain.model.pokemonMove.Move

data class MoveDto(
    val name: String?
) {
    fun toMove(): Move =
        Move(name = name?.replaceFirstChar { it.uppercaseChar() })
}