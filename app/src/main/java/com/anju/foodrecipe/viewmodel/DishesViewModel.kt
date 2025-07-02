package com.anju.foodrecipe.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.anju.foodrecipe.model.Category
import com.anju.foodrecipe.model.FoodDish
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class DishesViewModel : ViewModel() {

    var categoryList by mutableStateOf<List<Category>>(emptyList())
        private set

    var foodDishList by mutableStateOf<List<FoodDish>>(emptyList())
        private set

    init {
        fetchCategories()
        fetchFeaturedDishes()
    }

    private fun fetchCategories() {
        Firebase.firestore.collection("data")
            .document("Dishes")
            .collection("category")
            .get()
            .addOnSuccessListener { snapshot ->
                val result = snapshot.documents.mapNotNull { it.toObject(Category::class.java) }
                categoryList = result
            }
            .addOnFailureListener { e ->
                Log.e("ViewModel", "Failed to fetch categories: ${e.message}")
            }
    }

    private fun fetchFeaturedDishes() {
        Firebase.firestore.collection("data")
            .document("Dishes")
            .collection("FoodDishes")
            .get()
            .addOnSuccessListener { snapshot ->
                println("Documents fetched: ${snapshot.size()}")
                snapshot.documents.forEach { doc ->
                    println("Doc ID: ${doc.id} -> Data: ${doc.data}")
                }

                val result = snapshot.documents.mapNotNull { it.toObject(FoodDish::class.java) }
                foodDishList = result
                println("foodDishList: $foodDishList")
            }

            .addOnFailureListener { e ->
                println("Failed to fetch food dishes: ${e.message}")
            }
    }
}
