package com.anju.foodrecipe.model

data class FoodDish(
    val id: String = "",
    val dishName: String = "",
    val desc: String = "",
    val dishImage: String = "",
    val cookName: String = "",
    val cookProfile: String = "",
    val cookingTime: String = "",
    val isFeatured: Boolean = false,
    val type: String = ""
)