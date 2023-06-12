package com.example.mypokedex.presentation.pokemonForm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PokemonFormViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _detailsState: MutableStateFlow<FormDetailsState> =
        MutableStateFlow(FormDetailsState.Loading)
    val detailsState = _detailsState.asStateFlow()

    fun getPokemonDetails(pokemonId: Int) {
        viewModelScope.launch {
            repository.getPokemonDetails(pokemonId).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        result.message?.let {
                            _detailsState.value =
                                FormDetailsState.Error(message = it)
                        }
                    }

                    Resource.Loading -> {
                        _detailsState.value = FormDetailsState.Loading
                    }

                    is Resource.Success -> {
                        result.data?.let { pokemonDetails ->
                            _detailsState.value = FormDetailsState.Success(data = pokemonDetails)
                        }
                    }
                }
            }
        }
    }
}