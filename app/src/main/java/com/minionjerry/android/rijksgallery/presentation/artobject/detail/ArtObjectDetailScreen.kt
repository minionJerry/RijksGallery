package com.minionjerry.android.rijksgallery.presentation.artobject.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.minionjerry.android.rijksgallery.R
import com.minionjerry.android.rijksgallery.presentation.UiState
import com.minionjerry.android.rijksgallery.presentation.artobject.list.ArtObjectListItemModel
import com.minionjerry.android.rijksgallery.presentation.artobject.list.IMAGE_SCALE_DOWN
import com.minionjerry.android.rijksgallery.presentation.artobject.list.getContrastColor

@Composable
fun ArtObjectDetailScreen(viewModel: ArtObjectDetailViewModel, objectNumber: String) {
    viewModel.loadArtObject(objectNumber)
    viewModel.artObjectDetailFlow.collectAsState().value.let { state->
        when(state) {
            is UiState.Loading ->  Loading()
            is UiState.Error -> Error(errorMessage = state.errorMessage)
            is UiState.Success -> ArtObjectDetail(artObject = state.data)
        }
    }

}

@Composable
fun ArtObjectDetail(artObject: ArtObjectListItemModel) {
    Text(text = "DetailsScreen", color = MaterialTheme.colorScheme.onBackground)
}

@Composable
fun ArtObject(artObj: ArtObjectListItemModel, bgColor: Color = Color.Transparent) {
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
            .wrapContentHeight(),
        alpha = 0.7f,
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





