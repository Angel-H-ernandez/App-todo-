package com.angel_inc.todoapp.ui.view.home_principal_fragments

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.angel_inc.todoapp.R
import com.angel_inc.todoapp.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class homeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private  lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        Log.e("Tag", "home activity 2")
        initUi()

    }

    private fun initUi() {
        Log.e("Tag", "home activity")
        initNavigation()
    }

    private fun initNavigation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.containerFragment) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavigation.setupWithNavController(navController)

    }
}