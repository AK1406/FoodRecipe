package com.anju.foodrecipe.registration

import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.anju.foodrecipe.AuthState
import com.anju.foodrecipe.R
import com.anju.foodrecipe.ui.theme.customTextFieldColors
import com.anju.foodrecipe.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel?
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }

    val authstate = authViewModel?.authState?.observeAsState()
    val context = LocalContext.current

    if (authstate != null) {
        LaunchedEffect(authstate.value) {
            if (authstate != null) {
                when (authstate.value) {
                    is AuthState.Authenticated -> {
                        navController.navigate("home") {
                            popUpTo("auth") { inclusive = true }
                        }
                    }

                    is AuthState.Error -> Toast.makeText(
                        context,
                        (authstate.value as AuthState.Error).msg,
                        Toast.LENGTH_SHORT
                    ).show()

                    else -> Unit
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Hi there!",
            style = TextStyle(
                fontSize = 25.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Create your account",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(R.drawable.sign_img),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentDescription = "sign-up banner"
        )
        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(value = email, onValueChange = {
            email = it
        }, label = {
            Text("Email")
        }, colors = customTextFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = fullName, onValueChange = {
            fullName = it
        }, label = {
            Text("Full Name")
        }, colors = customTextFieldColors(),modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Password")
            },colors = customTextFieldColors(),
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (authstate != null) {
            Button(
                onClick = {
                    authViewModel.signup(email, password, fullName)
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.authScreenBgColor),
                    contentColor = Color.White
                ),
                enabled = authstate.value != AuthState.Loader
            ) {
                Text("Create account")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        TextButton(onClick = { navController.navigate("login") }) {
            Text(
                "Have an account, login",
                color = colorResource(R.color.authScreenBgColor)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    val navController = rememberNavController()

    SignUpScreen(
        navController = navController,
        authViewModel = null
    )
}