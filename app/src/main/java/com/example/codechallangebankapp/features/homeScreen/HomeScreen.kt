package com.example.codechallangebankapp.features.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.codechallangebankapp.R
import com.example.codechallangebankapp.domain.models.AccountsModel
import com.example.codechallangebankapp.features.navigation.AppScreens
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    username: String,
    viewModel: HomeViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getAccountsList(username)
    }
    val isLoading by viewModel.isLoadingSwipe.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Productos", color = Color.White) },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )
    }
    ) { innerPadding ->

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = viewModel::loadSwipeRefresh,
            indicator = { state, refreshTrigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = refreshTrigger,
                    backgroundColor = Color.Blue,
                    contentColor = Color.White
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                SearchResultsList(viewModel, navController, username)
            }
        }
    }
}


@Composable
fun SearchResultsList(
    viewModel: HomeViewModel,
    navController: NavController,
    username: String?
) {
    //recuperamos el estado del viewmodel
    val resultState = viewModel.accountsState.value
    //si esta cargando mostramos el progressbar
    if (resultState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    //si hay un error mostramos el mensaje, este mensaje contiene el error de la llamada a la api
    if (resultState.error.isNotBlank()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 60.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "resultState.error")
        }
    }
    //si el homeState tiene datos mostramos la lista
    resultState.data?.let { recipeListData ->
        Modifier
            .padding(top = 18.dp)
            .padding(horizontal = 18.dp)
        LazyColumn {
            items(recipeListData.cuentasBancarias.size) { index ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(top = 18.dp)
                        .padding(horizontal = 18.dp)
                        .clickable(
                            onClick = {
                                //navegamos a la pantalla de detalle
                                val nameAccount = recipeListData.cuentasBancarias[index].tipoCuenta
                                val amountAccount = recipeListData.cuentasBancarias[index].saldo.toString()
                                val numberAccount = recipeListData.cuentasBancarias[index].numeroCuenta
                                navController.navigate(AppScreens.DetailScreen.route + "/$nameAccount" + "/$amountAccount" + "/$numberAccount")
                            }
                        )
                )
                {
                    ItemView(recipeListData, index)
                }
            }
        }
    }
}

@Composable
fun ItemView(recipeListData: AccountsModel, index: Int) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Image(
            painterResource(id = R.drawable.piggyimage),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = recipeListData.cuentasBancarias[index].tipoCuenta,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = recipeListData.cuentasBancarias[index].saldo.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}