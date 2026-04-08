package com.example.smartbuddyai.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import com.example.smartbuddyai.feature.home.components.ContentRow
import com.example.smartbuddyai.feature.search.components.SearchBar
import com.example.smartbuddyai.feature.search.components.SearchResultsRow
import com.example.smartbuddyai.feature.search.components.SuggestionsRow

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onItemClicked: (String) -> Unit
) {
    val state = viewModel.state
    val searchFocus = remember { FocusRequester() }
    val suggestionFocus = remember { FocusRequester() }
    val resultItemFocusRequesters = remember {
        List(20) { FocusRequester() }
    }
    LaunchedEffect(Unit) {
        searchFocus.requestFocus()
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 48.dp, top = 32.dp, bottom = 32.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0B1220),
                        Color(0xFF0E1A2B),
                        Color(0xFF0B1220)
                    )
                )
            ),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        item {
            SearchBar(
                query = state.query,
                onQueryChange = viewModel::onQueryChange,
                focusRequester = searchFocus,
                onDown = {
                    suggestionFocus.requestFocus()
                }
            )
        }

        if (state.suggestions.isNotEmpty()) {
            item {
                SuggestionsRow(
                    suggestions = state.suggestions,
                    onClick = viewModel::onSuggestionClick,
                    focusRequester = suggestionFocus,
                    onDown = {
                        resultItemFocusRequesters[0].requestFocus()
                    }
                )
            }
        }
        if (state.results.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                SearchResultsRow(
                    items = state.results,
                    lastFocusedIndex = viewModel.lastFocusedIndex,
                    onItemFocused = viewModel::updateFocusedIndex,
                    onItemClicked = { videoId ->
                        onItemClicked(videoId)
                    },
                    focusRequesters = resultItemFocusRequesters
                )
            }
        } else {
            item {
                Text(
                    text = "No Result Found",
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 32.dp)
                )
            }
        }
    }
}
