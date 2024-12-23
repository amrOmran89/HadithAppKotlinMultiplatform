package org.example.project.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.HadithScreen

@Composable
fun HadithView(hadith: HadithScreen, viewModel: HadithViewModel = viewModel { HadithViewModel() }) {
    Text("${hadith.bookSlug} + ${hadith.chapter}")
}

class HadithViewModel: ViewModel() {

}