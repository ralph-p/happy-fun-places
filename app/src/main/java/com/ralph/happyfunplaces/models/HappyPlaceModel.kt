package com.ralph.happyfunplaces.models

data class HappyPlaceModel(
    val id: Int,
    val title: String,
    val image: String,
    val description: String,
    val date: String,
    val location: String,
    val lat: Double,
    val long: Double
)