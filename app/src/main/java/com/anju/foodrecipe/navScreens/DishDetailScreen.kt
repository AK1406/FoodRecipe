package com.anju.foodrecipe.navScreens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.anju.foodrecipe.R
import com.anju.foodrecipe.model.FoodDish
import com.anju.foodrecipe.viewmodel.DishesViewModel
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Divider
import androidx.compose.material3.rememberBottomSheetScaffoldState
import com.anju.foodrecipe.GlobalNavigation
import com.anju.foodrecipe.model.CartIngredientsModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishDetailScreen(dishId: String, modifier: Modifier = Modifier, viewModel: DishesViewModel) {
    val dish = viewModel.getDishDetail(dishId)
    val sheetScaffoldState = rememberBottomSheetScaffoldState()
    val foodDishes = viewModel.foodDishList

    val nutrientDrawableMap = mapOf(
        "calories" to R.drawable.calories,
        "protein" to R.drawable.proteins,
        "carbs" to R.drawable.carbs,
        "fat" to R.drawable.fats
    )

    val nutrientInfo: Map<Int, Map<String, String>> = dish?.nutrients?.mapNotNull { (key, value) ->
        nutrientDrawableMap[key.lowercase()]?.let { drawable ->
            drawable to mapOf(key to value)
        }
    }?.toMap().orEmpty()

    BottomSheetScaffold(
        scaffoldState = sheetScaffoldState,
        sheetPeekHeight = 320.dp,
        sheetContent = {
            dish?.let {
                BottomSheetContent(it, nutrientInfo, foodDishes, viewModel)
            }
        },
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = Color.Transparent
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(dish?.dishImage)
                    .crossfade(true)
                    .error(R.drawable.taco)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = dish?.dishName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp),
                contentScale = ContentScale.FillBounds
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.75f),
                                Color.White.copy(alpha = 0.05f),
                                Color.Transparent
                            )
                        )
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Close",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(6.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                        .padding(6.dp)
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    dishDetail: FoodDish,
    nutrientInfo: Map<Int, Map<String, String>>,
    foodDishes: List<FoodDish>,
    viewModel: DishesViewModel
) {

    val selectedIngredients = remember {
        mutableStateListOf<Map<String, String>>().apply {
            dishDetail?.ingredients?.let { addAll(it) }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = dishDetail.dishName,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.clock),
                        contentDescription = "time to cook",
                        modifier = Modifier.size(22.dp),
                        colorFilter = ColorFilter.tint(colorResource(R.color.light_grey))
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = dishDetail.cookingTime,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = colorResource(R.color.light_grey)
                        )
                    )
                }
            }
        }

        item {
            ExpandableText(dishDetail.detail, 2)
        }

        item {
            NutrientsInfo(nutrientInfo)
        }

        item {
            TabsOptions(dishDetail) { updatedIngList ->
                selectedIngredients.clear()
                selectedIngredients.addAll(updatedIngList)
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    var totalCost = 0.0
                    selectedIngredients.forEach { item ->
                        val qty = item["qty"]?.toFloatOrNull() ?: 0f
                        val cost = item["cost"]?.toFloatOrNull() ?: 0f
                        totalCost += qty * cost
                    }
                    val finalCart = CartIngredientsModel(
                        dishDetail.id,
                        dishDetail.dishName,
                        dishDetail.dishImage,
                        selectedIngredients,
                        totalCost
                    )
                    viewModel.saveCartItem(finalCart)
                    GlobalNavigation.navController.navigate("cart")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.authScreenBgColor))
            ) {
                Text(
                    "Add to cart",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Creator(foodDishes.filter { it.cookName == dishDetail.cookName })
        }
    }
}

@Composable
fun Creator(dish: List<FoodDish>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {

        Text(
            "Creator",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Profile row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(dish[0].cookProfile)
                    .crossfade(true)
                    .error(R.drawable.avtar)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentDescription = dish[0].cookName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = colorResource(R.color.authScreenBgColor),
                        shape = CircleShape
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = dish[0].cookName,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Food Creator", // or dish[0].cookDesc if available
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Related Recipes section
        Text(
            text = "Related Recipes",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(10.dp))
        CreatorDishList(dish)
    }
}


@Composable
fun CreatorDishList(popularDishes: List<FoodDish>) {

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(popularDishes.size) { item ->
            CreatorDishListItem(popularDishes[item])
        }


    }
}

@Composable
fun CreatorDishListItem(item: FoodDish) {
    Card(
        modifier = Modifier
            .width(120.dp),
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
                modifier = Modifier
                    .width(80.dp)
                    .height(74.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Text(
                "${item.dishName}",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            )
        }
    }
}


