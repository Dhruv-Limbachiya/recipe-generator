package com.dhruvv.recipegenerator.data.db.converters

import android.util.Log
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.dhruvv.recipegenerator.common.parser.JsonParser
import com.dhruvv.recipegenerator.data.api.model.Recipe

@ProvidedTypeConverter
class RecipeConverter(
    private val jsonParser: JsonParser,
) {
    // Converts a Recipe object to its JSON string representation
    @TypeConverter
    fun fromRecipeToJsonString(recipe: Recipe): String {
        try {
            // Use the JsonParser to convert the Recipe object to a JSON string
            return jsonParser.toJson(recipe, Recipe::class.java).toString()
        } catch (e: Exception) {
            // Log any exceptions that occur during conversion
            Log.e(TAG, "fromRecipeToJsonString: $e")
        }
        // Return an empty string if conversion fails
        return ""
    }

    // Converts a JSON string representation of a Recipe back into a Recipe object
    @TypeConverter
    fun fromJsonStringToRecipe(jsonString: String): Recipe {
        try {
            // Use the JsonParser to convert the JSON string back to a Recipe object
            return jsonParser.fromJson(jsonString, Recipe::class.java) ?: Recipe.INVALID_RECIPE
        } catch (e: Exception) {
            // Log any exceptions that occur during conversion
            Log.e(TAG, "fromJsonStringToRecipe: $e")
        }
        // Return an INVALID_RECIPE if conversion fails
        return Recipe.INVALID_RECIPE
    }

    companion object {
        // Tag used for logging
        private const val TAG = "RecipeConverter"
    }
}
