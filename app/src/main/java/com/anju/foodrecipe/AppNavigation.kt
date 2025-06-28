package com.anju.foodrecipe

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anju.foodrecipe.navScreens.BottomNavigation
import com.anju.foodrecipe.navScreens.DishDetailScreen
import com.anju.foodrecipe.navScreens.HomeScreen
import com.anju.foodrecipe.navScreens.SearchDish
import com.anju.foodrecipe.navScreens.SearchRecipeScreen
import com.anju.foodrecipe.navScreens.UserAccountScreen
import com.anju.foodrecipe.registration.AuthScreen
import com.anju.foodrecipe.registration.LoginScreen
import com.anju.foodrecipe.registration.SignUpScreen
import com.anju.foodrecipe.viewmodel.AuthViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val isLoggedIn = Firebase.auth.currentUser != null
    val firstScreen = if (isLoggedIn) "home" else "auth"
    val navController = rememberNavController()
    GlobalNavigation.navController = navController

    NavHost(navController = navController, startDestination = firstScreen, builder = {
        composable("auth") {
            AuthScreen(modifier,navController,authViewModel)
        }
        composable("login") {
            LoginScreen(modifier,navController,authViewModel)
        }
        composable("sign_up") {
            SignUpScreen(modifier,navController,authViewModel)
        }
        composable("home") {
            BottomNavigation(modifier)
        }
        composable("search_recipe") {
            SearchRecipeScreen(modifier)
        }
        composable("dish_detail") {
            DishDetailScreen(modifier)
        }
        composable("account") {
            UserAccountScreen(modifier)
        }

    })
}

object GlobalNavigation {
    lateinit var navController: NavController
}