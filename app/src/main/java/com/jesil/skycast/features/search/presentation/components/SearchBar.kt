package com.jesil.skycast.features.search.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesil.skycast.R
import com.jesil.skycast.ui.theme.SkyCastTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    textValue: String,
    onValueChange: (String) -> Unit,
    onClearText: () -> Unit,
    onMicClick: () -> Unit
) {
    val backgroundColor = if (isSystemInDarkTheme()) Color.White else Color.Gray.copy(.5f)
    val buttonTint = if (isSystemInDarkTheme()) Color.Black.copy(.5f) else Color.White.copy(.5f)

    BasicTextField(
        value = textValue,
        onValueChange = onValueChange,
        modifier = modifier,
        maxLines = 1,
        cursorBrush = Brush.horizontalGradient(
            colors = listOf(buttonTint, buttonTint)
        ),
        textStyle = MaterialTheme.typography.bodyLarge,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(
                    color = backgroundColor,
                ),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Icon(
                        modifier = Modifier.padding(5.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.Black.copy(.5f)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f),
                        content = {
                            if (textValue.isEmpty()){
                                Text(
                                    text = "Search for a location",
                                    fontSize = 13.sp,
                                    color = Color.Black.copy(.5f)
                                )
                            }
                            innerTextField()
                        }
                    )
                    if (textValue.isNotEmpty()){
                        IconButton(
                            modifier = Modifier.padding(start = 5.dp, end = 10.dp).size(20.dp),
                            onClick = onClearText,
                            colors = IconButtonColors(
                                containerColor = buttonTint,
                                contentColor = Color.White,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = Color.Transparent
                            ),
                            content = {
                                Icon(
                                    modifier = Modifier.size(15.dp),
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = "Clear",
                                )
                            }
                        )
                    } else {
                        IconButton(
                            modifier = Modifier.padding(start = 5.dp, end = 5.dp).size(23.dp),
                            onClick = onMicClick,
                            content = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_mic),
                                    contentDescription = "Search with mic",
                                    tint = Color.Black.copy(.5f)
                                )
                            }
                        )
                    }
                }
            )
        }
    )
}

@PreviewLightDark
@Composable
private fun SearchBarPreview() {
    SkyCastTheme {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            textValue = "",
            onValueChange = {},
            onClearText = {},
            onMicClick = {}
        )
    }
}