@Composable
fun ExpandableText(dishDesc: String?, minimizedMaxLine: Int) {
    var isExpanded by remember { mutableStateOf(false) }
    var isTextOverflowing by remember { mutableStateOf(false) }

    // This is used to calculate if the text overflows max lines
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    Column {
        Text(
            dishDesc.toString(),
            maxLines = if (isExpanded) Int.MAX_VALUE else minimizedMaxLine,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult ->
                textLayoutResultState.value = textLayoutResult
                if (!isExpanded) {
                    isTextOverflowing = textLayoutResult.hasVisualOverflow
                }
            },
            style = TextStyle(
                fontSize = 16.sp,
                color = colorResource(R.color.light_grey)
            ),
            modifier = Modifier.animateContentSize()
        )

        // Only show View More/Less if text is overflowing or expanded
        if (isTextOverflowing || isExpanded) {
            Text(
                text = if (isExpanded) "View Less" else "View More",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable { isExpanded = !isExpanded }
            )
        }
    }
}

@Composable
fun NutrientsInfo(nutrients: Map<Int, Map<String, String>>) {
    if (nutrients.isEmpty()) return

    val rows = nutrients.entries.chunked(2) // Group into rows of 2 items

    Column(modifier = Modifier.fillMaxWidth()) {
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                rowItems.forEach { (imageResId, infoMap) ->
                    val (label, value) = infoMap.entries.first()

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .background(colorResource(R.color.lightest_grey)),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = imageResId),
                                contentDescription = label,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = value,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        )
                    }
                }

                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}


@Composable
fun TabsOptions(dishDetail: FoodDish, updatedIng: (MutableList<Map<String, String>>) -> Unit) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf<String>("Ingredients", "Instructions")
    TabRow(
        selectedTabIndex = selectedTab,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(color = colorResource(R.color.lightest_grey)),
        containerColor = colorResource(R.color.lightest_grey),
        indicator = {}
    ) {
        tabs.forEachIndexed { idx, title ->
            val isSelected = selectedTab == idx
            Tab(
                selected = isSelected,
                onClick = { selectedTab = idx },
                modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(if (isSelected) colorResource(R.color.tabBgColor) else Color.Transparent),

                text = {
                    Text(
                        text = title,
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = if (isSelected) Color.White else Color.Black
                        )

                    )
                }
            )

        }
    }

    Spacer(modifier = Modifier.height(15.dp))
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            "Ingredients",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "Add all to cart",
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.authScreenBgColor)
            ),
            modifier = Modifier.padding(0.dp, 0.dp, 16.dp, 0.dp)
        )
    }
    Spacer(modifier = Modifier.height(15.dp))
    when (selectedTab) {
        0 -> TabContent1(dishDetail.ingredients) { updatedIngList ->
            updatedIng(updatedIngList.toMutableList())
        }

        1 -> TabContent2()
    }
}


@Composable
fun TabContent1(
    ingredients: List<Map<String, String>>,
    updatedIng: (List<Map<String, String>>) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        IngredientsList(
            ingredients,
            modifier = Modifier
        ) { updatedIngList ->
            updatedIng(updatedIngList)
        }
    }
}

@Composable
fun TabContent2() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text("This is content for Tab 2")
    }
}

@Composable
fun IngredientListItem(
    item: Map<String, String>,
    updatedIngredient: (Map<String, String>) -> Unit
) {
    val ingredient = item.toMutableMap()
    var itemQty by remember {
        mutableStateOf(item["qty"]?.toIntOrNull()?.coerceIn(1, 20) ?: 1)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Image and name
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(colorResource(R.color.lightest_grey)),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item["image"])
                            .crossfade(true)
                            .error(R.drawable.taco)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .build(),
                        contentDescription = item["name"],
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = item["name"].orEmpty(),
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            // Qty Controls
            Row(verticalAlignment = Alignment.CenterVertically) {
                QuantityButton("-", R.color.authScreenBgColor, {
                    if (itemQty > 1) {
                        itemQty -= 1
                        ingredient["qty"] = itemQty.toString()
                        updatedIngredient(ingredient)
                    }
                }, {})

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = itemQty.toString(),
                    style = TextStyle(fontSize = 18.sp, color = Color.Black)
                )

                Spacer(modifier = Modifier.width(10.dp))

                QuantityButton("+", R.color.authScreenBgColor, {}, {
                    if (itemQty < 20) {
                        itemQty += 1
                        ingredient["qty"] = itemQty.toString()
                        updatedIngredient(ingredient)
                    }
                })
            }
        }
    }
}

@Composable
fun QuantityButton(
    text: String,
    colorResId: Int,
    minusClicked: (Boolean) -> Unit,
    plusClicked: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .border(
                width = 1.dp,
                color = colorResource(colorResId),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                if (text == "-") {
                    minusClicked(true)
                } else if (text == "+") {
                    plusClicked(true)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(colorResId)
            )
        )
    }
}


@Composable
fun IngredientsList(
    ingList: List<Map<String, String>>,
    modifier: Modifier = Modifier,
    onUpdatedList: (List<Map<String, String>>) -> Unit
) {
    val updatedList = remember { mutableStateListOf(*ingList.toTypedArray()) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        updatedList.forEachIndexed { index, item ->
            IngredientListItem(item) { updatedItem ->
                updatedList[index] = updatedItem
                onUpdatedList(updatedList)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun DishDetailPreview() {
    //  DishDetailScreen()
}