package org.example.project.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.api.API
import org.example.project.models.Books

@Composable
fun Home(modifier: Modifier = Modifier, viewModel: MainViewModel, navController: NavController) {

    val books = viewModel.booksState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        books.value?.books?.let {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                items(it) { book ->
                    Column(
                        modifier = Modifier
                            .clickable {
                                navController.navigate(book)
                            }
                            .padding(vertical = 8.dp)
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 4.dp),
                            text = book.bookName,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                        Text(book.writerName, fontSize = 14.sp, color = Color.Gray)
                    }
                }
            }
        } ?: run {
            CircularProgressIndicator()
        }
    }

}

class MainViewModel : ViewModel() {

    private val _booksState = MutableStateFlow<Books?>(null)
    val booksState = _booksState.asStateFlow()

    init {
        getAllBooks()
    }

    fun getAllBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = API.getAllBooks()
                launch(Dispatchers.Main) {
                    _booksState.emit(response)
                }
            } catch (e: IllegalStateException) {
                println("Error has been detected $e")
            }

        }
    }
}