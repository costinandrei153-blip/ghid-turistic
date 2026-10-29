package com.example.obiective

import kotlinx.serialization.Serializable

@Serializable object Login
@Serializable object CategoriesGraph      // graf imbricat: listă + detaliu
@Serializable object PlaceList
@Serializable data class PlaceDetail(val id: Int)
@Serializable object Favorites
@Serializable object About
