package com.anju.foodrecipe.model

import com.google.firebase.firestore.PropertyName

data class FoodDish(
    val id: String = "",
    val dishName: String = "",
    val desc: String = "",
    val detail: String = "",
    val dishImage: String = "",
    val cookName: String = "",
    val cookProfile: String = "",
    val cookingTime: String = "",
    @field:JvmField
    @field:PropertyName("isFeatured")
    @get:PropertyName("isFeatured")
    var isFeatured: Boolean = false,
    val type: List<String> = emptyList(),
    val nutrients : Map<String, String> = emptyMap(),
    val ingredients : List<Map<String,String>> = emptyList(),
    val rating: Float?=0f,
    @field:JvmField
    @field:PropertyName("isFavourite")
    @get:PropertyName("isFavourite")
    var isFavourite : Boolean = false
)

data class FavouriteDishesWrapper(
    val dishes : List<FoodDish> = emptyList()
)