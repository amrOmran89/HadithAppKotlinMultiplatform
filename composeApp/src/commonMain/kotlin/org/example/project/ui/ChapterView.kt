package org.example.project.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.HadithScreen
import org.example.project.api.API
import org.example.project.models.Chapters


@Composable
fun ChapterView(bookSlug: String, modifier: Modifier = Modifier, navController: NavController, viewModel: ChapterViewModel = viewModel { ChapterViewModel() }) {
    val chapters = viewModel.chaptersState.collectAsState()

    LaunchedEffect(bookSlug) {
        viewModel.getAllChapters(bookSlug)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        chapters.value?.chapters?.let { chapters ->
            LazyColumn(modifier = modifier.fillMaxWidth()) {
                items(chapters) { chapter ->
                    Column(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .padding(horizontal = 16.dp)
                            .clickable {
                                navController.navigate(HadithScreen(bookSlug = chapter.bookSlug, chapter = chapter.id))
                            },
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 4.dp),
                            text = chapter.chapterArabic,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                        Text(chapter.chapterEnglish, fontSize = 14.sp, color = Color.Gray)
                    }
                }
            }
        } ?: run {
            CircularProgressIndicator()
        }
    }
}

class ChapterViewModel : ViewModel() {

    private val _chaptersState = MutableStateFlow<Chapters?>(null)
    val chaptersState = _chaptersState.asStateFlow()

    fun getAllChapters(bookSlug: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = API.getAllChapters(bookSlug = bookSlug)
                launch(Dispatchers.Main) {
                    _chaptersState.emit(response)
                }
            } catch (e: IllegalStateException) {
                println("Error has been detected $e")
            }
        }
    }
}