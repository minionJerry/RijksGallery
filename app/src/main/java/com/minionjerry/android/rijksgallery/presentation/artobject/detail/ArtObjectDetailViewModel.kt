package com.minionjerry.android.rijksgallery.presentation.artobject.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minionjerry.android.rijksgallery.domain.usecase.GetArtObjectUseCase
import com.minionjerry.android.rijksgallery.domain.usecase.GetArtObjectsUseCase
import com.minionjerry.android.rijksgallery.presentation.UiState
import com.minionjerry.android.rijksgallery.presentation.artobject.list.ArtObjectListItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtObjectDetailViewModel @Inject constructor(
    private val useCase: GetArtObjectUseCase,
    private val converter: ArtObjectDetailConverter
) : ViewModel() {

    private val _artObjectDetailFlow = MutableStateFlow<UiState<ArtObjectListItemModel>>(
        UiState.Loading()
    )
    val artObjectDetailFlow: StateFlow<UiState<ArtObjectListItemModel>> =
        _artObjectDetailFlow

    fun loadArtObject(objectNumber: String) {
        viewModelScope.launch {
            useCase.execute(GetArtObjectUseCase.Request(objectNumber))
                .map {
                    converter.convert(it)
                }
                .collect {
                    _artObjectDetailFlow.value = it
                }
        }
    }
}