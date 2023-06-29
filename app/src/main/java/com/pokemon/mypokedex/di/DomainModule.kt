package com.pokemon.mypokedex.di

import com.pokemon.mypokedex.domain.useCases.PokemonUseCases
import com.pokemon.mypokedex.domain.useCases.pokemonUseCases.FilterPokemonFormsUseCase
import com.pokemon.mypokedex.domain.useCases.pokemonUseCases.GetPokemonListByTypeUseCase
import com.pokemon.mypokedex.domain.useCases.pokemonUseCases.PokemonValidationUseCase
import com.pokemon.mypokedex.domain.useCases.pokemonUseCases.SearchPokemonListUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    /**
     * Load do Módulos da camada de Domain
     * */
    fun load() {
        loadKoinModules(useCaseModule())
    }

    private fun useCaseModule(): Module = module {
        factory {
            PokemonUseCases(
                searchPokemonList = get(),
                getPokemonListByType = get(),
                filterPokemonForms = get(),
                pokemonValidation = get()
            )
        }
        factory { GetPokemonListByTypeUseCase(repository = get()) }
        factory { PokemonValidationUseCase() }
        factory { FilterPokemonFormsUseCase() }
        factory { SearchPokemonListUseCase(repository = get(), validation = get()) }
    }
}
