package com.example.mypokedex.presentation.pokemonDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypokedex.core.Resource
import com.example.mypokedex.domain.model.Pokemon
import com.example.mypokedex.domain.model.pokemonEvolution.Chain
import com.example.mypokedex.domain.model.pokemonEvolution.Species
import com.example.mypokedex.domain.model.pokemonForms.PokemonSpecie
import com.example.mypokedex.domain.model.pokemonMove.PokemonMoves
import com.example.mypokedex.domain.useCases.PokemonUseCases
import com.example.mypokedex.presentation.pokemonDetails.state.PokemonDetailsState
import com.example.mypokedex.presentation.pokemonDetails.state.PokemonState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PokemonDetailsViewModel(
    private val pokemonUseCases: PokemonUseCases
) : ViewModel() {

    private val _pokemonDetails = MutableStateFlow<PokemonDetailsState>(PokemonDetailsState.Loading)
    val pokemonDetails: StateFlow<PokemonDetailsState> get() = _pokemonDetails

    private val _pokemonFormas = MutableStateFlow<PokemonState<Pokemon>>(PokemonState.Loading)
    val pokemonFormas: StateFlow<PokemonState<Pokemon>> get() = _pokemonFormas

    private val _firstEvolution = MutableStateFlow<PokemonState<Species>>(PokemonState.Empty)
    private val firstEvolution: StateFlow<PokemonState<Species>> get() = _firstEvolution

    private val _secondEvolution = MutableStateFlow<PokemonState<Species>>(PokemonState.Empty)
    private val secondEvolution: StateFlow<PokemonState<Species>> get() = _secondEvolution

    private val _thirdEvolution = MutableStateFlow<PokemonState<Species>>(PokemonState.Empty)
    private val thirdEvolution: StateFlow<PokemonState<Species>> get() = _thirdEvolution

    fun getPokemonDetails(pokemonOrId: String) {
        viewModelScope.launch {
            pokemonUseCases.getPokemonDetails(pokemonOrId).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        result.message?.let { message ->
                            _pokemonDetails.value = PokemonDetailsState.Error(message)
                        }
                    }
                    Resource.Loading -> {
                        _pokemonDetails.value = PokemonDetailsState.Loading
                    }
                    is Resource.Success -> {
                        result.data?.let { pokemonDetails ->
                            _pokemonDetails.value = PokemonDetailsState.Data(pokemonDetails)
                            getPokemonSpecie(pokemonDetails.id)
                        }
                    }
                }
            }
        }
    }

    fun filterPokemonMoves(pokemonMoves: List<PokemonMoves>): List<PokemonMoves> {
        return pokemonUseCases.filterToLeanableAttacks(pokemonMoves)

    }


    private fun getPokemonSpecie(pokemonId: Int) {
        viewModelScope.launch {
            pokemonUseCases.getPokemonSpecie(pokemonId).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        result.message?.let { message ->
                            _pokemonFormas.value =
                                PokemonState.Error(message)
                        }
                    }
                    Resource.Loading -> {
                        _pokemonFormas.value =
                            PokemonState.Loading
                    }
                    is Resource.Success -> {
                        result.data?.let { pokemonSpecie ->
                            getPokemonFormas(pokemonSpecie)
                            getPokemonEvolutionChain(pokemonSpecie.evolutionChain.id)
                        }
                    }
                }
            }
        }
    }

    private fun getPokemonFormas(pokemonSpecie: PokemonSpecie) {
        viewModelScope.launch {
            pokemonUseCases.getPokemonFormas(pokemonSpecie).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { pokemonList ->
                            _pokemonFormas.value = PokemonState.Data(pokemonList)
                        }
                    }
                    else -> {
                        _pokemonFormas.value = PokemonState.Empty
                    }
                }
            }
        }
    }

    private fun getPokemonEvolutionChain(evolutionChainId: Int) {
        viewModelScope.launch {
            pokemonUseCases.getPokemonEvolution(evolutionChainId).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { pokemonEvolutionChain ->
                            getPokemonFirstEvolutions(pokemonEvolutionChain)
                            getPokemonSecondEvolutions(pokemonEvolutionChain)
                            getPokemonThirdEvolutions(pokemonEvolutionChain)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getPokemonFirstEvolutions(chain: Chain) {
        viewModelScope.launch {
            pokemonUseCases.getPokemonFirstEvolution(chain).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { pokemon ->
                            _firstEvolution.value = PokemonState.Data(pokemon)
                        }
                    }
                    else -> {
                        _firstEvolution.value = PokemonState.Empty
                    }
                }
            }
        }
    }

    private fun getPokemonSecondEvolutions(chain: Chain) {
        viewModelScope.launch {
            pokemonUseCases.getPokemonSecondEvolution(chain).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { pokemon ->
                            _secondEvolution.value = PokemonState.Data(pokemon)
                        }
                    }
                    else -> {
                        _secondEvolution.value = PokemonState.Empty
                    }
                }
            }
        }
    }

    private fun getPokemonThirdEvolutions(chain: Chain) {
        viewModelScope.launch {
            pokemonUseCases.getPokemonThirdEvolution(chain).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { pokemon ->
                            _thirdEvolution.value = PokemonState.Data(pokemon)
                        }
                    }
                    else -> {
                        _thirdEvolution.value = PokemonState.Empty
                    }
                }
            }
        }
    }
}