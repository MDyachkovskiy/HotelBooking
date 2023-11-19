package com.test.application.hotelbooking.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.application.home.HomeFragment
import com.test.application.hotelbooking.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(binding.mainContainer.id, HomeFragment())
                .commit()
        }
    }
}