package com.pokemon.mypokedex.domain.useCases.pokemonUseCases

/**
 * É feito uma validação da string inserida no seu parâmetro.
 * Primeiramente é transformado em Int para em caso de ser o
 * número do pokemon ser removido todos os "0" iniciais
 * Ex: "0000001" -> 1, "0010" -> 10.
 * Em caso de não ser um número será tratado a Exception
 * para que a string seja formatada para todas as casas em
 * minúsculo e removendo espaços finais
 * Ex: " bUlbaSaUR  " -> "bulbasaur", "deoxys attack" -> "deoxys-attack"
 * atendendo formato de pesquisa de pokemon da API
 * */
class PokemonValidationUseCase {
    operator fun invoke(pokemonOrId: String): String =
        try {
            pokemonOrId.toInt().toString()
        } catch (e: Exception) {
            pokemonOrId.trim().lowercase().replace(" ", "-")
        }
}
