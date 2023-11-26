package com.example.codechallangebankapp.features.detailScreen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import com.example.codechallangebankapp.domain.models.AccountMovement

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    nameAccount : String,
    amountAccount : String,
    numberAccount : String,
    viewModel: DetailViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getAccountMovementsList(numberAccount)
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Consultas", color = Color.White) },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )
    }
    ){innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            cardAccount(nameAccount,amountAccount,numberAccount)
            Spacer(modifier = Modifier.height(8.dp))

            MovementsResultsList(viewModel)
        }
    }
}


@Composable
fun cardAccount(
    nameAccount: String,
    amountAccount: String,
    numberAccount: String
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(top = 18.dp)
            .padding(horizontal = 18.dp)
    )
    {
        Column {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
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
                        text = nameAccount,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = amountAccount,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                }
            }
            Row (modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
            ){
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = "Numero de cuenta",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = numberAccount,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                }
                Button(
                    onClick = {
                    },
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.Bottom),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Compartir",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MovementsResultsList(
    viewModel: DetailViewModel
) {
    //recuperamos el estado del viewmodel
    val resultState = viewModel.accountsMovementsState.value
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
        Modifier.fillMaxSize()
            .padding(top = 18.dp)
            .padding(horizontal = 18.dp)
        LazyColumn {
            items(recipeListData.size) { index ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(top = 18.dp)
                        .padding(horizontal = 18.dp)
                )
                {
                    Item(recipeListData[index])
                }
            }
        }
    }
}

@Composable
fun Item(recipeListData: AccountMovement) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = recipeListData.descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = recipeListData.fecha,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                fontSize = 14.sp
            )
        }
        Column (modifier = Modifier
            .padding(start = 16.dp)
            .align(Alignment.CenterVertically)
        ){
            Text(
                text = "S/ " + recipeListData.monto.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = if (recipeListData.monto > 0) MaterialTheme.colorScheme.primary else Color.Red,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}