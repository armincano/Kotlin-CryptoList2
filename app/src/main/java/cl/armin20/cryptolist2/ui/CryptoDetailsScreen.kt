package cl.armin20.cryptolist2.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.armin20.cryptolist2.R
import cl.armin20.cryptolist2.ui.theme.*
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
@Preview(showSystemUi = true, device = Devices.NEXUS_6)
fun CryptoDetailsScreen() {
//    val textex1 = "bitcoin"
//    val textex2 = 123.2021902
    
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
    ) {
        Image(
            painter = painterResource(R.drawable.bg_details),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(900.dp),
            contentScale = ContentScale.FillWidth,
        )

        Column {
            SymbolSection()
            Spacer(modifier = Modifier.height(25.dp))
            CardSection()
            Spacer(modifier = Modifier.height(35.dp))
            OthersValuesSection()
        }



    }
}

@Composable
fun SymbolSection(){
    val cryptoDetailsViewModel:CryptoDetailsViewModel = viewModel()
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 5.dp)
        ) {
            Text(
//                text = "btc",
                    text = cryptoDetailsViewModel.cryptoDetail.value.data.symbol.lowercase(),
                style = MaterialTheme.typography.h2.copy(color = Color.White),
                overflow = TextOverflow.Ellipsis
            )
            Text(
//                text = "bitcoin",
                    text = cryptoDetailsViewModel.cryptoDetail.value.data.name,
                style = MaterialTheme.typography.h6.copy(color = Color.White),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding( start = 4.dp)
            )
        }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                        .data("https://static.coincap.io/assets/icons/${cryptoDetailsViewModel.cryptoDetail.value.data.symbol.lowercase()}@2x.png")
//                .data("https://static.coincap.io/assets/icons/bitcoin@2x.png")
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(85.dp)
                .padding(top = 7.dp),
//                    .clip(RoundedCornerShape(10.dp)),
//                    .background(Color.White),
//                    .clip(CircleCropTransformation())
//            error = painterResource(R.drawable.ic_baseline_broken_image),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun CardSection(){
    val cryptoDetailsViewModel:CryptoDetailsViewModel = viewModel()
//    val textex1 = "bitcoin"
//    val textex2 = 123.20219
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.center_box),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .scale(1.3f)
        )
        Text(text = "${cryptoDetailsViewModel.cryptoDetail.value.data.priceUsd}",
//            text = textex2.toString(),
            style = MaterialTheme.typography.h3.copy(color = Color.White),
            modifier = Modifier
                .padding(top = 25.dp, bottom = 8.dp, start = 80.dp, end = 90.dp)
                .align(Alignment.TopCenter),
            overflow = TextOverflow.Clip,
            maxLines = 1
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 100.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "US Dollar",
                style = MaterialTheme.typography.h5.copy(color = Color.White)
            )
            Text(
            text =  "${cryptoDetailsViewModel.getDateTime(cryptoDetailsViewModel.cryptoDetail.value.timestamp)} hrs.",
//                text = "12:23:23 hrs.",
                style = MaterialTheme.typography.h6.copy(color = Color.White)
            )
        }
    }
}
@Composable
fun OthersValuesSection(){
    val cryptoDetailsViewModel:CryptoDetailsViewModel = viewModel()
//    val textex1 = "bitcoin"
//    val textex2 = 123.20219


        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .size(width = 155.dp, height = 85.dp)
                .background(PurpleSoft)
        ) {
            Row(modifier = Modifier.padding(start = 25.dp)) {
                Text(text = "Supply", style = MaterialTheme.typography.h5.copy(color = Color.Black))
                Text(
                    text = cryptoDetailsViewModel.cryptoDetail.value.data.supply.toString(),
//                text = textex2.toString(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 12.dp, top = 6.dp)
                )
            }
            Row(Modifier.padding(start = 25.dp)) {
                Text(text = "Market cap", style = MaterialTheme.typography.h5.copy(color = Color.Black))
                Text(
                    text = cryptoDetailsViewModel.cryptoDetail.value.data.marketCapUsd.toString(),
//                text = textex2.toString(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 12.dp, top = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

    Box(
        modifier = Modifier
            .padding(horizontal = 40.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .size(width = 155.dp, height = 95.dp)
            .background(
                if (cryptoDetailsViewModel.cryptoDetail.value.data.changePercent24Hr.contains("-")) {
                    DecreaseValue
                } else {
                    IncreaseValue
                }
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
                        text = cryptoDetailsViewModel.cryptoDetail.value.data.changePercent24Hr,
//            text = textex2.toString(),
            style = MaterialTheme.typography.h4.copy(color = Blackest),
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp, start = 50.dp, end = 50.dp)
                .align(Alignment.TopCenter),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = "value change in the last 24 hrs.",
            modifier = Modifier.padding(top = 55.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            color = Blackest
        )
    }


}





