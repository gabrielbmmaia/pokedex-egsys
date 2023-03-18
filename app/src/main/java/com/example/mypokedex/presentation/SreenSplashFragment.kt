package com.example.mypokedex.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypokedex.R
import com.example.mypokedex.presentation.pokemonDetails.PokemonDetailsFragmentDirections
import kotlinx.coroutines.delay

class SreenSplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            delay(3000)
//            findNavController().navigate(R.id.action_sreenSplashFragment_to_homeFragment)
            val action = SreenSplashFragmentDirections.actionSreenSplashFragmentToHomeFragment(null)
            findNavController().navigate(action)
        }
    }
}
