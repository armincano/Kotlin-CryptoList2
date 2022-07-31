package cl.armin20.cryptolist2.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.armin20.cryptolist2.R
import cl.armin20.cryptolist2.ui.theme.DecreaseValue
import cl.armin20.cryptolist2.ui.theme.IncreaseValue
import cl.armin20.cryptolist2.ui.theme.PurpleSoft
import cl.armin20.cryptolist2.ui.theme.SilverSoft
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
@Preview(showSystemUi = true, device = Devices.NEXUS_6)
fun CryptoDetailsScreen() {
    val cryptoDetailsViewModel:CryptoDetailsViewModel = viewModel()
    val textex1 = "bitcoin"
    val textex2 = 123.2021902
    
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

        Column() {
            SymbolSection()
            CardSection()
            Spacer(modifier = Modifier.height(46.dp))
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
            .padding(15.dp)
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
            placeholder = painterResource(R.drawable.ic_baseline_sushi_24),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun CardSection(){
    val cryptoDetailsViewModel:CryptoDetailsViewModel = viewModel()
    val textex1 = "bitcoin"
    val textex2 = 123.20219
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.center_box),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .scale(1.5f)
                .padding(top = 30.dp)
        )
        Text(
                        text = "${cryptoDetailsViewModel.cryptoDetail.value.data.priceUsd}",
//            text = textex2.toString(),
            style = MaterialTheme.typography.h3.copy(color = Color.White),
            modifier = Modifier
                .padding(top = 60.dp, bottom = 8.dp, start = 75.dp, end = 90.dp)
                .align(Alignment.TopCenter),
            overflow = TextOverflow.Clip,
            maxLines = 1
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 90.dp, top = 145.dp)
        ) {
            Text(
                text = "US Dollar",
                style = MaterialTheme.typography.h6.copy(color = Color.White)
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
    val textex1 = "bitcoin"
    val textex2 = 123.20219

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .size(width = 155.dp, height = 90.dp)
                .background(PurpleSoft)
                .padding(12.dp)
        ) {
            Text(text = "Supply", style = MaterialTheme.typography.h5,modifier = Modifier.padding(start = 5.dp))
            Text(
            text = cryptoDetailsViewModel.cryptoDetail.value.data.supply.toString(),
//                text = textex2.toString(),
                modifier = Modifier.padding(top = 5.dp, start = 5.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .size(width = 155.dp, height = 90.dp)
                .background(PurpleSoft)
                .padding(12.dp)
        ) {
            Text(text = "Market cap", style = MaterialTheme.typography.h5,modifier = Modifier.padding(start = 5.dp))
            Text(
            text = cryptoDetailsViewModel.cryptoDetail.value.data.marketCapUsd.toString(),
//                text = textex2.toString(),
                modifier = Modifier.padding(top = 5.dp, start = 5.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    Box(
        modifier = Modifier
            .padding(horizontal = 35.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .size(width = 155.dp, height = 95.dp)
            .background(
                if(cryptoDetailsViewModel.cryptoDetail.value.data.changePercent24Hr.contains("-")){
                    DecreaseValue} else {IncreaseValue}
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
                        text = cryptoDetailsViewModel.cryptoDetail.value.data.changePercent24Hr,
//            text = textex2.toString(),
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 8.dp, start = 50.dp, end = 50.dp)
                .align(Alignment.TopCenter),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = "value change in the last 24 hrs.",
            modifier = Modifier.padding(top = 55.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }


}





