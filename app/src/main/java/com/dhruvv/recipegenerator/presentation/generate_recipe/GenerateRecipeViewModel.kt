package com.dhruvv.recipegenerator.presentation.generate_recipe

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruvv.recipegenerator.common.util.Resource
import com.dhruvv.recipegenerator.data.model.Recipe
import com.dhruvv.recipegenerator.data.static.cookingOils
import com.dhruvv.recipegenerator.data.static.dairyProducts
import com.dhruvv.recipegenerator.data.static.spices
import com.dhruvv.recipegenerator.data.static.vegetables
import com.dhruvv.recipegenerator.domain.usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state and business logic related to recipe generation.
 *
 * @param useCase The use case responsible for generating recipes.
 */
@HiltViewModel
class GenerateRecipeViewModel
    @Inject
    constructor(
        private val useCase: UseCase,
    ) : ViewModel() {
        // Mutable state for holding the current generate recipe state, initialized with INVALID_GENERATE_RECIPE_STATE.
        private var _generateRecipeState = mutableStateOf(GenerateRecipeState.INVALID_GENERATE_RECIPE_STATE)
        val generateRecipeState: State<GenerateRecipeState> = _generateRecipeState

        // Mutable state for holding the prompt value, used as input for generating the recipe.
        var prompt = mutableStateOf("")

        /**
         * Function to initiate the recipe generation process.
         * Launches a coroutine to handle asynchronous operations and updates the generate recipe state accordingly.
         */
        fun generateRecipe() =
            viewModelScope.launch {
                // Call the use case to generate a recipe based on the current prompt value.
                val generatedRecipeFlow = useCase.generateRecipe(prompt.value)

                // Collect the latest value emitted by the generatedRecipeFlow coroutine flow.
                generatedRecipeFlow.collectLatest { resource ->
                    // Update the generate recipe state based on the type of resource emitted.
                    when (resource) {
                        is Resource.Error ->
                            updateGenerateRecipeState(
                                _generateRecipeState.value.copy(
                                    isLoading = false,
                                    isErrorMessage = resource.message ?: "Oops! Something went wrong😵‍💫",
                                ),
                            )

                        is Resource.Loading ->
                            updateGenerateRecipeState(
                                _generateRecipeState.value.copy(
                                    isLoading = true,
                                ),
                            )

                        is Resource.Success ->
                            updateGenerateRecipeState(
                                _generateRecipeState.value.copy(
                                    isLoading = false,
                                    generatedRecipe = resource.data ?: Recipe.INVALID_RECIPE,
                                ),
                            )
                    }
                }
            }

        /**
         * Function to update the prompt value.
         *
         * @param updatePrompt The new prompt value to set.
         */
        fun updatePrompt(updatePrompt: String) {
            prompt.value = updatePrompt
        }

        /**
         * Private function to update the generate recipe state.
         *
         * @param generateRecipeState The new generate recipe state to set.
         */
        private fun updateGenerateRecipeState(generateRecipeState: GenerateRecipeState) {
            _generateRecipeState.value = generateRecipeState
        }


        fun staticIngredients() = useCase.getStaticIngredient()
    }
