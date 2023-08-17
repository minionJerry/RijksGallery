package com.minionjerry.android.rijksgallery.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minionjerry.android.rijksgallery.domain.usecase.GetArtObjectsGroupedByArtistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtObjectListViewModel @Inject constructor(
    private val useCase: GetArtObjectsGroupedByArtistUseCase,
    private val converter: ArtObjectListConverter
) : ViewModel() {

    private val _groupedArtObjectListFlow = MutableStateFlow<UiState<GroupedArtObjectListModel>>(UiState.Loading())
    val groupedArtObjectListFlow: StateFlow<UiState<GroupedArtObjectListModel>> =
        _groupedArtObjectListFlow

    fun loadGroupedArtObjects() {
        viewModelScope.launch {
            useCase.execute(GetArtObjectsGroupedByArtistUseCase.Request)
                .map {
                    converter.convert(it)
                }
                .collect{
                    _groupedArtObjectListFlow.value = it
                }
        }
    }
}