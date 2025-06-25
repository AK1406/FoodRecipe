package com.anju.foodrecipe.model

data class DishDetailModel(
    val desc : String?,
    val cookingTime: String?,
    val nutrientsInfo : Map<Int, Pair<String, String>>?,
    val ingredients : Map<String, Int>?,
    val cookName: String?,
    val cookBriefBio : String?,
    val cookImage : Int?
)