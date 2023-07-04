package com.pokemon.mypokedex.presentation.pokemonDetails

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokemon.mypokedex.core.Constantes
import com.pokemon.mypokedex.core.Resource
import com.pokemon.mypokedex.domain.repository.PokemonRepository
import com.pokemon.mypokedex.domain.useCases.PokemonUseCases
import com.pokemon.mypokedex.presentation.pokemonDetails.state.PokemonDetailsState
import com.pokemon.mypokedex.presentation.pokemonDetails.state.PokemonEvolutionsState
import com.pokemon.mypokedex.presentation.pokemonDetails.state.PokemonFormsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val pokemonRepository: PokemonRepository,
    private val pokemonUseCases: PokemonUseCases,
    private val preferences: SharedPreferences
) : ViewModel() {

    private val _detailsState: MutableStateFlow<PokemonDetailsState> =
        MutableStateFlow(PokemonDetailsState.Loading)
    val detailsState = _detailsState.asStateFlow()

    private val _evolutionState: MutableStateFlow<PokemonEvolutionsState> =
        MutableStateFlow(PokemonEvolutionsState.Empty)
    val evolutionState = _evolutionState.asStateFlow()

    private val _formsState: MutableStateFlow<PokemonFormsState> =
        MutableStateFlow(PokemonFormsState.Empty)
    val formsState = _formsState.asStateFlow()

    private val _lastPokemonId: MutableStateFlow<Int> =
        MutableStateFlow(Constantes.DEFAULT_LAST_POKEMON)
    val lastPokemonId = _lastPokemonId.asStateFlow()

    init{
        loadLastPokemonId()
    }

    fun getPokemonDetails(pokemonId: Int) {
        viewModelScope.launch {
            pokemonRepository.getPokemonDetails(pokemonId).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        result.message?.let {
                            _detailsState.value =
                                PokemonDetailsState.Error(message = it)
                        }
                    }

                    Resource.Loading -> {
                        _detailsState.value = PokemonDetailsState.Loading
                    }

                    is Resource.Success -> {
                        result.data?.let { pokemonDetails ->
                            _detailsState.value =
                                PokemonDetailsState.Success(data = pokemonDetails)
                            getPokemonForms(pokemonDetails.id)
                        }
                    }
                }
            }
        }
    }

    private fun loadLastPokemonId() {
        val lastPokemonId = preferences
            .getInt(Constantes.KEY_LAST_POKEMON_NUMBER, Constantes.DEFAULT_LAST_POKEMON)
        _lastPokemonId.value = lastPokemonId
    }

    private fun getPokemonForms(pokemonId: Int) {
        viewModelScope.launch {
            pokemonRepository.getPokemonForms(pokemonId).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _formsState.value = PokemonFormsState.Empty
                    }

                    Resource.Loading -> {
                        _formsState.value = PokemonFormsState.Empty
                    }

                    is Resource.Success -> {
                        result.data?.let { pokemonForms ->
                            _formsState.value = PokemonFormsState.Success(
                                data = pokemonUseCases.filterPokemonForms(pokemonForms)
                            )
                            getPokemonEvolutionChain(pokemonForms.evolutionChainId)
                        }
                    }
                }
            }
        }
    }

    private fun getPokemonEvolutionChain(evolutionChainId: Int) {
        viewModelScope.launch {
            pokemonRepository.getPokemonEvolution(evolutionChainId).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _evolutionState.value = PokemonEvolutionsState.Empty
                    }

                    Resource.Loading -> {
                        _evolutionState.value = PokemonEvolutionsState.Empty
                    }

                    is Resource.Success -> {
                        result.data?.let { evolutionChain ->
                            _evolutionState.value =
                                PokemonEvolutionsState.Success(data = evolutionChain)
                        }
                    }
                }
            }
        }
    }
}
