package com.example.mypokedex.presentation.pokemonDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.repository.PokemonRepository
import com.example.mypokedex.domain.useCases.PokemonUseCases
import com.example.mypokedex.presentation.pokemonDetails.state.PokemonDetailsState
import com.example.mypokedex.presentation.pokemonDetails.state.PokemonEvolutionsState
import com.example.mypokedex.presentation.pokemonDetails.state.PokemonFormsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val pokemonRepository: PokemonRepository,
    private val pokemonUseCases: PokemonUseCases
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

    fun getPokemonDetails(pokemonOrId: String) {
        viewModelScope.launch {
            pokemonUseCases.getPokemonDetails(pokemonOrId).collectLatest { result ->
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
                            _evolutionState.value = PokemonEvolutionsState.Success(data = evolutionChain)
                        }
                    }
                }
            }
        }
    }
}
