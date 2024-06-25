package com.dhruvv.recipegenerator.domain.usecases

import com.dhruvv.recipegenerator.domain.repo.RecipeRepo

class SaveRecipe(
    private val recipeRepo: RecipeRepo
) {
    suspend operator fun invoke(recipeId: Int, isSaved: Int) =
        recipeRepo.updateRecipe(id = recipeId, isSaved = isSaved)
}