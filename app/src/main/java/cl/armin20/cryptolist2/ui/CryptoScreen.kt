package cl.armin20.cryptolist2.ui


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.armin20.cryptolist2.R
import cl.armin20.cryptolist2.data.model.Data
import coil.compose.AsyncImage
import coil.request.ImageRequest

//@Preview(showSystemUi = true, device = Devices.NEXUS_6)
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
    ){
        items(cryptoViewModel.cryptoList.value.data){
            CryptoListItem(
                it,
                onItemClick = { id -> onItemClick(id) })
        }
    }
}

//@Preview(showSystemUi = true, device = Devices.NEXUS_6)
@Composable
fun CryptoListItem(item: Data, onItemClick: (id: String) -> Unit){
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .size(70.dp)
            .clickable { onItemClick(item.id) }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.padding(8.dp)
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
                placeholder = painterResource(R.drawable.ic_baseline_sushi_24),
                contentScale = ContentScale.Fit
            )
            Text(
                text = item.symbol,
                modifier = Modifier.weight(0.3f)
            )
            Text(
                text = "USD$ ${item.priceUsd}",
                modifier = Modifier.weight(0.4f)
            )
        }
    }
}



