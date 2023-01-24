package com.yusufarisoy.composepagingapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yusufarisoy.composepagingapp.R
import com.yusufarisoy.composepagingapp.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, HomeFragment())
            .commit()
    }
}
