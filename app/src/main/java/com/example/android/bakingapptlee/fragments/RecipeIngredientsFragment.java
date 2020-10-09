package com.example.android.bakingapptlee.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapptlee.R;
import com.example.android.bakingapptlee.adapters.RecipeIngredientsAdapter;
import com.example.android.bakingapptlee.databinding.FragmentRecipeIngredientsBinding;
import com.example.android.bakingapptlee.models.Recipe;

public class RecipeIngredientsFragment extends Fragment {

    FragmentRecipeIngredientsBinding recipeIngredientsBinding;

    private Recipe recipeData;

    public RecipeIngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            recipeData = savedInstanceState.getParcelable(getString(R.string.SELECTED_RECIPE_KEY));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recipeIngredientsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_ingredients, container, false);

        if (recipeData != null) {

            LinearLayoutManager ingredientsLayoutManager = new LinearLayoutManager(getContext());
            recipeIngredientsBinding.rvRecipeIngredients.setLayoutManager(ingredientsLayoutManager);
            recipeIngredientsBinding.rvRecipeIngredients.setHasFixedSize(true);

            RecipeIngredientsAdapter recipeIngredientsAdapter = new RecipeIngredientsAdapter();
            recipeIngredientsBinding.rvRecipeIngredients.setAdapter(recipeIngredientsAdapter);
            recipeIngredientsAdapter.setIngredientData(recipeData.getRecipeIngredients());
        }

        return recipeIngredientsBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(getString(R.string.SELECTED_RECIPE_KEY), recipeData);

        super.onSaveInstanceState(outState);
    }

    public void setRecipeData(Recipe recipe) {
        recipeData = recipe;
    }
}