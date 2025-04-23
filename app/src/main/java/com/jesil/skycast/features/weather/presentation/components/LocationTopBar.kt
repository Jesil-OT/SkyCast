package com.jesil.skycast.features.weather.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.jesil.skycast.R
import com.jesil.skycast.ui.theme.ClearSky
import com.jesil.skycast.ui.theme.SkyCastTheme

@Composable
fun LocationTopBar(
    date: String,
    temperatureUnit: String = "°C",
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit,
    backgroundColor: Color,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(backgroundColor)
    ) {
        val (navIcon, locationText, unitText) = createRefs()
        createHorizontalChain(navIcon, locationText, unitText, chainStyle = ChainStyle.SpreadInside)

        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = stringResource(R.string.menu),
            tint = Color.White,
            modifier = Modifier
                .padding(start = 30.dp)
                .clickable { onNavigationClick() }
                .constrainAs(navIcon) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )

        Text(
            text = date,
            style = MaterialTheme.typography.displayMedium,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(locationText) {
                    start.linkTo(navIcon.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }

        )

        Text(
            text = temperatureUnit,
            style = MaterialTheme.typography.displayMedium,
            color = Color.White,
            modifier = Modifier
                .padding(end = 30.dp)
                .constrainAs(unitText) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(locationText.end)
                }
        )

    }
}

@Preview
@Composable
private fun LocationTopBarPreview() {
    SkyCastTheme {
        LocationTopBar(
            date = "Lagos, NG",
            onNavigationClick = {},
            backgroundColor = ClearSky
        )
    }

}