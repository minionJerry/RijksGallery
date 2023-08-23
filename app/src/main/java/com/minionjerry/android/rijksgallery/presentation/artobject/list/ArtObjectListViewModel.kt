package com.minionjerry.android.rijksgallery.presentation.artobject.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.minionjerry.android.rijksgallery.domain.usecase.GetArtObjectsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtObjectListViewModel @Inject constructor(
    private val useCase: GetArtObjectsUseCase,
    private val converter: ArtObjectListConverter
) : ViewModel() {

    private val _artObjectListFlow = MutableStateFlow<PagingData<ArtObjectUiModel>>(
        PagingData.empty()
    )
    val artObjectListFlow: StateFlow<PagingData<ArtObjectUiModel>> =
        _artObjectListFlow

    fun loadArtObjects() {
        viewModelScope.launch {
            useCase.execute(GetArtObjectsUseCase.Request)
                .map {
                    converter.convert(it)
                }
                .collect {
                    _artObjectListFlow.value = it
                }
        }
    }
}