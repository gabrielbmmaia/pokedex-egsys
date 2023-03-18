package com.example.mypokedex.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mypokedex.R
import com.example.mypokedex.core.Constantes.POKEMON_FINAL_INDEX_LIST
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
import com.example.mypokedex.core.Constantes.TOOLBAR_TITLE
import com.example.mypokedex.core.extensions.visibilityGone
import com.example.mypokedex.core.extensions.visibilityVisible
import com.example.mypokedex.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<HomeViewModel>()
    private val args by navArgs<HomeFragmentArgs>()


    private lateinit var pokemonAdapter: PokemonAdapter
    private var rvIndexPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setToolbar()
        initRecyclerView()
        return binding.root
    }

    /**
     * Quando o Fragment entrar em OnPause será salvo o rvIndexPosition
     * do recyclerview para ao voltar o recyclerview estiver
     * na mesma posição ao sair do Fragment
     * */
    override fun onPause() {
        rvIndexPosition =
            (binding.rvHomefragment.layoutManager as
                    LinearLayoutManager).findFirstVisibleItemPosition()
        super.onPause()
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
        changePokemonTypeByArgs()
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
            toDetailsFragment(maxRandomNumber.toString())
        }
    }

    /**
     * Inicia o recyclerview e ao clicar em algum item é
     * enviado para PokemonDetailsFragment com o ID do
     * Pokemon clicado
     * */
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
                        with(binding.internetProblems) {
                            progressBar.visibilityGone()
                            errorMessage.visibilityGone()
                            retryButton.visibilityGone()
                        }
                        pokemonAdapter.setData(result.pokemonList)
                        binding.rvHomefragment.scrollToPosition(rvIndexPosition)
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

    /**
     * Popula o recyclerview para o tipo enviado via argumentos.
     * Caso seja nulo será populado com todos os Pokemon
     * */
    private fun changePokemonTypeByArgs() {
        if (args.pokemonType.isNullOrBlank()) {
            viewModel.loadPokemon()
        } else {
            viewModel.loadPokemon(pokemonType = args.pokemonType)
        }
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
