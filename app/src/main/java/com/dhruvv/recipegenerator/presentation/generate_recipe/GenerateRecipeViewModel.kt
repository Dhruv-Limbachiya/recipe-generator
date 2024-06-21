package com.dhruvv.recipegenerator.presentation.generate_recipe

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruvv.recipegenerator.common.util.Resource
import com.dhruvv.recipegenerator.data.model.CheckableItem
import com.dhruvv.recipegenerator.data.model.Recipe
import com.dhruvv.recipegenerator.domain.usecases.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

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
    private var _generateRecipeState =
        mutableStateOf(GenerateRecipeState.INVALID_GENERATE_RECIPE_STATE)
    val generateRecipeState: State<GenerateRecipeState> = _generateRecipeState

    // map for holding the current generate recipe state
    var staticIngredients = mutableStateOf(mapOf<String, List<CheckableItem>>())

    init {
        getStaticIngredients()
    }

    /**
     * Function to initiate the recipe generation process.
     * Launches a coroutine to handle asynchronous operations and updates the generate recipe state accordingly.
     */
    fun generateRecipe() =
        viewModelScope.launch {
            val prompt = preparePromptFromIngredients()
            Log.i(TAG, "generateRecipe: $${prompt}")

            // Call the use case to generate a recipe based on the current prompt value.
            val generatedRecipeFlow = useCase.generateRecipe(prompt)

            // Collect the latest value emitted by the generatedRecipeFlow coroutine flow.
            generatedRecipeFlow.collectLatest { resource ->
                // Update the generate recipe state based on the type of resource emitted.
                when (resource) {
                    is Resource.Error ->
                        updateGenerateRecipeState(
                            _generateRecipeState.value.copy(
                                isLoading = false,
                                isErrorMessage = resource.message
                                    ?: "Oops! Something went wrong😵‍💫",
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
     * Private function to update the generate recipe state.
     *
     * @param generateRecipeState The new generate recipe state to set.
     */
    private fun updateGenerateRecipeState(generateRecipeState: GenerateRecipeState) {
        _generateRecipeState.value = generateRecipeState
    }


    // Fetch static ingredients and update the state
    private fun getStaticIngredients() {
        staticIngredients.value = useCase.getStaticIngredient()
    }

    // Prepare a prompt string from the selected ingredients
    private fun preparePromptFromIngredients(): String {
        val promptStr: StringBuilder = StringBuilder()
        for ((key, value) in staticIngredients.value) {
            val selectedItems = value.filter { it.isSelected }
            if (selectedItems.isNotEmpty()) {
                promptStr.append(" $key : ${selectedItems.joinToString { it.title }}")
                promptStr.append(",")
            }
        }
        return promptStr.toString()
    }

    // Clear the selection status of all ingredients
    fun clearAllSelection() {
        // Create a mutable copy of the current static ingredients
        val resetStaticIngredients = staticIngredients.value.toMutableMap()

        // Iterate over each entry and set isSelected to false
        resetStaticIngredients.entries.forEach { (_, value) ->
            value.forEach {
                it.isSelected = false
            }
        }

        // Update the staticIngredients state with the reset values
        staticIngredients.value = mutableMapOf()
        staticIngredients.value = resetStaticIngredients
    }

    // Reset the recipe detail state to an invalid state
    fun resetRecipeDetail() {
        _generateRecipeState.value = GenerateRecipeState.INVALID_GENERATE_RECIPE_STATE
    }

    companion object {
        private const val TAG = "GenerateRecipeViewModel"
    }
}
