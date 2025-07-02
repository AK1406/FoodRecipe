package com.anju.foodrecipe.navScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anju.foodrecipe.R
import com.anju.foodrecipe.viewmodel.AuthViewModel
import com.anju.foodrecipe.viewmodel.DishesViewModel

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    dishesViewModel: DishesViewModel
) {
    val navItems = listOf(
        Icons.Default.Home,
        Icons.Default.Search,
        null, // Placeholder for center FAB
        Icons.Default.Favorite,
        Icons.Default.Person
    )

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold(
        bottomBar = {
            Box {
                // Bottom Bar Background
                Surface(
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    tonalElevation = 8.dp,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        navItems.forEachIndexed { index, icon ->
                            if (index == 2) {
                                Spacer(modifier = Modifier.width(60.dp)) // space for FAB
                            } else {
                                IconButton(onClick = { selectedItemIndex = index }) {
                                    Icon(
                                        imageVector = icon!!,
                                        contentDescription = null,
                                        tint = if (selectedItemIndex == index) Color(0xFF042628) else Color.Gray,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Floating Center FAB with Chef Icon (clickable!)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = (-30).dp)
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF042628))
                        .border(4.dp, Color.White, CircleShape)
                        .clickable { selectedItemIndex = 2 },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.chef),
                        contentDescription = "Chef",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        ContentScreen(
            modifier = modifier.padding(paddingValues),
            selectedIdx = selectedItemIndex,
            dishesViewModel
        )
    }
}




@Composable
fun ContentScreen(
    modifier: Modifier = Modifier, selectedIdx: Int, dishesViewModel: DishesViewModel
) {
    when (selectedIdx) {
        0 -> HomeScreen(modifier,dishesViewModel)
        1 -> SearchRecipeScreen(modifier)
        2 -> DishDetailScreen(modifier)
        3 -> UserAccountScreen(modifier)
    }

}


@Preview(showBackground = true)
@Composable
private fun DishDetailPreview() {
   // BottomNavigation(dishesViewModel = dishesViewModel)
}