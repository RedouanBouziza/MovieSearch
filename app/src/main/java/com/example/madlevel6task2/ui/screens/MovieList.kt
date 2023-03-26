package com.example.madlevel6task2.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.request.CachePolicy
import coil.request.ImageRequest

import com.example.madlevel6task2.api.util.Resource
import com.example.madlevel6task2.datamodel.Movie
import com.example.madlevel6task2.viewmodel.MovieViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.madlevel6task2.ui.screens.MovieScreens
import com.example.madlevel6task2.R
import com.example.madlevel6task2.api.Api.Companion.IMAGE_BASE_URL
import com.example.madlevel6task2.datamodel.MovieSearchResult

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieList(
    viewModel: MovieViewModel = viewModel(),
    navController: NavController
) {
    val searchResults by viewModel.searchResults.observeAsState(initial = Resource.Empty())
    val context = LocalContext.current
    val apiKey = context.getString(R.string.API_KEY)

    val errorMessage = remember { mutableStateOf("") }

    Column {
        SearchView(
            viewModel = viewModel,
            apiKey = apiKey,
            errorMessage = errorMessage
        )
        MovieList(
            searchResults = searchResults,
            navController = navController,
            viewModel = viewModel,
            errorMessage = errorMessage
        )
    }
}

@Composable
fun MovieList(
    searchResults: Resource<MovieSearchResult>,
    navController: NavController,
    viewModel: MovieViewModel,
    errorMessage: MutableState<String>
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {

        when  (searchResults) {
            is Resource.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(110.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(searchResults.data!!.results) { movie ->
                        MovieListItem(
                            movie = movie,
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                }
            }
            is Resource.Error -> {
                Text(
                    text = searchResults.message!!,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 24.sp
                )
            }
            is Resource.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(16.dp)
                )
            }
            is Resource.Empty -> {
                when (errorMessage.value) {
                    "Please enter a search query" -> {
                        Text(
                            text = stringResource(id = R.string.empty_search),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 24.sp
                        )
                    }
                    else -> {
                        Text(
                            text = stringResource(id = R.string.search_hint),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieListItem(
    movie: Movie,
    navController: NavController,
    viewModel: MovieViewModel
){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                viewModel.setSelectedMovie(movie)
                navController.navigate(MovieScreens.MovieDetailScreen.route)
            },
        shape = RoundedCornerShape(0.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(0.7f)
        ) {
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(
                        data = IMAGE_BASE_URL + movie.posterPath
                    )
                    .apply(block = fun ImageRequest.Builder.() {
                        memoryCachePolicy(CachePolicy.ENABLED)
                    }).build()
            )
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {}
            }
        }
    }
}

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun SearchView(
    viewModel: MovieViewModel,
    apiKey: String,
    errorMessage: MutableState<String>
) {
    val searchQueryState = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
        onDone = {
                if (searchQueryState.value.text == "") {
                    errorMessage.value = "Please enter a search query"
                } else {
                    errorMessage.value = ""
                    viewModel.searchMovies(apiKey, searchQueryState.value.text)
                }
                keyboardController?.hide()
            }
        ),
        value = searchQueryState.value,
        onValueChange = { value ->
            searchQueryState.value = value
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 0.dp),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            IconButton(onClick = {
                if (searchQueryState.value.text == "") {
                    errorMessage.value = "Please enter a search query"
                } else {
                    errorMessage.value = ""
                    viewModel.searchMovies(apiKey, searchQueryState.value.text)
                }
                keyboardController?.hide()
            }) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(24.dp)
                )
            }
        },
        trailingIcon = {
            if (searchQueryState.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        searchQueryState.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(16.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search),
                color = Color.White
            )
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}



