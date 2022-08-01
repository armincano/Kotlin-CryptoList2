package cl.armin20.cryptolist2.ui


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
            StickyHeader(cryptoViewModel)
        }
        items(cryptoViewModel.cryptoList.value.data){
            CryptoListItem(
                it,
                onItemClick = { id -> onItemClick(id) })
        }
    }
}

@Composable
fun StickyHeader(cryptoViewModel:CryptoViewModel){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(PurpleSoft)
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = "Last updated: ${cryptoViewModel.getDateTime(cryptoViewModel.cryptoList.value.timestamp)}",
            style = MaterialTheme.typography.h6.copy(color = Color.White),
            modifier = Modifier.weight(0.7f)
        )
        Button(
            onClick = { GlobalScope.launch(Dispatchers.IO) { cryptoViewModel.getCoins() }},
            modifier = Modifier
                .size(width = 120.dp, height = 40.dp)
                .weight(0.3f)
        ) {
            Text(text = "UPDATE")
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
            .clickable { onItemClick(item.id) }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
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



