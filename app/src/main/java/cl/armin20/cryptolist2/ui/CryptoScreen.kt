package cl.armin20.cryptolist2.ui


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.armin20.cryptolist2.CryptoList2Application
import cl.armin20.cryptolist2.data.datastore.WelcomeViewModel
import cl.armin20.cryptolist2.data.model.Data
import cl.armin20.cryptolist2.ui.theme.PurpleSoft
import cl.armin20.cryptolist2.ui.theme.SilverSoft
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//@Preview(showSystemUi = true, device = Devices.NEXUS_6)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CryptoScreen(onItemClick: (id: String) -> Unit){

    //Instanciar el ViewModel de forma correcta, sin entregar parámetros
    val cryptoViewModel:CryptoViewModel = viewModel()
    LazyColumn(
        contentPadding = PaddingValues(
            vertical = 8.dp,
            horizontal = 8.dp
        ),
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleSoft)
    ){
        stickyHeader {
            StickyHeader(cryptoViewModel, onItemClick = { id -> onItemClick(id) })
        }
        items(
            if (cryptoViewModel.searchTextField.value.isEmpty())
            {
                cryptoViewModel.cryptoList.value.data
            }else
            cryptoViewModel.cryptoList.value.data.filter {
            it.id.contains(cryptoViewModel.searchTextField.value)
        }
        ){
            CryptoListItem(
                it,
                onItemClick = { id -> onItemClick(id) })

        }
    }

}

@Composable
fun StickyHeader(cryptoViewModel:CryptoViewModel, onItemClick: (id: String) -> Unit ){

    val welcomeViewModel: WelcomeViewModel = viewModel()
    welcomeViewModel.getName("user_name", CryptoList2Application.getAppContext())

    Row(horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = CenterVertically,
        modifier = Modifier
            .background(PurpleSoft)
            .fillMaxWidth()
    ) {
        Button(
            onClick = { onItemClick("addUser")},
            modifier = Modifier
                .size(width = 1900.dp, height = 40.dp)
                .padding(horizontal = 19.dp)
        ) {

            Text(
                text = "You are ${welcomeViewModel.userName}.",
                style = MaterialTheme.typography.h6.copy(color = Color.White),
                modifier = Modifier.padding(horizontal = 20.dp)
            )

//            Text(
//                text = cryptoViewModel.getDateTime(cryptoViewModel.cryptoList.value.timestamp),
//                style = MaterialTheme.typography.h6.copy(color = Color.White),
//            )

            Icon(
                imageVector = Icons.Filled.Create ,contentDescription = null,
                tint = Color.White)

        }
    }

    Row(horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = CenterVertically,
        modifier = Modifier
            .background(PurpleSoft)
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        val textState = remember { mutableStateOf("") }
        cryptoViewModel.searchTextField.value = textState.value

        TextField(
            value = textState.value,
            onValueChange = { newValue ->
                textState.value = newValue
            },
            label = { Text("Hi! Write to search") },
            textStyle = MaterialTheme.typography.h6.copy(color = Color.White)
        )

        Button(
            onClick = { GlobalScope.launch(Dispatchers.IO) { cryptoViewModel.getCoins() }},
            modifier = Modifier
                .size(width = 70.dp, height = 50.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ){
//                Text(
//                    text = cryptoViewModel.getDateTime(cryptoViewModel.cryptoList.value.timestamp),
//                    style = MaterialTheme.typography.h6.copy(color = Color.White),
//                )
                Icon(
                    imageVector = Icons.Filled.Refresh,contentDescription = null,
                    tint = Color.White)
            }

        }
    }


}

//@Preview(showSystemUi = true, device = Devices.NEXUS_6)
@Composable
fun CryptoListItem(item: Data, onItemClick: (id: String) -> Unit){
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .size(height = 90.dp, width = 0.dp)
            .clickable { onItemClick("cryptocoins/${item.id}") }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = CenterVertically,
            modifier = Modifier.background(SilverSoft)
        ) {
//            CryptoIcon(Icons.Filled.Place, Modifier.weight(0.15f))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://static.coincap.io/assets/icons/${item.symbol.lowercase()}@2x.png")
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .weight(0.3f)
                    .size(60.dp),
//                    .clip(RoundedCornerShape(10.dp)),
//                    .background(Color.White),
//                    .clip(CircleCropTransformation())
//                placeholder = painterResource(R.drawable.ic_baseline_face1),
                contentScale = ContentScale.Fit
            )
            Text(
                text = item.symbol.lowercase(),
                style = MaterialTheme.typography.h4.copy(color = Color.White),
                modifier = Modifier.weight(0.3f)
            )
            Text(
                text = item.priceUsd.toString(),
                style = MaterialTheme.typography.h6.copy(color = Color.White),
                modifier = Modifier
                    .weight(0.4f)
                    .padding(end = 25.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}



