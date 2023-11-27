package com.example.codechallangebankapp.features.loginScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.codechallangebankapp.R
import com.example.codechallangebankapp.core.components.TextFieldState
import com.example.codechallangebankapp.features.UiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    //recuperamos States del viewmodel
    val usernameState = viewModel.usernameState.value
    val passwordState = viewModel.passwordState.value
    val loginState = viewModel.loginState.value
    val scaffoldState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val usernameVisible by rememberSaveable { mutableStateOf(false) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val backgroundImage = painterResource(id = R.drawable.loginbackground)
    val keyboardController = LocalSoftwareKeyboardController.current
    //Se inicializa el evento inicial en la creacion de la pantalla de login
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                //Caso Error - Se muestra el snackbar con el mensaje de error
                is UiState.SnackbarEvent -> {
                    scope.launch {
                        scaffoldState.showSnackbar(
                            message = event.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
                //Caso Success - Se navega a la pantalla de home
                is UiState.NavigateEvent -> {
                    scope.launch {
                        scaffoldState.showSnackbar(
                            message = "Login Successful",
                            duration = SnackbarDuration.Short
                        )
                    }
                    navController.popBackStack()
                    //Agregamos el username al route para poder recuperarlo en la pantalla de home
                    val username = viewModel.usernameState.value.text
                    navController.navigate(event.route + "/$username")
                }
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(scaffoldState) },
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Image(
                    painter = backgroundImage,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    //Se muestra el loaderFullScreen cuando isLoading es true
                    if (loginState.isLoading) {
                        FullScreenLoader()
                    }
                    Spacer(modifier = Modifier.height(100.dp))
                    Image(
                        painterResource(id = R.drawable.piggyimage),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally)
                    )
                    Card(
                        modifier = Modifier
                            .padding(20.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.Transparent),
                        shape = MaterialTheme.shapes.medium
                    )
                    {
                        Spacer(modifier = Modifier.height(16.dp))
                        //Se muestra el TextField para el username sin emojis
                        TextWithOutEmoji(viewModel = viewModel, usernameState = usernameState, passwordState = passwordState, usernameVisible = usernameVisible)
                        Spacer(modifier = Modifier.height(16.dp))
                        //Se muestra el TextField para el password
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            shape = RoundedCornerShape(8.dp),
                            value = passwordState.text,
                            onValueChange = {
                                viewModel.setPassword(it)
                            },
                            label = { Text("Password") },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            isError = passwordState.error != null,
                            trailingIcon = {
                                val image = if (passwordVisible) Icons.Filled.Info else Icons.Filled.Lock
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(imageVector = image, "Hide password")
                                }
                            },
                            maxLines = 1,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = MaterialTheme.colorScheme.primary,
                                containerColor = MaterialTheme.colorScheme.background,
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                                placeholderColor = MaterialTheme.colorScheme.tertiary.copy(
                                    alpha = 0.5f
                                ),
                                unfocusedLeadingIconColor = MaterialTheme.colorScheme.tertiary.copy(
                                    alpha = 0.5f
                                )
                            )
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        //Se muestra el botón de login
                        Button(
                            onClick = {
                                //Se realiza la petición de login que llamara a futuro a nuestra API
                                viewModel.loginUser()
                                //Se oculta el teclado
                                keyboardController?.hide()
                            },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            Text(
                                text = "Continue",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(vertical = 6.dp)
                            )
                        }
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(onClick = {
                            }) {
                                Text(
                                    text = "Forgot Password?",
                                    color = Color.Black,
                                    modifier = Modifier.padding(horizontal = 20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    )
}
fun containsEmoji(input: String): Boolean {
    val emojiPattern = Regex("[\uD83C-\uDBFF\uDC00-\uDFFF\uD800-\uDBFF\uDC00-\uDFFF\u2600-\u2B55\u23F0-\u23F3\u231A\u231B\u23E9-\u23EC\u23F8-\u23FA🥺]")
    return emojiPattern.containsMatchIn(input)
}

@Composable
fun FullScreenLoader() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column (modifier = Modifier
            .fillMaxSize()
            .align(Alignment.Center))
        {
            Spacer(modifier = Modifier.height(120.dp))
            Image(
                painterResource(id = R.drawable.piggyimage),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            CircularProgressIndicator(
                modifier = Modifier
                    .size(80.dp)
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextWithOutEmoji(
    viewModel : LoginViewModel,
    usernameState : TextFieldState,
    passwordState : TextFieldState,
    usernameVisible : Boolean
){
    var updatedUsernameVisible by rememberSaveable { mutableStateOf(usernameVisible)}
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(8.dp),
        value = usernameState.text,
        onValueChange = {
            if (!containsEmoji(it)) {
                viewModel.setUsername(it)
            }
        },
        label = { Text("Usuario") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        isError = passwordState.error != null,
        trailingIcon = {
            val image = if (updatedUsernameVisible)
                Icons.Filled.Info
            else Icons.Filled.Lock
            val description =
                if (updatedUsernameVisible) "Hide username" else "Show username"
            IconButton(onClick = { updatedUsernameVisible = !updatedUsernameVisible }) {
                Icon(imageVector = image, description)
            }
        },
        maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.background,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            placeholderColor = MaterialTheme.colorScheme.tertiary.copy(
                alpha = 0.5f
            ),
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.tertiary.copy(
                alpha = 0.5f
            )
        )
    )

}