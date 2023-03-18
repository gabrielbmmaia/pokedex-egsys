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

    lateinit var tipoAdapter: PokemonTipoAdapter
    lateinit var ataqueAdapter: PokemonAtaqueAdapter
    lateinit var formasAdapter: PokemonSpriteAdapter
    lateinit var viewpagerAdapter: ViewPageAdapter
    lateinit var firstEvolutionAdapter: PokemonSpriteAdapter
    lateinit var secondEvolutionAdapter: PokemonSpriteAdapter
    lateinit var thirdEvolutionAdapter: PokemonSpriteAdapter

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

    private fun viewPagerConfiguration(artWork: ArtWork) {
        val artworkList = listOf(artWork.frontDefault, artWork.frontShiny)
        binding.viewpagerLayout.viewpager.apply {
            adapter = viewpagerAdapter
            offscreenPageLimit = artworkList.size
            (adapter as ViewPageAdapter).setData(artworkList)

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.viewpagerLayout.tabIndicator.apply {
                        selectTab(this.getTabAt(position))
                    }
                }
            })
        }
        TabLayoutMediator(
            binding.viewpagerLayout.tabIndicator,
            binding.viewpagerLayout.viewpager
        ) { _, _ -> }.attach()
    }

    private fun populatePokemonDetails() {
        viewmodel.getPokemonDetails(args.pokemonOrId)
    }

    private fun initPokemonDetails() {
        lifecycleScope.launchWhenStarted {
            viewmodel.pokemonDetails.collectLatest { result ->
                when (result) {
                    is PokemonDetailsState.Data -> {
                        val pokemon = result.data
                        initPreviousOrNextPokemon(pokemon.id)
                        binding.progressBar.visibilityGone()
                        binding.pokemonLayout.visibilityVisible()
                        binding.pokemonName.text = pokemon.name
                        binding.layoutPokemonNumber.pokemonNumber.text =
                            getFormatedPokemonNumber(pokemon.numero)
                        tipoAdapter.setData(result.data.types)
                        viewPagerConfiguration(result.data.sprites.otherArt.officialArtwork)
                        binding.layoutPokemonInfo.pokemonSpriteDefault.loadPokemonSpriteOrGif(
                            pokemon,
                            requireContext(),
                            pokemonShiny = false
                        )
                        binding.layoutPokemonInfo.pokemonSpriteShiny.loadPokemonSpriteOrGif(
                            pokemon,
                            requireContext(),
                            pokemonShiny = true
                        )
                        binding.layoutPokemonInfo.pokemonAltura.text =
                            formatToMeters(pokemon.height)
                        binding.layoutPokemonInfo.pokemonPeso.text = formatToKg(pokemon.weight)
                        ataqueAdapter.setData(viewmodel.filterPokemonMoves(pokemon.moves))
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

    private fun initPokemonFirstEvolution() {
        lifecycleScope.launchWhenStarted {
            viewmodel.firstEvolution.collectLatest { result ->
                when (result) {
                    is PokemonState.Data -> {
                        binding.pokemonEvolutionLayout.visibilityVisible()
                        firstEvolutionAdapter.setData(result.data)
                    }
                    else -> {
                        binding.pokemonEvolutionLayout.visibilityGone()
                    }
                }
            }
        }
    }

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
        formasAdapter.onItemClicked = { pokemonid ->
            binding.scrollView.scrollTo(0, 0)
            if (pokemonid > POKEMON_FINAL_INDEX_LIST) {
                binding.pokemonEvolutionLayout.visibilityGone()
            }
            viewmodel.getPokemonDetails(pokemonid.toString())
        }
    }
}