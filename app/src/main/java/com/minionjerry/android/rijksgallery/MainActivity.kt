package com.minionjerry.android.rijksgallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minionjerry.android.rijksgallery.ui.theme.RijksGalleryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RijksGalleryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RijksGalleryTheme {
        Greeting("Android")
    }
}

//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun ShowTheList() {
//    LazyVerticalStaggeredGrid(
//        columns = StaggeredGridCells.Adaptive(200.dp),
//        verticalItemSpacing = 4.dp,
//        horizontalArrangement = Arrangement.spacedBy(4.dp),
//        content = {
//            items(randomSizedPhotos) { photo ->
//                AsyncImage(
//                    model = photo,
//                    contentScale = ContentScale.Crop,
//                    contentDescription = null,
//                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
//                )
//            }
//        },
//        modifier = Modifier.fillMaxSize()
//    )
//
//    LazyColumn(
//        stickyHeader{}
//        content = )
//}