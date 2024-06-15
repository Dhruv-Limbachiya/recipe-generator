package com.dhruvv.recipegenerator.di

import android.content.Context
import androidx.room.Room
import com.dhruvv.recipegenerator.common.parser.JsonParser
import com.dhruvv.recipegenerator.common.parser.MoshiParser
import com.dhruvv.recipegenerator.data.api.RecipeGenerator
import com.dhruvv.recipegenerator.data.api.gemini.GeminiRecipeGenerator
import com.dhruvv.recipegenerator.data.db.converters.RecipeConverter
import com.dhruvv.recipegenerator.data.db.room.RecipeGeneratorDB
import com.dhruvv.recipegenerator.data.repo.RecipeRepoImpl
import com.dhruvv.recipegenerator.domain.repo.RecipeRepo
import com.dhruvv.recipegenerator.domain.usecases.GenerateRecipe
import com.dhruvv.recipegenerator.domain.usecases.GetStaticIngredient
import com.dhruvv.recipegenerator.domain.usecases.UseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**
     * Provides a singleton instance of Moshi for JSON parsing.
     */
    @Singleton
    @Provides
    fun provideMoshiParser(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    /**
     * Provides a singleton instance of JsonParser using Moshi.
     *
     * @param moshi The Moshi instance used for JSON parsing.
     */
    @Singleton
    @Provides
    fun provideJsonParser(moshi: Moshi): JsonParser = MoshiParser(moshi)

    /**
     * Provides a singleton instance of RecipeGeneratorDB for database operations.
     *
     * @param context The application context.
     * @param recipeConverter Type converter for Room database operations.
     */
    @Singleton
    @Provides
    fun provideRecipeGeneratorDB(
        @ApplicationContext context: Context,
        recipeConverter: RecipeConverter,
    ): RecipeGeneratorDB =
        Room.databaseBuilder(
            context,
            RecipeGeneratorDB::class.java,
            RecipeGeneratorDB.RECIPE_GENERATOR_DB,
        ).addTypeConverter(recipeConverter)
            .build()

    /**
     * Provides a singleton instance of RecipeRepo for accessing recipe data.
     *
     * @param recipeGeneratorDB The RecipeGeneratorDB instance for database operations.
     * @param recipeGenerator The RecipeGenerator instance for generating recipes.
     */
    @Singleton
    @Provides
    fun provideRecipeGeneratorRepo(
        recipeGeneratorDB: RecipeGeneratorDB,
        recipeGenerator: RecipeGenerator,
    ): RecipeRepo =
        RecipeRepoImpl(
            recipeGeneratorDB.getRecipeDao(),
            recipeGenerator,
        )

    /**
     * Provides a singleton instance of RecipeGenerator for generating recipes.
     *
     * @param moshi The Moshi instance used for JSON parsing.
     */
    @Singleton
    @Provides
    fun provideRecipeGeneratorModel(moshi: Moshi): RecipeGenerator = GeminiRecipeGenerator(moshi)

    /**
     * Provides a singleton instance of UseCase for business logic operations.
     *
     * @param recipeRepo The RecipeRepo instance for accessing recipe data.
     */
    @Singleton
    @Provides
    fun provideUseCase(recipeRepo: RecipeRepo) =
        UseCase(
            GenerateRecipe(recipeRepo),
            GetStaticIngredient()
        )
}
