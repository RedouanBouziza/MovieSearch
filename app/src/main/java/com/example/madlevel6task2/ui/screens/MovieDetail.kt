package com.example.madlevel6task2.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.madlevel6task2.api.Api.Companion.IMAGE_BASE_URL
import com.example.madlevel6task2.viewmodel.MovieViewModel

@Composable
fun MovieDetail(
    viewModel: MovieViewModel = viewModel(),
    navController: NavController
) {
    val selectedMovie = viewModel.selectedMovie

    selectedMovie?.let { movie ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 0.dp),
            verticalArrangement = Arrangement.Top
        ) {

            val backdropPath = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(
                        data = IMAGE_BASE_URL + movie.backdropPath
                    )
                    .apply(block = fun ImageRequest.Builder.() {
                        memoryCachePolicy(CachePolicy.ENABLED)
                    }).build()
            )
            Image(
                painter = backdropPath,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                val posterPath = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(
                            data = IMAGE_BASE_URL + movie.posterPath
                        )
                        .apply(block = fun ImageRequest.Builder.() {
                            memoryCachePolicy(CachePolicy.ENABLED)
                        }).build()
                )
                Image(
                    contentScale = ContentScale.Crop,
                    painter = posterPath,
                    contentDescription = null,
                    modifier = Modifier
                        .height(230.dp)
                        .width(150.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        modifier = Modifier
                            .padding(top = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star icon",
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .size(28.dp)
                        )
                        Text(
                            text = movie.voteAverage.toString().substring(0, 3),
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 26.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Overview",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 20.dp, top = 20.dp)
                )
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }
        }

    } ?: run {
        Text(text = "No movie selected")
    }
}

