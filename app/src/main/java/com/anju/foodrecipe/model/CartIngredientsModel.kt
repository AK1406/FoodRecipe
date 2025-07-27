package com.anju.foodrecipe.model


data class CartIngredientsModel(
    val dishId:String?="",
    val dishName:String?="",
    val dishImage: String? ="",
    val ingredients: List<Map<String,String>> = emptyList(),
    var totalCost: Double = 0.0
)