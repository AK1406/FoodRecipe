package com.anju.foodrecipe

sealed class AuthState{
    object Authenticated: AuthState()
    object UnAuthenticated : AuthState()
    object Loader : AuthState()
    data class Error(val msg : String): AuthState()
}