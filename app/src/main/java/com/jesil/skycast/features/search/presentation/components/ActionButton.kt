package com.jesil.skycast.features.search.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.jesil.skycast.R
import com.jesil.skycast.ui.theme.SkyCastTheme

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.background.copy(.2f))
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null,
            )
            .padding(horizontal = 20.dp, vertical = 10.dp),
        content = {
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(.9f)
            )
        }
    )
}

@Composable
fun ActionButtons(
    modifier: Modifier = Modifier,
    onCancel: () -> Unit,
    onCityAdded: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ActionButton(
            onClick = onCancel,
            text = stringResource(R.string.cancel)
        )
        ActionButton(
            onClick = onCityAdded,
            text = stringResource(R.string.add_city)
        )
    }
}

@PreviewLightDark
@Composable
private fun ActionButtonPreview() {
    SkyCastTheme {
       ActionButtons(
           modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(10.dp),
           onCancel = {},
           onCityAdded = {}
       )
    }
}