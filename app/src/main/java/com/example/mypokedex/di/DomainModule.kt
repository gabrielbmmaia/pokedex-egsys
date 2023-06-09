package com.example.mypokedex.di

import com.example.mypokedex.domain.useCases.PokemonUseCases
import com.example.mypokedex.domain.useCases.pokemonUseCases.*
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
                getPokemonList = get(),
                getPokemonListByType = get(),
                getPokemonDetails = get(),
                filterPokemonForms = get()
            )
        }
        factory { GetPokemonListUseCase(repository = get()) }
        factory { GetPokemonListByTypeUseCase(repository = get()) }
        factory { GetPokemonDetailsUseCase(repository = get(), pokemonValidation = get()) }
        factory { PokemonValidationUseCase() }
        factory { FilterPokemonFormsUseCase() }
    }
}
