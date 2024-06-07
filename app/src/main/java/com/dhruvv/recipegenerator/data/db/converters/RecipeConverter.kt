package com.dhruvv.recipegenerator.data.db.converters

import android.util.Log
import androidx.room.TypeConverter
import com.dhruvv.recipegenerator.common.parser.JsonParser
import com.dhruvv.recipegenerator.data.api.model.Recipe

class RecipeConverter(
    private val jsonParser: JsonParser
) {

    @TypeConverter
    fun fromRecipeToJsonString(recipe: Recipe) : String {
        try {
            return jsonParser.toJson(recipe,Recipe::class.java).toString()
        }catch (e: Exception) {
            Log.e(TAG, "fromRecipeToJsonString: $e")
        }

        return ""
    }

    @TypeConverter
    fun fromJsonStringToRecipe(jsonString: String) : Recipe {
        try {
            return jsonParser.fromJson(jsonString,Recipe::class.java) ?: Recipe.INVALID_RECIPE
        } catch (e: Exception) {
            Log.e(TAG, "fromJsonStringToRecipe: $e")
        }

        return Recipe.INVALID_RECIPE
    }


    companion object {
        private const val TAG = "RecipeConverter"
    }
}