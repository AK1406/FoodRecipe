package com.anju.foodrecipe.model

data class FoodDish(
    val id: String = "",
    val dishName: String = "",
    val desc: String = "",
    val dishImage: String = "",
    val cookName: String = "",
    val cookProfile: String = "",
    val cookingTime: String = "",
    var isFeatured: Boolean = false,
    val type: String = "",
    val calories: String?="",
    val protein: String?="",
    val carbs: String?="",
    val fat: String?="",
    val rating: Float?=0f,
)