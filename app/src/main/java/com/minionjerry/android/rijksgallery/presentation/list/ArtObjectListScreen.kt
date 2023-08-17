package com.minionjerry.android.rijksgallery.presentation.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ArtObjectListScreen(viewModel: ArtObjectListViewModel) {
    viewModel.loadGroupedArtObjects()
    viewModel.groupedArtObjectListFlow.collectAsState().value.let { state ->
        when (state) {
            is UiState.Loading -> Loading()
            is UiState.Error -> Error(state.errorMessage)
            is UiState.Success -> GroupedArtObjectList(groupedArtObjects = state.data)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GroupedArtObjectList(groupedArtObjects: GroupedArtObjectListModel) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        groupedArtObjects.list.map { artObjectListModel ->
            stickyHeader {
                ArtistHeader(artObjectListModel.headerText)
            }

            items(artObjectListModel.items) {
                ArtObjectList(artObjectListModel.items)
            }
        }
    }
}

@Composable
fun ArtistHeader(artist: String) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = artist)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtObjectList(list: List<ArtObjectListItemModel>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(3),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(list) { photo ->
                AsyncImage(
                    model = photo,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun Error(errorMessage: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Snackbar {
            Text(text = errorMessage)
        }
    }
}





