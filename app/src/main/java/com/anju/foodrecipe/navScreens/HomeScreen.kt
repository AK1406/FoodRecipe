package com.anju.foodrecipe.navScreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.anju.foodrecipe.model.Category
import com.anju.foodrecipe.model.FoodDish
import com.anju.foodrecipe.model.PopularDishesModel
import kotlinx.coroutines.delay
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.anju.foodrecipe.viewmodel.DishesViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: DishesViewModel) {
    val categoryList = viewModel.categoryList
    val featuredDishes = viewModel.foodDishList
    println("featuredDishes : $featuredDishes")
    LaunchedEffect(featuredDishes) {
        println("UI sees featured dishes: ${featuredDishes.map { it.dishName }}")
    }
    HomeScreenContent(categoryList, featuredDishes, modifier)

}

@Composable
fun HomeScreenContent(
    categoryList: List<Category>,
    featuredList: List<FoodDish>,
    modifier: Modifier = Modifier
) {
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
                .padding(start = 16.dp, top = 10.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            //top header
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
            FeaturedList(featuredList)
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
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
            Categories(categoryList)
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
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
fun FeaturedDishes(item: FoodDish) {
    Card(
        modifier = Modifier
            .width(264.dp)
            .height(172.dp),
        shape = RoundedCornerShape(24.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val dishImg = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(item.dishImage)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .crossfade(true)
                    .build()
            )
            val cookProfile = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(item.cookProfile)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .crossfade(true)
                    .build()
            )
            // Background Image
            Image(
                painter = painterResource(R.drawable.card),
                contentDescription = "card bg",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Dish Image positioned at top-right
            Image(
                painter = dishImg,
                contentDescription = "${item.dishName}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(140.dp)
                    .offset(x = 25.dp, y = (-40).dp)
                    .align(Alignment.TopEnd)
            )

            // Content at bottom
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "${item.desc}",
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
                        painter = cookProfile,
                        contentDescription = "cook Image",
                        modifier = Modifier
                            .size(25.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.White, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        item.cookName,
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
                            item.cookingTime,
                            style = TextStyle(fontSize = 12.sp, color = Color.White)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FeaturedList(foodDishList: List<FoodDish>) {
    if (foodDishList.isEmpty()) return
    val listState = rememberLazyListState()
    var currentIndex by remember { mutableStateOf(0) }

    // Auto-scroll effect
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentIndex = (currentIndex + 1) % foodDishList.size
            listState.animateScrollToItem(currentIndex)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(foodDishList.size) { index ->
                FeaturedDishes(foodDishList[index])
            }
        }

        // Dot Indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(foodDishList.size) { index ->
                val isSelected = currentIndex == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (isSelected) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) Color.Black else Color.LightGray
                        )
                )
            }
        }
    }
}


@Composable
fun Categories(foodCategoryList: List<Category>) {
    val categoryList = foodCategoryList.map { it.name }
    var selectedCat by remember { mutableStateOf(categoryList.firstOrNull() ?: "") }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(categoryList.size) { index ->
            val categoryName = categoryList[index]
            FilterChip(
                selected = selectedCat == categoryName,
                onClick = {
                    if (categoryName != null) {
                        selectedCat = categoryName
                    }
                },
                label = {
                    if (categoryName != null) {
                        Text(
                            text = categoryName,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
                        )
                    }
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
            Box(
                modifier = Modifier
                    .width(168.dp)
                    .height(128.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
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
                    modifier = Modifier
                        .size(14.dp)
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
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = colorResource(R.color.light_grey)
                        )
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
fun HomeScreenContentPreview() {
    HomeScreenContent(
        categoryList = listOf(Category(name = "Mock Cat")),
        featuredList = listOf(
            FoodDish(
                "chinese",
                "White Noodles",
                "mock food dish description",
                "",
                "Anju Singh",
               " R.drawable.avtar",
                "20 mins",
                true,
                "breakfast"
            )
        )
    )
}
