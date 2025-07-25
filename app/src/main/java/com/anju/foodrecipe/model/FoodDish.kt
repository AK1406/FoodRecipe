package com.anju.foodrecipe.model

data class FoodDish(
    val id: String = "",
    val dishName: String = "",
    val desc: String = "",
    val detail: String = "",
    val dishImage: String = "",
    val cookName: String = "",
    val cookProfile: String = "",
    val cookingTime: String = "",
    var isFeatured: Boolean = false,
    val type: List<String> = emptyList(),
    val nutrients : Map<String, String> = emptyMap(),
    val ingredients : List<Map<String,String>> = emptyList(),
    val rating: Float?=0f,
)