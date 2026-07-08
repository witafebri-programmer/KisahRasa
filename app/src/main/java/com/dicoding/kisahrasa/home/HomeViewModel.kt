package com.dicoding.kisahrasa.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.kisahrasa.core.domain.usecase.RecipeUseCase
import com.dicoding.kisahrasa.core.ui.model.RecipeUI
import com.dicoding.kisahrasa.core.utils.Resource
import com.dicoding.kisahrasa.core.utils.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(private val recipeUseCase: RecipeUseCase) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val recipes: StateFlow<Resource<List<RecipeUI>>> = _searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            recipeUseCase.getAllRecipes(query).map { resource ->
                resource.map { list -> list.map { it.toUI() } }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = Resource.Loading()
        )

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
