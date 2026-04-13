package com.jesil.skycast.features.search.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jesil.skycast.R
import com.jesil.skycast.features.search.model.SearchCitiesStateUi
import com.jesil.skycast.features.search.model.SearchCitiesViewState
import com.jesil.skycast.features.search.presentation.components.CityItem
import com.jesil.skycast.features.search.presentation.components.SearchBar
import com.jesil.skycast.features.search.presentation.events.SearchCitiesAction
import com.jesil.skycast.ui.theme.SkyCastTheme
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun SearchCitiesScreen(
    state: SearchCitiesViewState,
    searchKeyword: String = "",
    onActions: (SearchCitiesAction) -> Unit,
    navController: NavController
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    SearchBar(
                        modifier = Modifier.weight(1f).focusRequester(focusRequester),
                        textValue = searchKeyword,
                        onValueChange = { onActions(SearchCitiesAction.SearchCity(it)) },
                        onClearText = { onActions(SearchCitiesAction.ClearCity(searchKeyword)) },
                        onMicClick = { onActions(SearchCitiesAction.RecordWithMic) }
                    )
                    Text(
                        text = stringResource(R.string.cancel),
                        modifier = Modifier.clickable{ navController.navigateUp()
                        }.padding(start = 10.dp),
                        textDecoration = TextDecoration.Underline
                    )
                }
            )
        },
        content = { paddingValues ->
            when(state){
                is SearchCitiesViewState.EmptyState -> {}
                is SearchCitiesViewState.Success -> {
                    Column(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                    ) {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            content = {
                                items(state.data){ city ->
                                    CityItem(
                                        modifier = Modifier.fillMaxWidth(),
                                        item = city,
                                        onItemClicked = { lat, long ->
                                            onActions(SearchCitiesAction.SelectCity(lat, long))
                                        }
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
private fun SearchCitiesScreenPreview() {
    SkyCastTheme {
        SearchCitiesScreen(
            state = SearchCitiesViewState.Success(
                data = fakeSearchCitiesList
            ),
            onActions = {},
            navController = rememberNavController(),
            searchKeyword = "",
        )
    }
}

private val fakeSearchCitiesList = listOf(
    SearchCitiesStateUi(
        cityName = "Lagos, NG",
        latitude = 6.45,
        longitude = 9.68,
    ),
    SearchCitiesStateUi(
        cityName = "London, UK",
        latitude = 51.50,
        longitude = -0.12,
    ),
    SearchCitiesStateUi(
        cityName = "Paris, FR",
        latitude = 48.85,
        longitude = 2.39,
    ),
    SearchCitiesStateUi(
        cityName = "New York, US",
        latitude = 40.7,
        longitude = -74.0,
    )
)