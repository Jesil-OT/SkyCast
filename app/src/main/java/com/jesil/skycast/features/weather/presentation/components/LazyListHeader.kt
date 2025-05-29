package com.jesil.skycast.features.weather.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.skycast.R

@Composable
fun LazyListHeader(
    modifier: Modifier = Modifier,
    headerTitle: @Composable () -> Unit,
    iconRes: ImageVector,
    contentDescription: String? = null
) {
    Column (
        modifier = modifier
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                modifier = Modifier.padding(
                    start = 20.dp,
                    top = 10.dp,
                    bottom = 5.dp,
                    end = 5.dp
                ),
                imageVector = iconRes,
                contentDescription = contentDescription,
                tint = Color.White.copy(.5f)
            )

            headerTitle.invoke()
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 20.dp),
            thickness = 1.dp,
            color = Color.White.copy(.2f)
        )
    }
}