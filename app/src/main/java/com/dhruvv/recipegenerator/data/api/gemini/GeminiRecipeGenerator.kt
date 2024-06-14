package com.dhruvv.recipegenerator.data.api.gemini

import android.util.Log
import com.dhruvv.recipegenerator.BuildConfig
import com.dhruvv.recipegenerator.data.api.RecipeGenerator
import com.dhruvv.recipegenerator.data.api.model.ApiRecipe
import com.dhruvv.recipegenerator.data.api.model.ApiRecipeMain
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter

/**
 * GeminiRecipeGenerator is responsible for generating recipes using the Gemini generative model.
 *
 * @param moshi An instance of Moshi used for JSON parsing.
 */
class GeminiRecipeGenerator(
    private val moshi: Moshi
) : RecipeGenerator {

    /**
     * Generates a recipe based on the provided prompt.
     *
     * @param prompt The prompt to generate the recipe from.
     * @return An [ApiRecipe] generated from the prompt, or null if an error occurs.
     */
    @OptIn(ExperimentalStdlibApi::class)
    override suspend fun generateRecipe(prompt: String): ApiRecipe? {
        // Initialize the GenerativeModel with the appropriate parameters
        val model = GenerativeModel(
            MODEL,
            // Retrieve API key as an environmental variable defined in a Build Configuration
            // see https://github.com/google/secrets-gradle-plugin for further instructions
            BuildConfig.GEMINI_API_KEY,
            generationConfig = generationConfig {
                temperature = 1f
                topK = 64
                topP = 0.95f
                maxOutputTokens = 8192
                responseMimeType = "application/json"
            },
        )

        // Generate content using the model with the given prompt
        val response = model.generateContent(
            content {
                text(INITIAL_INSTRUCTION)
                text("Ingredients: $prompt")
                text(OUTPUT_INSTRUCTION)
            }
        )


        // Parse the response into an ApiRecipe object
        return try {
            Log.i(TAG, "generateRecipe: ${response.text}")
            // Define a JSON adapter for serializing and deserializing ApiRecipeMain objects using Moshi.
            val adapter: JsonAdapter<ApiRecipeMain> = moshi.adapter<ApiRecipeMain>()

            // Attempt to parse the JSON response text into an ApiRecipeMain object.
            // If response.text is not null, deserialize it into an ApiRecipeMain object using the Moshi adapter.
            val apiRecipeMain = response.text?.let { adapter.fromJson(it) } as ApiRecipeMain

            // Extract the ApiRecipe object from the parsed ApiRecipeMain object.
            apiRecipeMain.recipe
        } catch (e: Exception) {
            // Log any errors that occur during the generation process
            Log.e(TAG, "generateRecipe: ${e.message}")
            null
        }
    }

    companion object {
        private const val TAG = "GeminiRecipeGenerator"
    }
}