package com.dicoding.kisahrasa.core.data

import com.dicoding.kisahrasa.core.data.local.LocalDataSource
import com.dicoding.kisahrasa.core.data.local.entity.RecipeEntity
import com.dicoding.kisahrasa.core.data.remote.RemoteDataSource
import com.dicoding.kisahrasa.core.domain.model.Recipe
import com.dicoding.kisahrasa.core.utils.AppExecutors
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RecipeRepositoryTest {

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var localDataSource: LocalDataSource

    @Mock
    private lateinit var appExecutors: AppExecutors

    private lateinit var recipeRepository: RecipeRepository

    @Before
    fun setUp() {
        recipeRepository = RecipeRepository(remoteDataSource, localDataSource, appExecutors)
    }

    @Test
    fun `getFavoriteRecipes should return data from local data source`() = runTest {
        val dummyEntities = listOf(
            RecipeEntity(
                id = "1",
                name = "Nasi Goreng",
                category = "Beef",
                instructions = "Cook it",
                thumbUrl = "url",
                isFavorite = true
            )
        )
        `when`(localDataSource.getFavoriteRecipes()).thenReturn(flowOf(dummyEntities))

        recipeRepository.getFavoriteRecipes().collect { result ->
            assertEquals(dummyEntities.size, result.size)
            assertEquals(dummyEntities[0].name, result[0].name)
        }
        verify(localDataSource).getFavoriteRecipes()
    }

    @Test
    fun `setFavoriteRecipe should call local data source`() = runTest {
        val dummyRecipe = Recipe(
            id = "1",
            name = "Nasi Goreng",
            category = "Beef",
            instructions = "Cook it",
            thumbUrl = "url",
            isFavorite = false
        )
        val expectedEntity = RecipeEntity(
            id = "1",
            name = "Nasi Goreng",
            category = "Beef",
            instructions = "Cook it",
            thumbUrl = "url",
            isFavorite = false
        )
        
        recipeRepository.setFavoriteRecipe(dummyRecipe, true)
        
        verify(localDataSource).setFavoriteRecipe(expectedEntity, true)
    }
}
