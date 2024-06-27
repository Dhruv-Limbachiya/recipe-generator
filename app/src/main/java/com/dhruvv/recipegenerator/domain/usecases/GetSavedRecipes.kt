package com.dhruvv.recipegenerator.domain.usecases

import com.dhruvv.recipegenerator.domain.repo.RecipeRepo

class GetSavedRecipes(
    private val recipeRepo: RecipeRepo
) {
    suspend operator fun invoke() = recipeRepo.getSavedRecipes()
}