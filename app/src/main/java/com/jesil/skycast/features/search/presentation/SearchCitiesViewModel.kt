package com.jesil.skycast.features.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.skycast.data.mapper.toSearchCitiesUI
import com.jesil.skycast.data.repository.search_cities.SearchWeatherRepository
import com.jesil.skycast.features.search.model.SearchCitiesViewState
import com.jesil.skycast.features.search.presentation.events.SearchCitiesAction
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

@OptIn(FlowPreview::class)
class SearchCitiesViewModel(
    private val searchWeatherRepository: SearchWeatherRepository
): ViewModel(), KoinComponent {

    private val _searchCitiesViewState = MutableStateFlow<SearchCitiesViewState>(SearchCitiesViewState.EmptyState)

    val searchCitiesViewState = _searchCitiesViewState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SearchCitiesViewState.EmptyState
    )

    private val _searchKeyword = MutableStateFlow("")
    val searchKeyword = _searchKeyword.asStateFlow()

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    init {
        // Collect searchKeyword with debounce
        viewModelScope.launch {
            searchKeyword
                .debounce(300) // wait 300ms after last input
                .filter { it.isNotBlank() } // ignore empty strings
                .distinctUntilChanged() // only act if keyword changes
                .collect { keyword ->
                    searchCity(keyword)
                }
        }
    }

    fun onAction(action: SearchCitiesAction){
        when(action){
            is SearchCitiesAction.SearchCity -> {
                _searchKeyword.update { action.cityName }
            }
            is SearchCitiesAction.ClearCity -> {
                _searchKeyword.update { "" }
            }
        }
    }

    private fun searchCity(city: String) {
        viewModelScope.launch {
            searchWeatherRepository.searchCity(city)
                .onStart {
                    _searchCitiesViewState.update { SearchCitiesViewState.EmptyState }
                }
                .catch { err ->
                    _error.emit(err.message.toString())
                }
                .collect { data ->
                    _searchCitiesViewState.update {
                        SearchCitiesViewState.Success(data.toSearchCitiesUI())
                    }
                }
        }
    }
}