package com.nazlican.ecommerce.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.databinding.ActivityMainBinding
import com.nazlican.sisterslabproject.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen()
        setContentView(binding.root)



        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(
            binding.bottomNav,
            navController
        )

        navController.addOnDestinationChangedListener {_,destination,_ ->
            when (destination.id) {
                R.id.homeFragment -> binding.bottomNav.visibility = View.VISIBLE
                R.id.searchFragment -> binding.bottomNav.visibility = View.VISIBLE
                R.id.cartFragment -> binding.bottomNav.visibility = View.VISIBLE
                R.id.favoritesFragment -> binding.bottomNav.visibility = View.VISIBLE
                R.id.profileFragment -> binding.bottomNav.visibility = View.VISIBLE

                else ->binding.bottomNav.visibility = View.GONE
            }
        }
    }
}
