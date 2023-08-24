package com.minionjerry.android.rijksgallery.presentation.artobject.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.minionjerry.android.rijksgallery.R
import com.minionjerry.android.rijksgallery.presentation.navigation.NavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtObjectListScreen(viewModel: ArtObjectListViewModel, navController: NavController) {
    viewModel.loadArtObjects()
    val artObjects = viewModel.artObjectListFlow.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                        .weight(1.0f),
                    textAlign = TextAlign.Center
                )
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            ArtObjectList(artObjects = artObjects) {
                navController.navigate(NavRoutes.ArtObject.routeForArtObject(it))
            }
        }
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                artObjects.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { Loading() }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error = artObjects.loadState.refresh as LoadState.Error
                            item {
                                Error(error.error.localizedMessage!!)
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { Loading() }
                        }

                        loadState.append is LoadState.Error -> {
                            val error = artObjects.loadState.append as LoadState.Error
                            item {
                                Error(errorMessage = error.error.localizedMessage!!)
                            }
                        }
                    }
                }

        }
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtObjectList(artObjects: LazyPagingItems<ArtObjectUiModel>, onGridClick: (String) -> Unit) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(artObjects.itemCount) { index ->
                val data = artObjects[index]!!
                when (data) {
                    is ArtObjectListItemModel -> ArtObjectImage(
                        artObj = data,
                        onCardClick = onGridClick
                    )

                    is HeaderItemModel ->
                        ArtistHeader(artist = data.artist, color = Color.Black)
                }

            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

const val IMAGE_SCALE_DOWN = 2

@Composable
fun ArtObjectImage(
    artObj: ArtObjectListItemModel,
    bgColor: Color = Color.Transparent,
    onCardClick: (String) -> Unit
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(artObj.image.url)
            .placeholder(R.drawable.placeholder_view)
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
            .wrapContentHeight()
            .clickable { onCardClick(artObj.objectNumber) },
        alpha = 1f,
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





