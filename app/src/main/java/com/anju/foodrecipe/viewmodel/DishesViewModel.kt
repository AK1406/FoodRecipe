package com.anju.foodrecipe.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.anju.foodrecipe.model.Category
import com.anju.foodrecipe.model.FoodDish
import com.anju.foodrecipe.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class DishesViewModel : ViewModel() {

    var categoryList by mutableStateOf<List<Category>>(emptyList())
        private set

    var foodDishList by mutableStateOf<List<FoodDish>>(emptyList())
        private set

    var userInfo by mutableStateOf(UserModel())
        private set


    init {
        getLoggedInUserDetails()
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
                val result = snapshot.documents.mapNotNull { doc ->
                    try {
                        val dish = doc.toObject(FoodDish::class.java)?.copy(
                            isFeatured = doc.getBoolean("isFeatured") ?: false
                        )
                        dish
                    } catch (e: Exception) {
                        println("Error parsing document ${doc.id}: ${e.message}")
                        null
                    }
                }

                foodDishList = result
                println("Final foodDishList: $foodDishList")
            }
            .addOnFailureListener { e ->
                println("Failed to fetch food dishes: ${e.message}")
            }
    }


    private fun getLoggedInUserDetails() {
        val uid = Firebase.auth.currentUser?.uid

        if (uid != null) {
            Firebase.firestore.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val user = document.toObject(UserModel::class.java)
                        user?.let {
                            userInfo = it
                        }
                        println("User fetched: $user")
                    } else {
                        println("No such user found with UID: $uid")
                    }
                }
                .addOnFailureListener { e ->
                    println("Error fetching user: ${e.message}")
                }
        } else {
            println("User is not logged in")
        }
    }

}
