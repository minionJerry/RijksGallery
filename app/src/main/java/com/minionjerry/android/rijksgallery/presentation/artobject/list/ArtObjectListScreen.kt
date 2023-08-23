package com.minionjerry.android.rijksgallery.presentation.artobject.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import kotlin.random.Random

@Composable
fun ArtObjectListScreen(viewModel: ArtObjectListViewModel) {
    viewModel.loadArtObjects()
    val artObjects = viewModel.artObjectListFlow.collectAsLazyPagingItems()
    ArtObjectList(artObjects = artObjects)
    artObjects.let { state ->
        when {
            state.loadState.refresh is LoadState.Loading -> Loading()
            state.loadState.refresh is LoadState.Error -> {
                val error = artObjects.loadState.refresh as LoadState.Error
                Error(error.error.localizedMessage!!)
            }

            state.loadState.append is LoadState.Loading -> Loading()
            state.loadState.append is LoadState.Error -> {
                val error = artObjects.loadState.append as LoadState.Error
                Error(error.error.localizedMessage!!)
            }
        }
    }
}


const val IMAGE_SCALE_DOWN = 2

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtObjectList(artObjects: LazyPagingItems<ArtObjectListItemModel>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            val groupedArtObjects = artObjects.itemSnapshotList.items.groupBy { it.artist }
            groupedArtObjects.map { artObjectList ->
                val random = Random
                val randomColor = Color(random.nextFloat(), random.nextFloat(), random.nextFloat())

                item(artObjectList.key) {
                    ArtistHeader(
                        artist = artObjectList.key,
                        color = randomColor
                    )
                }

                items(artObjectList.value.size) { index ->
                    ArtObject(artObj = artObjectList.value[index], bgColor = randomColor )
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ArtObject(artObj: ArtObjectListItemModel, bgColor: Color) {
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
            .background(color = bgColor)
            .fillMaxWidth()
            .wrapContentHeight(),
        alpha = 0.8f,
    )
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





