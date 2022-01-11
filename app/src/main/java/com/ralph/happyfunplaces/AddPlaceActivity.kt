package com.ralph.happyfunplaces

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar


class AddPlaceActivity : AppCompatActivity() {
    lateinit var addToolBar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_place)
        addToolBar = findViewById(R.id.toolbar_add_place)
        setSupportActionBar(addToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        addToolBar.setNavigationOnClickListener{
            onBackPressed()
        }
    }
}