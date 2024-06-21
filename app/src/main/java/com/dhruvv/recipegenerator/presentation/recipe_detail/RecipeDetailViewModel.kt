package com.dhruvv.recipegenerator.presentation.recipe_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruvv.recipegenerator.common.util.Resource
import com.dhruvv.recipegenerator.data.model.Recipe
import com.dhruvv.recipegenerator.domain.usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private var _recipeDetailState = mutableStateOf(RecipeDetailState())
    val recipeDetailState: State<RecipeDetailState> = _recipeDetailState


    fun getRecipe(id: Int) = viewModelScope.launch {
        val recipeResource = useCase.getRecipeById(id)
        recipeResource.collectLatest { resource ->
            when (resource) {
                is Resource.Loading -> updateRecipeState(_recipeDetailState.value.copy(isLoading = true))
                is Resource.Error -> updateRecipeState(
                    _recipeDetailState.value.copy(
                        isLoading = false,
                        recipe = Recipe.INVALID_RECIPE,
                        error = resource.message ?: ""
                    )
                )

                is Resource.Success -> updateRecipeState(
                    _recipeDetailState.value.copy(
                        isLoading = false,
                        recipe = resource.data ?: Recipe.INVALID_RECIPE
                    )
                )
            }
        }
    }

    private fun updateRecipeState(recipeDetailState: RecipeDetailState) {
        _recipeDetailState.value = recipeDetailState
    }

    fun resetRecipeState() {
        _recipeDetailState.value = RecipeDetailState.INVALID_HOME_STATE
    }
}