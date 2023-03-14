package com.example.mypokedex.presentation.home

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.mypokedex.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var pokemonAdapter: PokemonPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPokemonRecyclerView()
        initPokeballButton()
        initElementosButton()
    }
    
    private fun initElementosButton(){
        binding.elementosButton.setOnClickListener {
            
        }
    }

    private fun initPokeballButton() {
        binding.pokeballButton.setOnClickListener {
            
        }
    }

    private fun initPokemonRecyclerView() {
        // Configurando Adapter
        pokemonAdapter = PokemonPagingAdapter()
        val pokemonRV = binding.rvHomefragment
        with(pokemonRV) {
            hasFixedSize()
            adapter = pokemonAdapter.withLoadStateFooter(
                footer = LoadStateAdapter { pokemonAdapter.retry() }
            )
        }
        // Adicionando Pokemon
        lifecycleScope.launchWhenStarted {
            viewModel.pokemonList.collectLatest { result ->
                when (result) {
                    is PokemonListState.Data -> {
                        pokemonAdapter.submitData(result.pokemons)
                    }
                    else -> {}
                }
            }
        }
        // State Handling
        pokemonAdapter.addLoadStateListener { loadState ->
            with(binding) {
                rvHomefragment.isVisible =
                    loadState.source.refresh is LoadState.NotLoading
                internetProblems.retryButton.isVisible =
                    loadState.source.refresh is LoadState.Error
                internetProblems.progressBar.isVisible =
                    loadState.source.refresh is LoadState.Loading
                internetProblems.errorMessage.isVisible =
                    loadState.source.refresh is LoadState.Error

                internetProblems.retryButton.setOnClickListener {
                    pokemonAdapter.retry()
                }
            }
        }
    }
}
