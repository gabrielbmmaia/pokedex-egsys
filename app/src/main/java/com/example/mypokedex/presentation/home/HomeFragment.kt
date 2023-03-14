package com.example.mypokedex.presentation.home

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.mypokedex.R
import com.example.mypokedex.core.Constantes.HOME_FRAGMENT_TITLE
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
        setToolbar()
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
        setMenu()
    }

    private fun setMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)

                val searchItem = menu.findItem(R.id.menu_search_icon)
                val searchView = searchItem.actionView as SearchView
                searchView.queryHint = getString(R.string.search_pokemon)

                searchView.setOnQueryTextListener(object : OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        searchView.clearFocus()
                        return false
                    }

                    override fun onQueryTextChange(p0: String?): Boolean = false
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                when (menuItem.itemId) {
                    R.id.menu_popup_elemento_aco -> {
                        Toast.makeText(requireContext(), "jesus", Toast.LENGTH_SHORT).show()
                    }
                }

                return true
            }
        }, viewLifecycleOwner)
    }

    private fun setToolbar() {
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(binding.homeToolbar)
        activity.title = HOME_FRAGMENT_TITLE
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
