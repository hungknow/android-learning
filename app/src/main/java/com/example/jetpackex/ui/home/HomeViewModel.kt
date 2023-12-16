package com.example.jetpackex.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackex.models.ErrorMessage
import com.example.jetpackex.models.Post
import com.example.jetpackex.models.PostsFeed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed interface HomeUiState {
    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>
    val searchInput: String

    data class NoPosts(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val searchInput: String
    ): HomeUiState

    data class HasPosts(
        val postsFeed: PostsFeed,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        override val searchInput: String
    ): HomeUiState
}

private data class HomeViewModelState(
    val postsFeed: PostsFeed? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
    val searchInput: String = "",
){
    fun toUIState(): HomeUiState {
        if (postsFeed != null) {
            return HomeUiState.HasPosts(
                postsFeed = postsFeed,
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        } else {
            return HomeUiState.NoPosts(
                isLoading = isLoading,
                errorMessages = errorMessages,
                searchInput = searchInput
            )
        }
    }
}

class HomeViewModel(): ViewModel() {
    private val viewModelState = MutableStateFlow(
        HomeViewModelState(
            isLoading = true,
        )
    )

    // UI state exposed to the UI
    val uiState = viewModelState
        .map(HomeViewModelState::toUIState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUIState(),
        )
}