package com.jesil.skycast.features.search.presentation

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
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
import java.util.Locale

@Composable
fun SearchCitiesScreen(
    state: SearchCitiesViewState,
    searchKeyword: String = "",
    onActions: (SearchCitiesAction) -> Unit,
    navController: NavController
) {
    val context = LocalContext.current

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    val recordAudioPermission = Manifest.permission.RECORD_AUDIO

    val speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenText = result.data
                ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ?.firstOrNull()
            spokenText?.let {
                onActions(SearchCitiesAction.SearchCity(it))
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startVoiceRecognition(context, speechLauncher)
        } else {
            Toast.makeText(context, "Microphone permission denied", Toast.LENGTH_SHORT).show()
        }
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
                        onMicClick = {
                            when {
                                ContextCompat.checkSelfPermission(context, recordAudioPermission) ==
                                        PackageManager.PERMISSION_GRANTED -> {
                                    startVoiceRecognition(context, speechLauncher)
                                }
                                else -> {
                                    permissionLauncher.launch(recordAudioPermission)
                                }
                            }
                        }
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

private fun startVoiceRecognition(context: Context, launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a city name")
    }
    try {
        launcher.launch(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "Voice input not supported", Toast.LENGTH_SHORT).show()
    }
}