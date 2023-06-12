package com.example.mypokedex.presentation.splashScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mypokedex.R
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScreenSplashFragment : Fragment() {

    private val viewModel by viewModel<SplashScreenViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.synchronizePokemonList()

        lifecycleScope.launchWhenStarted {
            delay(3000)
            val action = ScreenSplashFragmentDirections.actionSreenSplashFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }
}
