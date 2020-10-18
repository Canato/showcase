package com.can_apps.showcase

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.can_apps.showcase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(binding.myNavHostFragment.id) as NavHostFragment? ?: return

        val navController = host.navController

        setupNavigationMenu(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                destination.id.toString()
            }

            // TODO #21 use for tracking
            Log.d("NavigationActivity", "Navigated to $dest")
        }
    }

    private fun setupNavigationMenu(navController: NavController) {
        // In split screen mode, you can drag this view out from the left
        // This does NOT modify the actionbar
        binding.navView?.setupWithNavController(navController)
    }
}
