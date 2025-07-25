package com.anju.foodrecipe

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anju.foodrecipe.navScreens.BottomNavigation
import com.anju.foodrecipe.navScreens.DishDetailScreen
import com.anju.foodrecipe.navScreens.SearchRecipeScreen
import com.anju.foodrecipe.navScreens.UserAccountScreen
import com.anju.foodrecipe.registration.AuthScreen
import com.anju.foodrecipe.registration.LoginScreen
import com.anju.foodrecipe.registration.SignUpScreen
import com.anju.foodrecipe.viewmodel.AuthViewModel
import com.anju.foodrecipe.viewmodel.DishesViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    dishesViewModel: DishesViewModel
) {
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
            BottomNavigation(modifier,dishesViewModel)
        }
        composable("search_recipe") {
            SearchRecipeScreen(modifier,dishesViewModel)
        }
        composable(
            route = "dish_detail/{dishId}",
            arguments = listOf(navArgument("dishId") { type = NavType.StringType })
        ) { backStackEntry ->
            val dishId = backStackEntry.arguments?.getString("dishId")
            dishId?.let {
                DishDetailScreen(dishId = it,modifier,dishesViewModel)
            }
        }

        composable("account") {
            UserAccountScreen(modifier)
        }

    })
}

object GlobalNavigation {
    lateinit var navController: NavController
}