package com.example.android.bakingapptlee.network;

import com.example.android.bakingapptlee.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeInterface {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
