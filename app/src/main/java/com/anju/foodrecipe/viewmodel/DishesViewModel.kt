package com.anju.foodrecipe.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.anju.foodrecipe.model.CartIngredientsModel
import com.anju.foodrecipe.model.Category
import com.anju.foodrecipe.model.FoodDish
import com.anju.foodrecipe.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class DishesViewModel : ViewModel() {

    var categoryList by mutableStateOf<List<Category>>(emptyList())
        private set

    var foodDishList by mutableStateOf<List<FoodDish>>(emptyList())
        private set

    var userInfo by mutableStateOf(UserModel())
        private set

    var savedCartItems by mutableStateOf<List<CartIngredientsModel>>(emptyList())
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
                    println("Doc ID: ${doc.id}, type field: ${doc.get("type")}")
                    try {
                        val typeList: List<String> = when (val typeField = doc.get("type")) {
                            is String -> listOf(typeField)
                            is List<*> -> typeField.filterIsInstance<String>()
                            else -> emptyList()
                        }
                        val ingredients: List<Map<String, String>> =
                            (doc.get("ingredients") as? List<Map<*, *>>)?.mapNotNull { rawMap ->
                                rawMap.entries
                                    .mapNotNull { entry ->
                                        val key = entry.key as? String
                                        val value = entry.value as? String
                                        if (key != null && value != null) key to value else null
                                    }
                                    .toMap()
                                    .takeIf { it.isNotEmpty() }
                            } ?: emptyList()


                        val dish = doc.toObject(FoodDish::class.java)?.copy(
                            isFeatured = doc.getBoolean("isFeatured") ?: false,
                            detail = doc.getString("detail") ?: "no description",
                            rating = when (val rawRating = doc.get("rating")) {
                                is Number -> rawRating.toFloat()
                                is String -> rawRating.toFloatOrNull() ?: 0f
                                else -> 0f
                            },
                            nutrients = (doc.get("nutrients") as? Map<*, *>)?.mapNotNull {
                                val key = it.key as? String
                                val value = it.value as? String
                                if (key != null && value != null) key to value else null
                            }?.toMap() ?: emptyMap(),
                            ingredients = ingredients,
                            type = typeList
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


    fun getDishDetail(dishId: String): FoodDish? {
        return foodDishList.find { it.id == dishId }
    }


    fun saveCartItem(item: CartIngredientsModel) {
        if (!savedCartItems.contains(item)) {
            val updatedList = savedCartItems.toMutableList()
            updatedList.add(item)
            savedCartItems = updatedList
        }
        saveCartToFireStore(savedCartItems)
    }

    fun saveCartToFireStore(cartList: List<CartIngredientsModel>) {
        // Generate unique document ID using date + UUID
        val date = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val documentId = "cart_${date}_${UUID.randomUUID()}"

        userInfo.uid?.let { uid ->
            Firebase.firestore.collection("data")
                .document("cart")
                .collection(uid)
                .document(documentId)
                .set(mapOf("items" to cartList))
                .addOnSuccessListener {
                    Log.d("CartSave", "Cart items saved successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("CartSave", "Failed to save cart items: ${e.message}")
                }
        }
    }


}
