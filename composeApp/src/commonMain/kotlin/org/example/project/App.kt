package org.example.project

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.example.project.models.Book
import org.example.project.ui.ChapterView
import org.example.project.ui.Home
import org.example.project.ui.MainViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val mainViewModel = viewModel { MainViewModel() }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Hadith") },
                    navigationIcon = {
                        if (navBackStackEntry?.destination?.route?.contains("BooksScreen") == false) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "ArrowBack"
                                )
                            }
                        }
                    },
                    actions = {

                    },
                    contentColor = Color.White,
                    backgroundColor = Color.Black
                )
            },
            content = { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = BooksScreen
                ) {
                    composable<BooksScreen> {
                        Home(modifier = Modifier.padding(innerPadding), viewModel = mainViewModel, navController = navController)
                    }

                    composable<Book> {
                        val book = it.toRoute<Book>()
                        ChapterView(bookSlug = book.bookSlug, modifier = Modifier.padding(innerPadding), navController = navController)
                    }
                }
            }
        )
    }
}


@Serializable
object BooksScreen