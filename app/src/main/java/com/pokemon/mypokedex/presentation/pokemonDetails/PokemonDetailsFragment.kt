package com.pokemon.mypokedex.presentation.pokemonDetails

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.pokemon.mypokedex.core.Constantes
import com.pokemon.mypokedex.core.Constantes.DEFAULT_LAST_POKEMON
import com.pokemon.mypokedex.core.Constantes.KEY_LAST_POKEMON_NUMBER
import com.pokemon.mypokedex.core.Constantes.POKEMON_FINAL_INDEX_LIST
import com.pokemon.mypokedex.core.Constantes.POKEMON_START_INDEX_LIST
import com.pokemon.mypokedex.core.extensions.formatToKg
import com.pokemon.mypokedex.core.extensions.formatToMeters
import com.pokemon.mypokedex.core.extensions.getFormatedPokemonNumber
import com.pokemon.mypokedex.core.extensions.loadPokemonSpriteOrGif
import com.pokemon.mypokedex.core.extensions.toast
import com.pokemon.mypokedex.core.extensions.visibilityGone
import com.pokemon.mypokedex.core.extensions.visibilityInvisible
import com.pokemon.mypokedex.core.extensions.visibilityVisible
import com.pokemon.mypokedex.databinding.FragmentPokemonDetailsBinding
import com.pokemon.mypokedex.domain.model.PokemonMoves
import com.pokemon.mypokedex.domain.model.Sprites
import com.pokemon.mypokedex.presentation.pokemonDetails.adapters.PokemonAtaqueAdapter
import com.pokemon.mypokedex.presentation.pokemonDetails.adapters.PokemonSpriteAdapter
import com.pokemon.mypokedex.presentation.pokemonDetails.adapters.PokemonTipoAdapter
import com.pokemon.mypokedex.presentation.pokemonDetails.adapters.ViewPageAdapter
import com.pokemon.mypokedex.presentation.pokemonDetails.state.PokemonDetailsState
import com.pokemon.mypokedex.presentation.pokemonDetails.state.PokemonEvolutionsState
import com.pokemon.mypokedex.presentation.pokemonDetails.state.PokemonFormsState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class PokemonDetailsFragment : Fragment() {

    private var _binding: FragmentPokemonDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<PokemonDetailsFragmentArgs>()
    private val viewModel by viewModel<PokemonDetailsViewModel>()

    private var pokemonId: Int? = null

    private lateinit var tipoAdapter: PokemonTipoAdapter
    private lateinit var ataqueAdapter: PokemonAtaqueAdapter
    private lateinit var formasAdapter: PokemonSpriteAdapter
    private lateinit var viewpagerAdapter: ViewPageAdapter
    private lateinit var firstEvolutionAdapter: PokemonSpriteAdapter
    private lateinit var secondEvolutionAdapter: PokemonSpriteAdapter
    private lateinit var thirdEvolutionAdapter: PokemonSpriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pokemonId = args.pokemonId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        initRecyclerView()
        setToolbar()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populatePokemonDetails()
        initPokemonDetails()
        initPokemonEvolutionsList()
        initPokemonFormsList()
        initAdaptersOnItemClicked()
    }

    private fun setToolbar() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.detailsToolbar.setupWithNavController(navController, appBarConfiguration)
    }

    /**
     * Inicia todos recyclerviews
     * */
    private fun initRecyclerView() {
        tipoAdapter = PokemonTipoAdapter()
        ataqueAdapter = PokemonAtaqueAdapter(requireContext())
        formasAdapter = PokemonSpriteAdapter()
        viewpagerAdapter = ViewPageAdapter()
        firstEvolutionAdapter = PokemonSpriteAdapter()
        secondEvolutionAdapter = PokemonSpriteAdapter()
        thirdEvolutionAdapter = PokemonSpriteAdapter()
        with(binding) {
            rvPokemonType.adapter = tipoAdapter
            layoutPokemonAttack.rvPokemonAttack.adapter = ataqueAdapter
            layoutPokemonFormas.rvPokemonFormas.adapter = formasAdapter
            rvFirstEvolution.evolutionList.adapter = firstEvolutionAdapter
            rvSecondEvolution.evolutionList.adapter = secondEvolutionAdapter
            rvThirdEvolution.evolutionList.adapter = thirdEvolutionAdapter
        }
    }

    /**
     * Configura o ViewPager das imagens do Pokemon
     * */
    private fun viewPagerConfiguration(sprite: Sprites) {
        val viewpagerLayout = binding.viewpagerLayout
        val artworkList = listOf(sprite.artWorkDefault, sprite.artWorkShiny)
        viewpagerLayout.viewpager.apply {
            adapter = viewpagerAdapter
            offscreenPageLimit = artworkList.size
            (adapter as ViewPageAdapter).setData(artworkList)

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewpagerLayout.tabIndicator.apply {
                        selectTab(this.getTabAt(position))
                    }
                }
            })
        }
        TabLayoutMediator(
            viewpagerLayout.tabIndicator,
            viewpagerLayout.viewpager
        ) { _, _ -> }.attach()
    }

    private fun populatePokemonDetails() {
        pokemonId?.let { viewModel.getPokemonDetails(it) }
    }

    /**
     * Em caso do pokémon não ter evoluções não sera mostrado
     * as listas de evoluções.
     *
     * Primeiro é negado a visibilidade da lista de TERCEIRAS
     * evoluções e só ficará visivel caso a lista não seja vazia.
     * */
    private fun initPokemonEvolutionsList() {
        lifecycleScope.launchWhenStarted {
            viewModel.evolutionState.collectLatest { state ->
                when (state) {

                    PokemonEvolutionsState.Empty -> {
                        with(binding) {
                            containerPokemonEvolution.visibilityGone()
                            rvThirdEvolution.evolutionList.visibilityGone()
                            rvThirdEvolution.arrowDown.visibilityGone()
                        }
                    }

                    is PokemonEvolutionsState.Success -> {
                        val evolutions = state.data
                        if (!evolutions.thirdEvolutions.isNullOrEmpty()) {
                            with(binding) {
                                containerPokemonEvolution.visibilityVisible()
                                rvThirdEvolution.evolutionList.visibilityVisible()
                            }

                            firstEvolutionAdapter.setData(listOf(evolutions.firstEvolution))
                            secondEvolutionAdapter.setData(evolutions.secondEvolutions)
                            thirdEvolutionAdapter.setData(evolutions.thirdEvolutions)
                        } else if (evolutions.secondEvolutions.isNotEmpty()) {
                            with(binding) {
                                containerPokemonEvolution.visibilityVisible()
                                rvSecondEvolution.arrowDown.visibilityGone()
                            }

                            firstEvolutionAdapter.setData(listOf(evolutions.firstEvolution))
                            secondEvolutionAdapter.setData(evolutions.secondEvolutions)
                        } else if (!evolutions.thirdEvolutions.isNullOrEmpty()) {
                            binding.rvThirdEvolution.evolutionList.visibilityVisible()
                            thirdEvolutionAdapter.setData(evolutions.thirdEvolutions)
                        } else {
                            binding.containerPokemonEvolution.visibilityGone()
                        }
                    }
                }
            }
        }
    }

    private fun initPokemonDetails() {
        lifecycleScope.launchWhenStarted {
            viewModel.detailsState.collectLatest { state ->
                when (state) {

                    is PokemonDetailsState.Error -> {
                        requireActivity().toast(state.message)
                        binding.progressBar.visibilityGone()
                        toHomeFragment()
                    }

                    PokemonDetailsState.Loading -> {
                        with(binding) {
                            progressBar.visibilityVisible()
                            pokemonLayout.visibilityInvisible()
                        }
                    }

                    is PokemonDetailsState.Success -> {
                        val pokemon = state.data
                        pokemonId = pokemon.id
                        initPreviousOrNextPokemon(pokemon.id)

                        with(binding) {
                            progressBar.visibilityGone()
                            pokemonLayout.visibilityVisible()
                            pokemonName.text = pokemon.name
                            layoutPokemonNumber.pokemonNumber.text =
                                getFormatedPokemonNumber(pokemon.number)
                            with(layoutPokemonInfo) {
                                pokemonSpriteDefault.loadPokemonSpriteOrGif(
                                    pokemon.sprites,
                                    requireContext(),
                                    pokemonShiny = false
                                )
                                pokemonSpriteShiny.loadPokemonSpriteOrGif(
                                    pokemon.sprites,
                                    requireContext(),
                                    pokemonShiny = true
                                )
                                pokemonAltura.text = formatToMeters(pokemon.height)
                                pokemonPeso.text = formatToKg(pokemon.weight)
                            }
                        }
                        tipoAdapter.setData(pokemon.types)
                        viewPagerConfiguration(pokemon.sprites)
                        populateRvPokemonAttacks(pokemon.moves)
                    }
                }
            }
        }
    }

    private fun initPokemonFormsList() {
        lifecycleScope.launchWhenStarted {
            viewModel.formsState.collectLatest { state ->
                when (state) {
                    PokemonFormsState.Empty -> {
                        binding.containerPokemonFormas.visibilityGone()
                    }

                    is PokemonFormsState.Success -> {
                        if (state.data.isNotEmpty()) {
                            with(binding) {
                                containerPokemonFormas.visibilityVisible()
                                layoutPokemonFormas.rvPokemonFormas.visibilityVisible()
                            }
                            formasAdapter.setData(state.data)
                        } else binding.containerPokemonFormas.visibilityGone()
                    }
                }
            }
        }
    }

    /**
     * Popula o recyclewview de Ataques e em caso de não existir nenhum,
     * é removido o display do container_pokemon_attack
     * */
    private fun populateRvPokemonAttacks(pokemonAttacks: List<PokemonMoves>) {
        if (pokemonAttacks.isNotEmpty()) {
            binding.containerPokemonAttack.visibilityVisible()
            ataqueAdapter.setData(pokemonAttacks)
        } else binding.containerPokemonAttack.visibilityGone()
    }

    private fun toHomeFragment() {
        findNavController().popBackStack()
    }

    private fun toFormFragment(pokemonId: Int) {
        val action = PokemonDetailsFragmentDirections
            .actionPokemonDetailsFragmentToPokemonFormFragment(pokemonId)
        findNavController().navigate(action)
    }

    /**
     * Altera o PokemonDetails em display para o Pokemon Anterior ou Posterior
     * dependendo de qual seta for clicada e em caso de ser o primeiro Pokemon(1)
     * é removido o display da seta para esquerda e em caso de ser o último
     * Pokemon(1010) é removido o display da seta para direita
     * */
    private fun initPreviousOrNextPokemon(pokemonId: Int) {
        lifecycleScope.launchWhenStarted {
            val arrowRight = binding.layoutPokemonNumber.arrowRight
            val arrowLeft = binding.layoutPokemonNumber.arrowLeft
            viewModel.lastPokemonId.collectLatest { lastPokemonId ->
                if (pokemonId > POKEMON_START_INDEX_LIST) {
                    arrowLeft.visibilityVisible()
                    arrowLeft.setOnClickListener {
                        viewModel.getPokemonDetails(pokemonId.minus(1))
                    }
                } else arrowLeft.visibilityGone()
                if (pokemonId < lastPokemonId) {
                    arrowRight.visibilityVisible()
                    arrowRight.setOnClickListener {
                        viewModel.getPokemonDetails(pokemonId.plus(1))
                    }
                } else arrowRight.visibilityGone()
            }
        }
    }

    /**
     * Inicia todas funcionalidades de cliques dos
     * recyclerviews enviando o ID do pokemon clicado
     * para a viewmodel e em caso de ser uma Forma do pokemon
     * será removido o display de container_pokemon_number,
     * container_pokemon_formas e container_pokemon_evolution
     * */
    private fun initAdaptersOnItemClicked() {
        firstEvolutionAdapter.onItemClicked = { pokemonId ->
            binding.scrollView.scrollTo(0, 0)
            viewModel.getPokemonDetails(pokemonId)
        }
        secondEvolutionAdapter.onItemClicked = { pokemonId ->
            binding.scrollView.scrollTo(0, 0)
            viewModel.getPokemonDetails(pokemonId)
        }
        thirdEvolutionAdapter.onItemClicked = { pokemonId ->
            binding.scrollView.scrollTo(0, 0)
            viewModel.getPokemonDetails(pokemonId)
        }
        formasAdapter.onItemClicked = { pokemonId ->
            toFormFragment(pokemonId)
        }
    }
}
