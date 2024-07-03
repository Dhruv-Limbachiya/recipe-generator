package com.dhruvv.recipegenerator.domain.usecases

import com.dhruvv.recipegenerator.data.model.Recipe
import com.dhruvv.recipegenerator.domain.repo.RecipeRepo

class RemoveRecipe(
    private val recipeRepo: RecipeRepo
) {
    suspend operator fun invoke(recipe: Recipe) =
        recipeRepo.removeRecipe(recipe)
}