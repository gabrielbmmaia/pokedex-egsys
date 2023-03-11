package com.example.mypokedex.di

import com.example.mypokedex.domain.useCases.GetPokemonListUseCase
import com.example.mypokedex.domain.useCases.PokemonUseCases
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

    private fun useCaseModule(): Module {
        return module {
            factory { PokemonUseCases(getPokemonList = get()) }
            factory { GetPokemonListUseCase(repository = get()) }
        }
    }
}
