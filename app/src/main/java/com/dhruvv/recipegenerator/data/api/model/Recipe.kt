package com.dhruvv.recipegenerator.data.api.model

data class Recipe(
    val cuisine: String? = "",
    val details: String? = "",
    val ingredients: List<Ingredient> = mutableListOf(),
    val instructions: List<Instruction> = mutableListOf(),
    val name: String? = "",
    val tips: List<String> = mutableListOf(),
    val type: String? = "",
    val variations: List<Variation> = mutableListOf()
) {
    companion object {
        val INVALID_RECIPE = Recipe()
    }
}