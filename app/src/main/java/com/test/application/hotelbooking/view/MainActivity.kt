package com.test.application.hotelbooking.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.test.application.hotelbooking.R
import com.test.application.hotelbooking.databinding.ActivityMainBinding
import com.test.application.core.navigation.Navigator

class MainActivity : AppCompatActivity(), Navigator {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun navigateToRoomListFragment() {
        navController.navigate(R.id.action_homeFragment_to_roomListFragment)
    }

    override fun navigateFromRoomListToHomeFragment() {
        navController.navigate(R.id.action_roomListFragment_to_homeFragment)
    }
}