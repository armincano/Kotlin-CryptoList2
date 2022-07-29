package cl.armin20.cryptolist2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.armin20.cryptolist2.R
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
@Preview(showSystemUi = true, device = Devices.NEXUS_6)
fun CryptoDetailsScreen() {
    val cryptoDetailsViewModel:CryptoDetailsViewModel = viewModel()

    Column {
        Card(
            elevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://static.coincap.io/assets/icons/${cryptoDetailsViewModel.cryptoDetail.value.data.symbol.lowercase()}@2x.png")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(top = 20.dp),
//                    .clip(RoundedCornerShape(10.dp)),
//                    .background(Color.White),
//                    .clip(CircleCropTransformation())
                    placeholder = painterResource(R.drawable.ic_baseline_sushi_24),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = cryptoDetailsViewModel.cryptoDetail.value.data.symbol,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(top = 5.dp)
                )
                Text(
                    text = cryptoDetailsViewModel.cryptoDetail.value.data.name,
                    modifier = Modifier.padding(top = 5.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${cryptoDetailsViewModel.cryptoDetail.value.data.priceUsd}",
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp),
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        Text(
            text = "Currency is US Dollar",
            modifier = Modifier.padding(top = 10.dp, start = 20.dp)
        )
        Text(
            text = "Supply\t\t\t${cryptoDetailsViewModel.cryptoDetail.value.data.supply}",
            modifier = Modifier.padding(top = 10.dp, start = 20.dp)
        )
        Text(
            text = "Market Cap\t\t\t${cryptoDetailsViewModel.cryptoDetail.value.data.marketCapUsd}",
            modifier = Modifier.padding(top = 10.dp, start = 20.dp)
        )
        Text(
            text =  "Last updated at ${cryptoDetailsViewModel.getDateTime(cryptoDetailsViewModel.cryptoDetail.value.timestamp)}",
            modifier = Modifier.padding(top = 10.dp, start = 20.dp)
        )
    }
}