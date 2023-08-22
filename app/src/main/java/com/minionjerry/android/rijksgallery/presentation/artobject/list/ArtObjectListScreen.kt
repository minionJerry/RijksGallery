package com.minionjerry.android.rijksgallery.presentation.artobject.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.minionjerry.android.rijksgallery.presentation.UiState
import kotlin.random.Random

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
    //  Use the composable function below to see the grid with distinct sections with headers, which is a bit ugly
    //  ArtObjectListWithHeader(groupedArtObjects = groupedArtObjects)

    // Use the composable function below to see the grid of art objects in a nicer way
     ArtObjectList(groupedArtObjects = groupedArtObjects)
}


const val IMAGE_SCALE_DOWN = 2
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtObjectList(groupedArtObjects: GroupedArtObjectListModel) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            groupedArtObjects.list.map { artObjectList ->
                val random = Random
                val randomColor = Color(random.nextFloat(), random.nextFloat(), random.nextFloat())

                item(artObjectList.headerText) {
                    ArtistHeader(
                        artist = artObjectList.headerText,
                        color = randomColor
                    )
                }

                items(artObjectList.items) { artObj ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(artObj.image.url)
                            .size(
                                Size(
                                    artObj.image.width / IMAGE_SCALE_DOWN,
                                    artObj.image.height / IMAGE_SCALE_DOWN
                                )
                            )
                            .scale(Scale.FIT)
                            .build(),
                        contentDescription = artObj.title,
                        modifier = Modifier
                            .background(color = randomColor)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        alpha = 0.8f,
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ArtObjectListWithHeader(groupedArtObjects: GroupedArtObjectListModel) {
    LazyVerticalGrid(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Adaptive(100.dp)
    ) {
        groupedArtObjects.list.map { artObjectListModel ->
            header {
                ArtistHeader(artObjectListModel.headerText, color = Color.Black)
            }

            items(artObjectListModel.items) { artObj ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(artObj.image.url)
                        .size(
                            Size(
                                artObj.image.width / IMAGE_SCALE_DOWN,
                                artObj.image.height / IMAGE_SCALE_DOWN
                            )
                        )
                        .scale(Scale.FIT)
                        .build(),
                    contentDescription = artObj.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
            }
        }
    }
}

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}

@Composable
fun ArtistHeader(artist: String, color: Color = Color.Transparent) {
    Column(
        modifier = Modifier
            .background(color)
            .padding(16.dp)
    ) {
        Text(text = "$artist's art(s):", color = Color(color.toArgb().getContrastColor()))
    }
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





