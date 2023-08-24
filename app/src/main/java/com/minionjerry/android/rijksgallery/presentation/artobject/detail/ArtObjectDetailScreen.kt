package com.minionjerry.android.rijksgallery.presentation.artobject.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minionjerry.android.rijksgallery.presentation.UiState
import com.minionjerry.android.rijksgallery.presentation.artobject.list.ArtObjectImage
import com.minionjerry.android.rijksgallery.presentation.artobject.list.ArtObjectListItemModel

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
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                ) {
                    ArtObjectImage(artObj = artObject, onCardClick = {})
                    ArtObjectDetails(artObject = artObject,this@BoxWithConstraints.maxHeight)
                }
            }
        }
    }
}

@Composable
private fun ArtObjectDetails(artObject: ArtObjectListItemModel, containerHeight: Dp) {
    Column {
        Title(artObject.title)
        ArtObjectProperty("Artist", artObject.artist)
        ArtObjectProperty("Id",artObject.id)
    }
}


@Composable
private fun Title(
    title: String
) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp)) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
fun ArtObjectProperty(label: String, value: String) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Divider(modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text = label,
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = FontFamily.Serif
        )
        Text(
            text = value,
            modifier = Modifier.height(24.dp),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
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





