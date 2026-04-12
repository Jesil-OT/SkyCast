package com.jesil.skycast.features.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesil.skycast.data.mapper.toSearchCitiesUI
import com.jesil.skycast.data.repository.search_cities.SearchWeatherRepository
import com.jesil.skycast.features.search.model.SearchCitiesViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class SearchCitiesViewModel(
    private val searchWeatherRepository: SearchWeatherRepository
): ViewModel(), KoinComponent {

    private val _searchCitiesViewState = MutableStateFlow<SearchCitiesViewState>(SearchCitiesViewState.Loading)

    val searchCitiesViewState = _searchCitiesViewState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SearchCitiesViewState.Loading
    )

    private fun searchCity(city: String) = viewModelScope.launch {
        val response = searchWeatherRepository.searchCity(city)

        response.onStart {
            _searchCitiesViewState.update { SearchCitiesViewState.Loading }
        }.catch { err ->
            _searchCitiesViewState.update { SearchCitiesViewState.Error(err.message.toString()) }
        }.collect { data ->
            _searchCitiesViewState.update { SearchCitiesViewState.Success(data.toSearchCitiesUI()) }
        }
    }
}