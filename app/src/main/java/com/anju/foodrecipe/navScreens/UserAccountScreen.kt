package com.anju.foodrecipe.navScreens

import android.util.Log
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.anju.foodrecipe.R
import com.anju.foodrecipe.model.FoodDish
import com.anju.foodrecipe.viewmodel.DishesViewModel

@Composable
fun UserAccountScreen(modifier: Modifier = Modifier, viewModel: DishesViewModel) {

    val favouriteDishList = viewModel.favouriteDishesList

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Account", style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    fontSize = 20.sp
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Outlined.Settings, contentDescription = "Settings")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(4.dp)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp, 5.dp, 10.dp, 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.avtar),
                    "user profile", modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Alena Sabyan", style =
                            TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black,
                                fontSize = 16.sp
                            )
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        "Recipe Developer", style =
                            TextStyle(
                                fontWeight = FontWeight.Normal,
                                color = colorResource(R.color.light_grey),
                                fontSize = 16.sp
                            )
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
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
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                "Favourites",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "See all",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(R.color.authScreenBgColor)
                ),
                modifier = Modifier.padding(0.dp, 0.dp, 16.dp, 0.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (favouriteDishList.isNotEmpty()) {
            FavouriteDishList(favouriteDishList)
        }

    }

}


@Composable
fun FavouriteDishes(item: FoodDish) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .width(200.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(4.dp),
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
                    model = ImageRequest.Builder(context)
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
                    Icons.Filled.Favorite,
                    contentDescription = "cook Image",
                    tint = colorResource(R.color.authScreenBgColor),
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color.White)
                        .padding(6.dp)
                        .align(Alignment.TopEnd),
                )
            }

            Text(
                "${item.dishName}",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
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
                    text = "James Spader",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = colorResource(R.color.light_grey)
                    )
                )
            }

        }
    }
}


@Composable
fun FavouriteDishList(dishList: List<FoodDish>) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(dishList.size) { dish ->
            FavouriteDishes(dishList[dish])
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun UserAccountPreview() {
    //UserAccountScreen()
}