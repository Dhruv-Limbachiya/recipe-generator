package com.dhruvv.recipegenerator.di

import android.content.Context
import androidx.room.Room
import com.dhruvv.recipegenerator.data.api.RecipeGenerator
import com.dhruvv.recipegenerator.data.api.gemini.GeminiRecipeGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.dhruvv.recipegenerator.data.db.room.RecipeGeneratorDB
import com.dhruvv.recipegenerator.data.repo.RecipeRepoImpl
import com.dhruvv.recipegenerator.domain.repo.RecipeRepo
import com.squareup.moshi.Moshi

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRecipeGeneratorDB(
        @ApplicationContext context: Context
    ): RecipeGeneratorDB = Room.databaseBuilder(
        context,
        RecipeGeneratorDB::class.java,
        RecipeGeneratorDB.RECIPE_GENERATOR_DB
    ).build()

    @Singleton
    @Provides
    fun provideRecipeGeneratorRepo(
        recipeGeneratorDB: RecipeGeneratorDB,
        recipeGenerator: RecipeGenerator
    ): RecipeRepo = RecipeRepoImpl(
        recipeGeneratorDB.getRecipeDao(),
        recipeGenerator
    )

    @Singleton
    @Provides
    fun provideMoshiParser(): Moshi = Moshi.Builder().build()

    @Singleton
    @Provides
    fun provideRecipeGeneratorModel(moshi: Moshi): RecipeGenerator = GeminiRecipeGenerator(moshi)
}