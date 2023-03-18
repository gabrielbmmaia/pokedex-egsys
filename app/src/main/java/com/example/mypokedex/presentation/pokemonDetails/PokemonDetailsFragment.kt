package com.example.mypokedex.presentation.pokemonDetails

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
import com.example.mypokedex.R
import com.example.mypokedex.core.Constantes.POKEMON_FINAL_INDEX_LIST
import com.example.mypokedex.core.Constantes.POKEMON_START_INDEX_LIST
import com.example.mypokedex.core.extensions.*
import com.example.mypokedex.databinding.FragmentPokemonDetailsBinding
import com.example.mypokedex.domain.model.pokemonMove.PokemonMoves
import com.example.mypokedex.domain.model.pokemonSprite.ArtWork
import com.example.mypokedex.presentation.pokemonDetails.adapters.PokemonAtaqueAdapter
import com.example.mypokedex.presentation.pokemonDetails.adapters.PokemonSpriteAdapter
import com.example.mypokedex.presentation.pokemonDetails.adapters.PokemonTipoAdapter
import com.example.mypokedex.presentation.pokemonDetails.adapters.ViewPageAdapter
import com.example.mypokedex.presentation.pokemonDetails.state.PokemonDetailsState
import com.example.mypokedex.presentation.pokemonDetails.state.PokemonState
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonDetailsFragment : Fragment() {

    private var _binding: FragmentPokemonDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<PokemonDetailsFragmentArgs>()
    private val viewmodel by viewModel<PokemonDetailsViewModel>()

    private lateinit var tipoAdapter: PokemonTipoAdapter
    private lateinit var ataqueAdapter: PokemonAtaqueAdapter
    private lateinit var formasAdapter: PokemonSpriteAdapter
    private lateinit var viewpagerAdapter: ViewPageAdapter
    private lateinit var firstEvolutionAdapter: PokemonSpriteAdapter
    private lateinit var secondEvolutionAdapter: PokemonSpriteAdapter
    private lateinit var thirdEvolutionAdapter: PokemonSpriteAdapter

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
        initPokemonFormas()
        initPokemonFirstEvolution()
        initPokemonSecondEvolution()
        initPokemonThirdEvolution()
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
    private fun viewPagerConfiguration(artWork: ArtWork) {
        val viewpagerLayout = binding.viewpagerLayout
        val artworkList = listOf(artWork.frontDefault, artWork.frontShiny)
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
        viewmodel.getPokemonDetails(args.pokemonOrId)
    }

    /**
     * Coleta os dados de PokemonDetails.
     * Em caso de Sucesso: é adicionado todas detalhes do Pokemon
     * Em caso de Loading: é escondido o display dos detalhes e e colocado
     * em display apenas a progressbar
     * Em caso de Erro: é redirecionado para o HomeFragment com um
     * toast dizendo que não possível encontrar o Pokemon
     * */
    private fun initPokemonDetails() {
        lifecycleScope.launchWhenStarted {
            viewmodel.pokemonDetails.collectLatest { result ->
                when (result) {
                    is PokemonDetailsState.Data -> {
                        val pokemon = result.data
                        initPreviousOrNextPokemon(pokemon.id)
                        with(binding) {
                            progressBar.visibilityGone()
                            pokemonLayout.visibilityVisible()
                            pokemonName.text = pokemon.name
                            layoutPokemonNumber.pokemonNumber.text =
                                getFormatedPokemonNumber(pokemon.numero)

                            with(layoutPokemonInfo) {
                                pokemonSpriteDefault.loadPokemonSpriteOrGif(
                                    pokemon,
                                    requireContext(),
                                    pokemonShiny = false
                                )
                                pokemonSpriteShiny.loadPokemonSpriteOrGif(
                                    pokemon,
                                    requireContext(),
                                    pokemonShiny = true
                                )
                                pokemonAltura.text = formatToMeters(pokemon.height)
                                pokemonPeso.text = formatToKg(pokemon.weight)
                            }
                        }
                        tipoAdapter.setData(pokemon.types)
                        viewPagerConfiguration(pokemon.sprites.otherArt.officialArtwork)
                        populateRvPokemonAttacks(viewmodel.filterPokemonMoves(pokemon.moves))
                    }
                    is PokemonDetailsState.Error -> {
                        requireActivity().toast(result.message)
                        binding.progressBar.visibilityGone()
                        toHomeFragment()
                    }
                    PokemonDetailsState.Loading -> {
                        binding.pokemonLayout.visibilityInvisible()
                        binding.progressBar.visibilityVisible()
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

    /**
     * Popula o recyclerview de Formas e em caso de não existir nenhum,
     * é removido o display do container_pokemon_formas
     * */
    private fun initPokemonFormas() {
        lifecycleScope.launchWhenStarted {
            viewmodel.pokemonFormas.collectLatest { result ->
                when (result) {
                    is PokemonState.Data -> {
                        binding.containerPokemonFormas.visibilityVisible()
                        formasAdapter.setData(result.data)
                    }
                    else -> {
                        binding.containerPokemonFormas.visibilityGone()
                    }
                }
            }
        }
    }

    private fun toHomeFragment() {
        findNavController().navigate(R.id.action_pokemonDetailsFragment_to_homeFragment)
    }

    /**
     * Altera o PokemonDetails em display para o Pokemon Anterior ou Posterior
     * dependendo de qual seta for clicada e em caso de ser o primeiro Pokemon(1)
     * é removido o display da seta para esquerda e em caso de ser o último
     * Pokemon(1010) é removido o display da seta para direita
     * */
    private fun initPreviousOrNextPokemon(pokemonId: Int) {
        val arrowRight = binding.layoutPokemonNumber.arrowRight
        val arrowLeft = binding.layoutPokemonNumber.arrowLeft
        if (pokemonId > POKEMON_START_INDEX_LIST) {
            arrowLeft.visibilityVisible()
            arrowLeft.setOnClickListener {
                viewmodel.getPokemonDetails(pokemonId.minus(1).toString())
            }
        } else arrowLeft.visibilityGone()
        if (pokemonId < POKEMON_FINAL_INDEX_LIST) {
            arrowRight.visibilityVisible()
            arrowRight.setOnClickListener {
                viewmodel.getPokemonDetails(pokemonId.plus(1).toString())
            }
        } else arrowRight.visibilityGone()
    }

    /**
     * Coleta os dados da viewmodel para popular o recyclerview
     * de primeiras evoluções e quando não obtver sucesso será
     * removido o display de container_pokemon_evolution
     * */
    private fun initPokemonFirstEvolution() {
        lifecycleScope.launchWhenStarted {
            viewmodel.firstEvolution.collectLatest { result ->
                when (result) {
                    is PokemonState.Data -> {
                        binding.containerPokemonEvolution.visibilityVisible()
                        firstEvolutionAdapter.setData(result.data)
                    }
                    else -> {
                        binding.containerPokemonEvolution.visibilityGone()
                    }
                }
            }
        }
    }

    /**
     * Coleta os dados da viewmodel para popular o recyclerview
     * de segundas evoluções caso obtiver sucesso
     * */
    private fun initPokemonSecondEvolution() {
        lifecycleScope.launchWhenStarted {
            viewmodel.secondEvolution.collectLatest { result ->
                when (result) {
                    is PokemonState.Data -> {
                        binding.rvSecondEvolution.evolutionList.visibilityVisible()
                        binding.rvSecondEvolution.arrowDown.visibilityVisible()
                        secondEvolutionAdapter.setData(result.data)
                    }
                    else -> {}
                }
            }
        }
    }

    /**
     * Coleta os dados da viewmodel e popular o recyclerview
     * de terceiras evoluções e quando não obtiver sucesso
     * será removido o display de pokemon_thrid_evolution e
     * arrow_down do pokemon_second_evolution
     * */
    private fun initPokemonThirdEvolution() {
        lifecycleScope.launchWhenStarted {
            viewmodel.thirdEvolution.collectLatest { result ->
                when (result) {
                    is PokemonState.Data -> {
                        with(binding.rvThirdEvolution) {
                            arrowDown.visibilityGone()
                            evolutionList.visibilityVisible()
                        }
                        thirdEvolutionAdapter.setData(result.data)
                    }
                    else -> {
                        with(binding) {
                            rvThirdEvolution.arrowDown.visibilityGone()
                            rvThirdEvolution.evolutionList.visibilityGone()
                            rvSecondEvolution.arrowDown.visibilityGone()
                        }
                    }
                }
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
            viewmodel.getPokemonDetails(pokemonId.toString())
        }
        secondEvolutionAdapter.onItemClicked = { pokemonId ->
            binding.scrollView.scrollTo(0, 0)
            viewmodel.getPokemonDetails(pokemonId.toString())
        }
        thirdEvolutionAdapter.onItemClicked = { pokemonId ->
            binding.scrollView.scrollTo(0, 0)
            viewmodel.getPokemonDetails(pokemonId.toString())
        }
        formasAdapter.onItemClicked = { pokemonId ->
            with(binding) {
                containerPokemonEvolution.visibilityGone()
                containerPokemonFormas.visibilityGone()
                containerPokemonNumber.visibilityGone()
                scrollView.scrollTo(0, 0)
            }
            viewmodel.getPokemonDetails(pokemonId.toString())
        }
    }
}
