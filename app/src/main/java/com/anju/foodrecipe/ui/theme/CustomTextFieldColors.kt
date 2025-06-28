package com.anju.foodrecipe.ui.theme

import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun customTextFieldColors(): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedIndicatorColor = Color(0xFF042628),
        unfocusedIndicatorColor = Color.Gray,
        errorIndicatorColor = Color.Red,
        disabledIndicatorColor = Color.LightGray,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent
    )
}
