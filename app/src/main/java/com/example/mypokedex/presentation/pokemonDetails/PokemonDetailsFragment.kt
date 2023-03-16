package com.example.mypokedex.presentation.pokemonDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.mypokedex.R
import com.example.mypokedex.core.extensions.*
import com.example.mypokedex.databinding.FragmentPokemonDetailsBinding
import com.example.mypokedex.presentation.pokemonDetails.adapters.PokemonAtaqueAdapter
import com.example.mypokedex.presentation.pokemonDetails.adapters.PokemonTipoAdapter
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonDetailsFragment : Fragment() {

    private var _binding: FragmentPokemonDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<PokemonDetailsFragmentArgs>()
    private val viewmodel by viewModel<PokemonDetailsViewModel>()
    lateinit var tipoAdapter: PokemonTipoAdapter
    lateinit var ataqueAdapter: PokemonAtaqueAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populatePokemonDetails()
        initPokemonDetails()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initRecyclerView() {
        tipoAdapter = PokemonTipoAdapter()
        ataqueAdapter = PokemonAtaqueAdapter(requireContext())
        with(binding) {
            rvPokemonType.adapter = tipoAdapter
            rvPokemonAttack.adapter = ataqueAdapter
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
                        ataqueAdapter.setData(filterToLearnableAttacks(pokemon.moves))
                    }
                    is PokemonDetailsState.Error -> {}
                    PokemonDetailsState.Loading -> {}
                }
            }
        }
    }
}