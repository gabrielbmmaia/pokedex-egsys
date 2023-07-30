package com.pokemon.mypokedex.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pokemon.mypokedex.R
import com.pokemon.mypokedex.core.Constantes.POKEMON_FINAL_INDEX_LIST
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_ACO
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_AGUA
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_DRAGAO
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_ELETRICO
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_FADA
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_FANTASMA
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_FOGO
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_GELO
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_INSETO
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_LUTADOR
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_NORMAL
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_PEDRA
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_PLANTA
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_PSIQUICO
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_SOMBRIO
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_TERRESTRE
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_VENENOSO
import com.pokemon.mypokedex.core.Constantes.POKEMON_TIPO_VOADOR
import com.pokemon.mypokedex.core.Constantes.TOOLBAR_TITLE
import com.pokemon.mypokedex.core.extensions.visibilityGone
import com.pokemon.mypokedex.core.extensions.visibilityVisible
import com.pokemon.mypokedex.databinding.FragmentHomeBinding
import com.pokemon.mypokedex.domain.model.Pokemon
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

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

    override fun onPause() {
        super.onPause()
        viewModel.loadPokemon()
    }

    /**
     * Ao dar submit no SearchView é enviado a informação para
     * PokemonDetailsFragment e ao selecionar qualquer tipo de Pokemon
     * a lista é trocada para Pokemons do tipo escolhido
     * */
    private fun setMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)

                val searchItem = menu.findItem(R.id.menu_search_icon)
                val searchView = searchItem.actionView as SearchView
                searchView.queryHint = getString(R.string.search_pokemon)

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean = false

                    override fun onQueryTextChange(query: String?): Boolean {
                        lifecycleScope.launch {
                            delay(500)
                            query?.let {
                                viewModel.searchPokemon(it)
                            }
                        }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                showPokemonListBySelectedType(menuItem)
                return true
            }
        }, viewLifecycleOwner)
    }

    private fun setToolbar() {
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(binding.homeToolbar)
        activity.title = TOOLBAR_TITLE
    }

    /**
     * Sorteia um pokemon dentre todos da lista e envia o ID
     * para PokemonDetailsFragment
     * */
    private fun initPokeballButton() {
        binding.pokeballButton.setOnClickListener {
            val maxRandomNumber = Random.nextInt(POKEMON_FINAL_INDEX_LIST.plus(1))
            toDetailsFragment(maxRandomNumber)
        }
    }

    /**
     * Inicia o recyclerview e ao clicar em algum item é
     * enviado para PokemonDetailsFragment com o ID do
     * Pokemon clicado
     * */
    private fun initRecyclerView(pokemonList: List<Pokemon> = emptyList()) {
        pokemonAdapter = PokemonAdapter(pokemonList)
        with(binding.rvHomefragment) {
            adapter = pokemonAdapter
            hasFixedSize()
        }
        pokemonAdapter.onItemClicked = {
            toDetailsFragment(pokemonId = it.id)
        }
    }

    /**
     * Coleta os dados da ViewModel
     * Em caso de sucesso: é colocado em display a lista de Pokemon e o
     * recyclervyew é scrollado para posição 0 em casos de listas diferentes
     * serem coletadas, como as listas de Pokemon por Tipo.
     * Em caso de Loading: é colocado em display uma progressbar e escodido o resto
     * do conteúdo.
     * Em caso de Erro: é mostrado mensagem de erro e um botão para tentar novamente
     * a requisição de dados
     * */
    private fun populateRecyclerView() {
        lifecycleScope.launchWhenStarted {
            viewModel.pokemonList.collectLatest { result ->
                when (result) {
                    is PokemonListState.Data -> {
                        binding.rvHomefragment.visibilityVisible()
                        with(binding.internetProblems) {
                            progressBar.visibilityGone()
                            errorMessage.visibilityGone()
                            retryButton.visibilityGone()
                        }
                        if (result.pokemonList.isEmpty()) {
                            binding.rvHomefragment.visibilityGone()
                            binding.emptyPokemonText.visibilityVisible()
                        } else {
                            initRecyclerView(result.pokemonList)
                            binding.emptyPokemonText.visibilityGone()
                            binding.rvHomefragment.visibilityVisible()
                        }
                    }

                    is PokemonListState.Error -> {
                        binding.rvHomefragment.visibilityGone()
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
                        binding.rvHomefragment.visibilityGone()
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

    private fun toDetailsFragment(pokemonId: Int) {
        val action = HomeFragmentDirections.homeFragmentToPokemonDetailsFragment(pokemonId)
        findNavController().navigate(action)
    }

    /**
     * A partir do ID do menu selecionado é enviado uma resposta diferente
     * para a ViewModel
     * */
    private fun showPokemonListBySelectedType(menuItem: MenuItem) {
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
    }
}
