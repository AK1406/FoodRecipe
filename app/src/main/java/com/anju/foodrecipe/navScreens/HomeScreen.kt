package com.anju.foodrecipe.navScreens

import android.graphics.drawable.Icon
import android.widget.Space
import androidx.annotation.ColorRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anju.foodrecipe.R
import com.anju.foodrecipe.model.FeaturedModel
import com.anju.foodrecipe.registration.AuthScreen
import kotlin.math.sign
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import com.anju.foodrecipe.model.PopularDishesModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White, colorResource(R.color.lightest_grey)) // From top to bottom
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, 16.dp, 0.dp, 0.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(R.drawable.sun), contentDescription = "Sunny")
                Spacer(modifier = Modifier.width(5.dp))
                Text("Good Morning!")
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    Icons.Outlined.ShoppingCart,
                    contentDescription = "cart",
                    modifier = Modifier.padding(0.dp, 0.dp, 16.dp, 0.dp)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                "Alena Sabyan",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                "Featured",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(15.dp))
            FeaturedList(modifier)
            Spacer(modifier = Modifier.height(15.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Category",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "See all",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(R.color.authScreenBgColor)
                    ),
                    modifier = Modifier.padding(0.dp, 0.dp, 16.dp, 0.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Categories()
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Popular Recipes",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "See all",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(R.color.authScreenBgColor)
                    ),
                    modifier = Modifier.padding(0.dp, 0.dp, 16.dp, 0.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            PopularDishList()

        }

    }

}


@Composable
fun FeaturedDishes(item: FeaturedModel) {
    Card(
        modifier = Modifier
            .width(264.dp)
            .height(172.dp),
        shape = RoundedCornerShape(24.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Image
            Image(
                painter = painterResource(R.drawable.card_bg),
                contentDescription = "card bg",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Content at bottom
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart) // Align column to bottom
                    .padding(16.dp), // Optional padding
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Asian white noodle with extra seafood",
                    modifier = Modifier.width(180.dp),
                    maxLines = 2,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Image(
                        painter = painterResource(R.drawable.avtar),
                        contentDescription = "cook Image",
                        modifier = Modifier
                            .size(25.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.White, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        "James Spader",
                        style = TextStyle(fontSize = 12.sp, color = Color.White)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.clock),
                            contentDescription = "time to cook",
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            "20 mins",
                            style = TextStyle(fontSize = 12.sp, color = Color.White)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun FeaturedList(modifier: Modifier = Modifier) {
    val featuredItems =
        listOf<FeaturedModel>(
            FeaturedModel("Asian white noodle with extra seafood", "James Spader", "20 mins"),
            FeaturedModel("Asian white noodle with extra seafood", "Satya Spader", "15 mins"),
            FeaturedModel("Asian white noodle with extra seafood", "Puhan Spader", "30 mins"),
        )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(featuredItems.size) { item ->
            FeaturedDishes(featuredItems[item])
        }


    }
}


@Composable
fun Categories() {
    val categoryList = listOf<String>("Breakfast", "Lunch", "Snacks", "Dinner")
    var selectedCat by remember { mutableStateOf(categoryList[0]) }
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(categoryList.size) { cat ->
            FilterChip(
                selected = selectedCat == categoryList[cat],
                onClick = { selectedCat = categoryList[cat] },
                label = {
                    Text(
                        text = categoryList[cat].toString(),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
                    )
                },
                shape = RoundedCornerShape(30.dp),
                border = BorderStroke(0.dp, Color.Transparent),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = colorResource(R.color.authScreenBgColor),
                    selectedLabelColor = Color.White,
                    containerColor = Color(0xFFE0E0E0),
                    labelColor = Color.Black
                )
            )
        }
    }
}


@Composable
fun PopularDishes(item: PopularDishesModel) {
    Card(
        modifier = Modifier
            .width(200.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(modifier = Modifier
                .width(168.dp)
                .height(128.dp)
                .clip(RoundedCornerShape(16.dp))) {
                // Background Image
                Image(
                    painter = painterResource(R.drawable.taco),
                    contentDescription = "card bg",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Icon(
                    Icons.Outlined.FavoriteBorder,
                    contentDescription = "cook Image",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color.White)
                        .padding(6.dp)
                        .align(Alignment.TopEnd),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "${item.dishName}",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(R.drawable.calories),
                    contentDescription = "total calories",
                    modifier = Modifier.size(14.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    "${item.calories}",
                    style = TextStyle(fontSize = 12.sp, color = colorResource(R.color.light_grey))
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.clock),
                        contentDescription = "time to cook",
                        modifier = Modifier.size(14.dp),
                        colorFilter = ColorFilter.tint(colorResource(R.color.light_grey))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        "${item.cookTime}",
                        style = TextStyle(fontSize = 12.sp, color = colorResource(R.color.light_grey))
                    )
                }
            }
        }
    }
}


@Composable
fun PopularDishList() {
    val featuredItems =
        listOf<PopularDishesModel>(
            PopularDishesModel("Healthy Taco Salad with fresh vegetable", "120 Kcal", "20 mins"),
            PopularDishesModel("Healthy Taco Salad with fresh vegetable", "120 Kcal", "20 mins"),
            PopularDishesModel("Healthy Taco Salad with fresh vegetable", "120 Kcal", "20 mins"),
            PopularDishesModel("Healthy Taco Salad with fresh vegetable", "120 Kcal", "20 mins"),
        )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(featuredItems.size) { item ->
            PopularDishes(featuredItems[item])
        }


    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}