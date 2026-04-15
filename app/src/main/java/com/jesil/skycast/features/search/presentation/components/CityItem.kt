package com.jesil.skycast.features.search.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.skycast.features.search.model.SearchCitiesStateUi
import com.jesil.skycast.ui.theme.SkyCastTheme

@Composable
fun CityItem(
    modifier: Modifier = Modifier,
    item: SearchCitiesStateUi,
    onItemClicked : (lat: String, long: String) -> Unit
) {
    Box(
        modifier = modifier.clickable { onItemClicked(item.latitude, item.longitude) }
            .padding(horizontal = 20.dp, vertical = 10.dp),
        content = {
            Text(
                text = item.cityName,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    )
}

@PreviewLightDark
@Composable
private fun CityItemPreview() {
    SkyCastTheme {
        CityItem(
            modifier = Modifier.fillMaxWidth(),
            item = SearchCitiesStateUi(
                cityName = "Lagos, NG",
                latitude = "6.45",
                longitude = "9.68",
            ),
            onItemClicked = { _, _ -> }
        )
    }
}