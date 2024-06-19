package com.dhruvv.recipegenerator.domain.usecases

import com.dhruvv.recipegenerator.domain.repo.RecipeRepo

class GetRecipeById(
    private val recipeRepo: RecipeRepo
)  {
    suspend operator fun invoke(recipeId: Int) = recipeRepo.getRecipe(recipeId)
}