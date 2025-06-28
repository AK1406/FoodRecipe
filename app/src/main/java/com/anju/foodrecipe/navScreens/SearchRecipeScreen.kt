package com.anju.foodrecipe.navScreens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anju.foodrecipe.R
import com.anju.foodrecipe.model.EditorChoiceDish
import com.anju.foodrecipe.model.PopularDishesModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRecipeScreen(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        colorResource(R.color.lightest_grey)
                    ) // From top to bottom
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Toolbar()
            Spacer(modifier = Modifier.height(15.dp))
            SearchDish()
            Spacer(modifier = Modifier.height(10.dp))
            DishCategories()
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
                    "View all",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(R.color.authScreenBgColor)
                    ),
                    modifier = Modifier.padding(0.dp, 0.dp, 16.dp, 0.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            DishList()
            Spacer(modifier = Modifier.height(30.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Editor's choice",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "View all",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(R.color.authScreenBgColor)
                    ),
                    modifier = Modifier.padding(0.dp, 0.dp, 16.dp, 0.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            EditorChoiceDishList()
        }
    }


}


@Composable
fun Toolbar() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Icon(imageVector = Icons.Default.ArrowBack, "back")
        Text(
            "Search", style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ), modifier = Modifier.fillMaxWidth()
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDish() {

    var query by remember { mutableStateOf("") }

    OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        placeholder = { Text("Search") },
        modifier = Modifier
            .fillMaxWidth()
            .height(67.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Blue,
            unfocusedIndicatorColor = Color.Gray,
            disabledIndicatorColor = colorResource(R.color.light_grey)
        ),
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, "search dish")
        },
        singleLine = true
    )
}

@Composable
fun DishCategories() {
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
fun DishList() {
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
            DishListItem(featuredItems[item])
        }


    }
}

@Composable
fun DishListItem(item: PopularDishesModel) {
    Card(
        modifier = Modifier
            .width(130.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
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
            Image(
                painter = painterResource(R.drawable.taco),
                contentDescription = "card bg",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(84.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Text(
                "${item.dishName}",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            )
        }
    }
}

@Composable
fun EditorChoiceListItem(editor: EditorChoiceDish) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(2.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.egg), "Dish image",
                modifier = Modifier
                    .width(100.dp)
                    .height(84.dp)
                    .clip(RoundedCornerShape(16.dp)),
            )
            //Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "${editor.dishName}",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.avtar),
                            contentDescription = "Cook Image",
                            modifier = Modifier
                                .size(25.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.White, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "James Spader",
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = colorResource(R.color.light_grey)
                            )
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black)
                        .padding(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Forward arrow",
                        tint = colorResource(R.color.light_grey)
                    )
                }
            }


        }
    }
}

@Composable
fun EditorChoiceDishList() {
    val dishList = listOf(
        EditorChoiceDish("Easy homemade beef burger", "James Spader"),
        EditorChoiceDish("Blueberry with egg for breakfast", "James Spader")
    )

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(dishList.size) { item ->
            EditorChoiceListItem(dishList[item])
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SearchDishScreenPreview() {
    SearchRecipeScreen()
}