package com.ralph.happyfunplaces

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var setOnHappyPlace: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setOnHappyPlace = findViewById(R.id.fabAddHappyPlace)
        setOnHappyPlace.setOnClickListener{
            val intent = Intent(this, AddPlaceActivity::class.java)
            startActivity(intent)
        }

    }
}