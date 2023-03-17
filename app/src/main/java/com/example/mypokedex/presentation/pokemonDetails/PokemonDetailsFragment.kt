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
import com.example.mypokedex.R
import com.example.mypokedex.core.Constantes.POKEMON_FINAL_INDEX_LIST
import com.example.mypokedex.core.Constantes.POKEMON_START_INDEX_LIST
import com.example.mypokedex.core.extensions.*
import com.example.mypokedex.databinding.FragmentPokemonDetailsBinding
import com.example.mypokedex.presentation.pokemonDetails.adapters.PokemonAtaqueAdapter
import com.example.mypokedex.presentation.pokemonDetails.adapters.PokemonSpriteAdapter
import com.example.mypokedex.presentation.pokemonDetails.adapters.PokemonTipoAdapter
import com.example.mypokedex.presentation.pokemonDetails.state.PokemonDetailsState
import com.example.mypokedex.presentation.pokemonDetails.state.PokemonState
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

        with(binding) {
            rvPokemonType.adapter = tipoAdapter
            rvPokemonAttack.adapter = ataqueAdapter
            rvPokemonFormas.adapter = formasAdapter
        }
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
                        binding.pokemonNumber.text = getFormatedPokemonNumber(pokemon.numero)
                        tipoAdapter.setData(result.data.types)
                        binding.pokemonImage.loadImageFromId(pokemon.numero)
                        binding.pokemonSpriteDefault.loadPokemonSpriteOrGif(
                            pokemon,
                            requireContext(),
                            pokemonShiny = false
                        )
                        binding.pokemonSpriteShiny.loadPokemonSpriteOrGif(
                            pokemon,
                            requireContext(),
                            pokemonShiny = true
                        )
                        binding.pokemonAltura.text = formatToMeters(pokemon.height)
                        binding.pokemonPeso.text = formatToKg(pokemon.weight)
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
                        binding.pokemonFormasLayout.visibilityVisible()
                        formasAdapter.setData(result.data)
                    }
                    else -> {
                        binding.pokemonFormasLayout.visibilityGone()
                    }
                }
            }
        }
    }

    private fun toHomeFragment() {
        findNavController().navigate(R.id.action_pokemonDetailsFragment_to_homeFragment)
    }

    private fun initPreviousOrNextPokemon(pokemonId: Int) {
        val arrowRight = binding.arrowRight
        val arrowLeft = binding.arrowLeft
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
}