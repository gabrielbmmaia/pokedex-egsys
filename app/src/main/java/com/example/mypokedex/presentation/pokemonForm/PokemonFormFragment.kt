package com.example.mypokedex.presentation.pokemonForm

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
import com.example.mypokedex.core.extensions.formatToKg
import com.example.mypokedex.core.extensions.formatToMeters
import com.example.mypokedex.core.extensions.loadPokemonSpriteOrGif
import com.example.mypokedex.core.extensions.toast
import com.example.mypokedex.core.extensions.visibilityGone
import com.example.mypokedex.core.extensions.visibilityInvisible
import com.example.mypokedex.core.extensions.visibilityVisible
import com.example.mypokedex.databinding.FragmentPokemonFormBinding
import com.example.mypokedex.domain.model.Sprites
import com.example.mypokedex.presentation.pokemonDetails.adapters.PokemonTipoAdapter
import com.example.mypokedex.presentation.pokemonDetails.adapters.ViewPageAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel


class PokemonFormFragment : Fragment() {

    private var _binding: FragmentPokemonFormBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<PokemonFormFragmentArgs>()
    private val viewModel by viewModel<PokemonFormViewModel>()

    private lateinit var tipoAdapter: PokemonTipoAdapter
    private lateinit var viewpagerAdapter: ViewPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonFormBinding.inflate(inflater, container, false)
        setToolbar()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setToolbar() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.formToolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initFormDetails()
        populatePokemonFormDetails()
    }

    private fun initRecyclerView() {
        tipoAdapter = PokemonTipoAdapter()
        viewpagerAdapter = ViewPageAdapter()
        binding.rvPokemonType.adapter = tipoAdapter
    }

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

    private fun populatePokemonFormDetails() {
        viewModel.getPokemonDetails(args.pokemonId)
    }

    private fun toPokemonDetails() {
        findNavController().popBackStack()
    }

    private fun initFormDetails() {
        lifecycleScope.launchWhenStarted {
            viewModel.detailsState.collectLatest { state ->
                when (state) {

                    is FormDetailsState.Error -> {
                        requireActivity().toast(state.message)
                        binding.progressBar.visibilityGone()
                        toPokemonDetails()
                    }

                    FormDetailsState.Loading -> {
                        with(binding) {
                            progressBar.visibilityVisible()
                            pokemonLayout.visibilityInvisible()
                        }
                    }

                    is FormDetailsState.Success -> {
                        val pokemon = state.data

                        with(binding) {
                            progressBar.visibilityGone()
                            pokemonLayout.visibilityVisible()
                            pokemonName.text = pokemon.name
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
                    }
                }
            }
        }
    }
}