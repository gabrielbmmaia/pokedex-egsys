package com.example.mypokedex.presentation.home

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypokedex.R
import com.example.mypokedex.core.Constantes.HOME_FRAGMENT_TITLE
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_ACO
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_AGUA
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_DRAGAO
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_ELETRICO
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_FADA
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_FANTASMA
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_FOGO
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_GELO
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_INSETO
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_LUTADOR
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_NORMAL
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_PEDRA
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_PLANTA
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_PSIQUICO
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_SOMBRIO
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_TERRESTRE
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_VENENOSO
import com.example.mypokedex.core.Constantes.POKEMON_TIPO_VOADOR
import com.example.mypokedex.core.extensions.visibilityGone
import com.example.mypokedex.core.extensions.visibilityVisible
import com.example.mypokedex.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setToolbar()
        initRecyclerView()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateRecyclerView()
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
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        searchView.clearFocus()
                        query?.let { toDetailsFragment(it) }
                        return false
                    }

                    override fun onQueryTextChange(p0: String?): Boolean = false
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_popup_todos -> {
                        viewModel.loadPokemon()
                    }
                    R.id.menu_popup_elemento_aco -> {
                        viewModel.loadPokemon(pokemonType = POKEMON_TIPO_ACO)
                    }
                    R.id.menu_popup_elemento_agua -> {
                        viewModel.loadPokemon(pokemonType = POKEMON_TIPO_AGUA)
                    }
                    R.id.menu_popup_elemento_dragao -> {
                        viewModel.loadPokemon(pokemonType = POKEMON_TIPO_DRAGAO)
                    }
                    R.id.menu_popup_elemento_eletrico -> {
                        viewModel.loadPokemon(pokemonType = POKEMON_TIPO_ELETRICO)
                    }
                    R.id.menu_popup_elemento_fada -> {
                        viewModel.loadPokemon(pokemonType = POKEMON_TIPO_FADA)
                    }
                    R.id.menu_popup_elemento_fantasma -> {
                        viewModel.loadPokemon(pokemonType = POKEMON_TIPO_FANTASMA)
                    }
                    R.id.menu_popup_elemento_fogo -> {
                        viewModel.loadPokemon(pokemonType = POKEMON_TIPO_FOGO)
                    }
                    R.id.menu_popup_elemento_gelo -> {
                        viewModel.loadPokemon(pokemonType = POKEMON_TIPO_GELO)
                    }
                    R.id.menu_popup_elemento_inseto -> {
                        viewModel.loadPokemon(pokemonType = POKEMON_TIPO_INSETO)
                    }
                    R.id.menu_popup_elemento_lutador -> {
                        viewModel.loadPokemon(pokemonType = POKEMON_TIPO_LUTADOR)
                    }
                    R.id.menu_popup_elemento_normal -> {
                        viewModel.loadPokemon(pokemonType = POKEMON_TIPO_NORMAL)
                    }
                    R.id.menu_popup_elemento_pedra -> {
                        viewModel.loadPokemon(pokemonType = POKEMON_TIPO_PEDRA)
                    }
                    R.id.menu_popup_elemento_planta -> {
                        viewModel.loadPokemon(pokemonType = POKEMON_TIPO_PLANTA)
                    }
                    R.id.menu_popup_elemento_psiquico -> {
                        viewModel.loadPokemon(pokemonType = POKEMON_TIPO_PSIQUICO)
                    }
                    R.id.menu_popup_elemento_sombrio -> {
                        viewModel.loadPokemon(pokemonType = POKEMON_TIPO_SOMBRIO)
                    }
                    R.id.menu_popup_elemento_terrestre -> {
                        viewModel.loadPokemon(pokemonType = POKEMON_TIPO_TERRESTRE)
                    }
                    R.id.menu_popup_elemento_venenoso -> {
                        viewModel.loadPokemon(pokemonType = POKEMON_TIPO_VENENOSO)
                    }
                    R.id.menu_popup_elemento_voador -> {
                        viewModel.loadPokemon(pokemonType = POKEMON_TIPO_VOADOR)
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
            toDetailsFragment("25")
        }
    }

    private fun initRecyclerView() {
        pokemonAdapter = PokemonAdapter()
        with(binding.rvHomefragment) {
            adapter = pokemonAdapter
            hasFixedSize()
        }
        pokemonAdapter.onItemClicked = {
            toDetailsFragment(pokemonOrId = it.id.toString())
        }
    }

    private fun populateRecyclerView() {
        lifecycleScope.launchWhenStarted {
            viewModel.pokemonList.collectLatest { result ->
                when (result) {
                    is PokemonListState.Data -> {
                        with(binding.internetProblems) {
                            progressBar.visibilityGone()
                            errorMessage.visibilityGone()
                            retryButton.visibilityGone()
                        }
                        pokemonAdapter.setData(result.pokemonList)
                        binding.rvHomefragment.scrollToPosition(0)
                    }
                    is PokemonListState.Error -> {
                        with(binding.internetProblems) {
                            errorMessage.visibilityVisible()
                            retryButton.visibilityVisible()
                            progressBar.visibilityGone()
                            errorMessage.text = result.message

                            retryButton.setOnClickListener {
                                lifecycleScope.launchWhenStarted {
                                    viewModel.loadPokemon()
                                }
                            }
                        }
                    }
                    PokemonListState.Loading -> {
                        with(binding.internetProblems) {
                            errorMessage.visibilityGone()
                            retryButton.visibilityGone()
                            progressBar.visibilityVisible()
                        }
                    }
                }
            }
        }
    }

    private fun toDetailsFragment(pokemonOrId: String) {
        val action = HomeFragmentDirections.homeFragmentToPokemonDetailsFragment(pokemonOrId)
        findNavController().navigate(action)
    }


}
