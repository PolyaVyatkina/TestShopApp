package com.molkos.testshopapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.molkos.testshopapp.R
import com.molkos.testshopapp.presentation.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}