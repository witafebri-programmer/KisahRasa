package com.dicoding.kisahrasa.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.kisahrasa.core.domain.model.Recipe
import com.dicoding.kisahrasa.core.domain.usecase.RecipeUseCase
import com.dicoding.kisahrasa.core.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var recipeUseCase: RecipeUseCase

    private lateinit var homeViewModel: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getAllRecipes is called, it should return success resource with data`() = runTest {
        val dummyRecipes = listOf(
            Recipe(
                id = "1",
                name = "Nasi Goreng",
                category = "Beef",
                instructions = "Cook it",
                thumbUrl = "url",
                isFavorite = false
            )
        )
        val expectedResource = Resource.Success(dummyRecipes)
        val query = ""

        `when`(recipeUseCase.getAllRecipes(query)).thenReturn(flowOf(expectedResource))

        homeViewModel = HomeViewModel(recipeUseCase)

        // Collecting the StateFlow to start the flow
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            homeViewModel.recipes.collect()
        }

        // Advance until idle to allow debounce (300ms) and flatMapLatest to complete
        advanceTimeBy(400)
        runCurrent()

        val actualRecipes = homeViewModel.recipes.value
        
        assert(actualRecipes is Resource.Success)
        assertEquals(dummyRecipes.size, actualRecipes.data?.size)
        assertEquals(dummyRecipes[0].name, actualRecipes.data?.get(0)?.name)
        
        collectJob.cancel()
    }
}
