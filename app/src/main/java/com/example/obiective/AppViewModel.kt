package com.example.obiective

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/** Ținut la nivel de Activity: filtrul și favoritele supraviețuiesc detaliului, tab-urilor și rotirii. */
class AppViewModel : ViewModel() {
    private val _selected = MutableStateFlow<Category?>(null)   // null = Toate
    val selected: StateFlow<Category?> = _selected.asStateFlow()

    private val _favorites = MutableStateFlow<Set<Int>>(emptySet())
    val favorites: StateFlow<Set<Int>> = _favorites.asStateFlow()

    fun select(category: Category?) { _selected.value = category }

    fun toggleFavorite(id: Int) = _favorites.update { if (id in it) it - id else it + id }
}
