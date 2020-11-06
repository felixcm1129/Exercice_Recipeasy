package com.example.interfacerecipeasy.api;

import com.example.interfacerecipeasy.model.Recipe;

import java.util.ArrayList;

public interface RecipesFetchListener {
    void onResponse(ArrayList<Recipe> recipes);
}
