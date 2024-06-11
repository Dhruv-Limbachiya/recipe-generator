package com.dhruvv.recipegenerator.data.model

import com.dhruvv.recipegenerator.data.api.model.ApiRecipe

data class Recipe(
    val id: Int,
    val apiRecipe: ApiRecipe,
    val generatedAt: String,
)