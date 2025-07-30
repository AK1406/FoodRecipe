package com.anju.foodrecipe.navScreens

import android.util.Printer
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.anju.foodrecipe.model.Category
import com.anju.foodrecipe.model.FoodDish
import com.anju.foodrecipe.model.PopularDishesModel
import kotlinx.coroutines.delay
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.anju.foodrecipe.GlobalNavigation
import com.anju.foodrecipe.viewmodel.DishesViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: DishesViewModel) {

    val categoryList = viewModel.categoryList
    val foodDishes = viewModel.foodDishList
    LaunchedEffect(Unit) {
        if (foodDishes.isNotEmpty()) {
            viewModel.callFavouriteList()
        }
    }
    val favFoodDishes = viewModel.favouriteDishesList
    println("favFoodDishes: $favFoodDishes")
    val favDishIds = favFoodDishes.filter { it.isFavourite }.map { it.id }.toSet()

    val updatedFoodDishes = foodDishes.map { dish ->
        if (dish.id in favDishIds) {
            dish.copy(isFavourite = true)
        } else {
            dish
        }
    }

    val fav = updatedFoodDishes.filter { it.isFavourite == true }

    println("FoodDishes: $fav")
    HomeScreenContent(categoryList, updatedFoodDishes, viewModel, modifier)

}

@Composable
fun HomeScreenContent(
    categoryList: List<Category>,
    foodDishes: List<FoodDish>,
    viewModel: DishesViewModel,
    modifier: Modifier = Modifier
) {
    val userInfo = viewModel.userInfo
    var selCategory by remember { mutableStateOf("breakfast") }

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
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 16.dp, 0.dp)
                        .clickable {
                            GlobalNavigation.navController.navigate("cart")
                        }
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                userInfo.name.toString(),
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
            FeaturedList(foodDishes.filter { it.isFeatured == true })
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
            Categories(categoryList) { cat ->
                selCategory = cat
            }
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
            PopularDishList(
                foodDishes.filter { it.type.contains(selCategory.lowercase()) },
                viewModel
            )

        }

    }
}


@Composable
fun FeaturedDishes(item: FoodDish) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .width(264.dp)
            .height(172.dp),
        shape = RoundedCornerShape(24.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background image
            Image(
                painter = painterResource(id = R.drawable.card),
                contentDescription = "card bg",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Dish Image
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(item.dishImage)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = item.dishName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(140.dp)
                    .offset(x = 25.dp, y = (-40).dp)
                    .align(Alignment.TopEnd),
                placeholder = painterResource(R.drawable.white_noodles)
            )

            // Bottom content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = item.desc,
                    modifier = Modifier.width(180.dp),
                    maxLines = 2,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(item.cookProfile)
                            .crossfade(true)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .build(),
                        contentDescription = "cook image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(25.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.White, CircleShape)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = item.cookName,
                        style = TextStyle(fontSize = 12.sp, color = Color.White)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.clock),
                            contentDescription = "time to cook",
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = item.cookingTime,
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
    System.out.println("FeaturedList: ${foodDishList.size}")
    if (foodDishList.isEmpty()) return

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth()) {
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            itemsIndexed(foodDishList, key = { _, item -> item.id }) { index, dish ->
                FeaturedDishes(dish)
            }
        }

        val visibleIndex by remember {
            derivedStateOf {
                val midItem = listState.layoutInfo.visibleItemsInfo
                    .minByOrNull { kotlin.math.abs(it.offset + it.size / 2) }?.index
                midItem ?: 0
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(foodDishList.size) { index ->
                val isSelected = index == visibleIndex
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (isSelected) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color.Black else Color.LightGray)
                )
            }
        }
    }
}


@Composable
fun Categories(foodCategoryList: List<Category>, selType: (String) -> Unit) {
    if (foodCategoryList.isEmpty()) return

    val categoryList = foodCategoryList.map { it.name }
    var selectedCat by remember { mutableStateOf(categoryList.first()) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(categoryList) { categoryName ->
            FilterChip(
                selected = selectedCat == categoryName,
                onClick = {
                    selectedCat = categoryName
                    selType(selectedCat.lowercase())
                },
                label = {
                    Text(
                        text = categoryName,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                        maxLines = 1
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
fun PopularDishes(item: FoodDish, viewModel: DishesViewModel) {
    println("item: $item")
    var isFavorite by remember { mutableStateOf(item.isFavourite) }
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
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.dishImage)
                        .crossfade(true)
                        .error(R.drawable.taco)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = item.dishName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite Icon",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color.White)
                        .padding(6.dp)
                        .align(Alignment.TopEnd)
                        .clickable {
                            isFavorite = !isFavorite // Toggle state
                            item.isFavourite = isFavorite
                            viewModel.setFavouriteDish(item)
                        },
                    tint = if (isFavorite) colorResource(R.color.authScreenBgColor) else Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
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
                    "${item.nutrients["calories"]}",
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
                        "${item.cookingTime}",
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
fun PopularDishList(foodDishList: List<FoodDish>, viewModel: DishesViewModel) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(foodDishList.size) { item ->
            PopularDishes(foodDishList[item], viewModel)
        }

    }
}

/*
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
*/