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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anju.foodrecipe.R
import com.anju.foodrecipe.model.DishDetailModel
import com.anju.foodrecipe.model.IngredientModel



@Composable
fun DishDetailScreen() {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val sheetHeight = screenHeight * 0.75f
    val imageHeight = screenHeight * 0.5f

    Box(modifier = Modifier.fillMaxSize()) {
        // Top Image occupying 1/4th of the screen

        Image(
            painter = painterResource(R.drawable.dish_detail_bg),
            contentDescription = "Dish Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight),
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Favourite",
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(6.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                Icons.Outlined.FavoriteBorder,
                contentDescription = "Close",
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(6.dp)
            )
        }

        // Bottom sheet over image
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(sheetHeight)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = Color.White,
            tonalElevation = 8.dp
        ) {
            // Scrollable content
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                BottomSheetContent()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    dishDetail: DishDetailModel = DishDetailModel(
        "This Healthy Taco Salad is the universal delight of taco night This Healthy Taco Salad is the universal delight of taco night ",
        "20 mins",
        mapOf(
            R.drawable.carbs to ("carbs" to "65g carbs"),
            R.drawable.proteins to ("protien" to "20g protien"),
            R.drawable.fats to ("fat" to "10g fat"),
            R.drawable.kcal to ("calories" to "120 Kcal")
        ),
        mapOf(
            "Tortilla Chips" to 2,
            "Avocado" to 1,
            "Red Cabbage" to 9,
            "Peanuts" to 1,
            "Red onions" to 2
        ),
        "James Spader",
        "I'm the author and recipe developer.",
        R.drawable.avtar
    )
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp, 16.dp, 16.dp, 0.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Healthy Taco Salad",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.clock),
                    contentDescription = "time to cook",
                    modifier = Modifier.size(22.dp),
                    colorFilter = ColorFilter.tint(colorResource(R.color.light_grey))
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "${dishDetail.cookingTime}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = colorResource(R.color.light_grey)
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        ExpandableText(dishDetail.desc, 2)
        Spacer(modifier = Modifier.height(10.dp))
        NutrientsInfo(dishDetail.nutrientsInfo)
        Spacer(modifier = Modifier.height(10.dp))
        TabsOptions()
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {}, modifier = Modifier.fillMaxWidth(),
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
fun NutrientsInfo(nutrients: Map<Int, Pair<String, String>>?) {
    if (nutrients.isNullOrEmpty()) return

    val rows = nutrients.entries.chunked(2) // Split into rows of 2 items each

    Column(modifier = Modifier.fillMaxWidth()) {
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                rowItems.forEach { (image, pairInfo) ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .background(colorResource(R.color.lightest_grey)),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = image),
                                contentDescription = pairInfo.first,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = pairInfo.second,
                            style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        )
                    }
                }

                // Fill space if row has less than 2 items (only needed if odd count)
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}


@Composable
fun TabsOptions() {
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
        0 -> TabContent1()
        1 -> TabContent2()
    }
}


@Composable
fun TabContent1() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        IngredientsList()
    }
}

@Composable
fun TabContent2() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text("This is content for Tab 2")
    }
}

@Composable
fun IngredientListItem(item: IngredientModel) {
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
                    Image(
                        painter = painterResource(item.ingImage),
                        contentDescription = "Ingredient Image",
                        modifier = Modifier
                            .size(30.dp) // smaller to fit inside padding
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "${item.ingName}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            // Qty Controls
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                QuantityButton("-", R.color.authScreenBgColor)
                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = item.qty.toString(),
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.width(10.dp))
                QuantityButton("+", R.color.authScreenBgColor)
            }
        }
    }
}

@Composable
fun QuantityButton(text: String, colorResId: Int) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .border(
                width = 1.dp,
                color = colorResource(colorResId),
                shape = RoundedCornerShape(8.dp)
            ),
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
fun IngredientsList() {
    val ingList = listOf<IngredientModel>(
        IngredientModel(R.drawable.tortilla_chips, "Tortilla chips", 2),
        IngredientModel(R.drawable.avacado, "Avacado", 1),
        IngredientModel(R.drawable.red_cabbage, "Red cabbage", 1),
        IngredientModel(R.drawable.peanuts, "Peanuts", 9),
        IngredientModel(R.drawable.red_onion, "Red onion", 2)
    )
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(ingList.size) { item ->
            IngredientListItem(ingList[item])
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun DishDetailPreview() {
    DishDetailScreen()
}