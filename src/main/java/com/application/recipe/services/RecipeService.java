package com.application.recipe.services;

import com.application.recipe.model.Recipe;

import java.util.HashSet;
import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

}
