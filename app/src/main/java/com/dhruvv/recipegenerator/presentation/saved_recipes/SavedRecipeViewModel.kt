package com.dhruvv.recipegenerator.presentation.saved_recipes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruvv.recipegenerator.common.util.Resource
import com.dhruvv.recipegenerator.domain.usecases.UseCase
import com.dhruvv.recipegenerator.presentation.saved_recipes.SavedRecipeState.Companion.INVALID_SAVED_RECIPE_STATE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SavedRecipeViewModel @Inject constructor(
    private val useCase: UseCase
) : ViewModel() {

    private val _savedRecipeState = mutableStateOf(INVALID_SAVED_RECIPE_STATE)
    val savedRecipeState: State<SavedRecipeState> = _savedRecipeState

    fun getSavedRecipes() = viewModelScope.launch {
        val recipesFlow =  useCase.getSavedRecipes()

        recipesFlow.collectLatest { resource ->
            when (resource) {
                /**
                 * Handles the Error state by updating the HomeState with an empty list and a flag
                 * to indicate that no recipes were generated.
                 */
                is Resource.Error ->
                    updateSavedRecipeState(
                        _savedRecipeState.value.copy(
                            recipes = mutableListOf(),
                            showNoRecipeGenerated = true,
                            isLoading = false
                        ),
                    )

                /**
                 * Handles the Loading state by updating the HomeState with an empty list and a flag
                 * to indicate that no recipes were generated.
                 */
                is Resource.Loading ->
                    updateSavedRecipeState(
                        _savedRecipeState.value.copy(
                            recipes = mutableListOf(),
                            showNoRecipeGenerated = false,
                            isLoading = true
                        ),
                    )

                /**
                 * Handles the Success state by updating the HomeState with the generated recipes
                 * and resetting the flag to indicate that recipes were successfully generated.
                 *
                 * @param resource.data The list of generated recipes, or an empty list if data is null.
                 */
                is Resource.Success ->
                    updateSavedRecipeState(
                        _savedRecipeState.value.copy(
                            recipes = resource.data ?: mutableListOf(),
                            showNoRecipeGenerated = false,
                            isLoading = false
                        ),
                    )
            }
        }
    }


    /**
     * Updates the SavedRecipeState with the given state.
     *
     * @param savedRecipeState The new state to update.
     */
    private fun updateSavedRecipeState(savedRecipeState: SavedRecipeState) {
        _savedRecipeState.value = savedRecipeState
    }
}