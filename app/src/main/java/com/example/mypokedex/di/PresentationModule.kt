package com.example.mypokedex.di

import com.example.mypokedex.presentation.home.HomeViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule {

    /**
     * Load de Modulos da camada de presentation
     * */
    fun load() {
        loadKoinModules(viewModelModule())
    }

    private fun viewModelModule(): Module {
        return module {
            factory { HomeViewModel(pokemonUseCases = get()) }
        }
    }
}
