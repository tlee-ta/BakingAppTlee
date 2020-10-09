package com.example.android.bakingapptlee.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapptlee.R;
import com.example.android.bakingapptlee.RecipeStepDetailActivity;
import com.example.android.bakingapptlee.adapters.RecipeStepAdapter;
import com.example.android.bakingapptlee.databinding.FragmentRecipeStepBinding;
import com.example.android.bakingapptlee.models.Recipe;
import com.example.android.bakingapptlee.models.Step;

public class RecipeStepFragment extends Fragment implements RecipeStepAdapter.RecipeStepAdapterOnClickHandler {

    FragmentRecipeStepBinding recipeStepBinding;

    private Recipe recipeData;
    private boolean twoPane;

    public RecipeStepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        recipeStepBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_step, container, false);

        if (recipeData != null) {

            LinearLayoutManager stepLayoutManager = new LinearLayoutManager(getContext());
            recipeStepBinding.rvRecipeStep.setLayoutManager(stepLayoutManager);
            recipeStepBinding.rvRecipeStep.setHasFixedSize(true);

            RecipeStepAdapter recipeStepAdapter = new RecipeStepAdapter(this, recipeData);
            recipeStepBinding.rvRecipeStep.setAdapter(recipeStepAdapter);
            recipeStepAdapter.setStepData(recipeData.getRecipeSteps());

            if (twoPane) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                RecipeStepDetailFragment stepDetailFragment = new RecipeStepDetailFragment();
                stepDetailFragment.setStepData(recipeData.getRecipeSteps().get(0));
                fragmentManager.beginTransaction()
                        .add(R.id.fr_recipe_step_details, stepDetailFragment)
                        .commit();
            }
        }

        return recipeStepBinding.getRoot();
    }

    public void setRecipeData(Recipe recipe) {
        recipeData = recipe;
    }

    public void setTwoPane(Boolean isTwoPane) {
        twoPane = isTwoPane;
    }

    @Override
    public void onClick(Step selectedStep, Recipe currentRecipe) {
        if (twoPane) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            RecipeStepDetailFragment stepDetailFragment = new RecipeStepDetailFragment();
            stepDetailFragment.setStepData(selectedStep);
            fragmentManager.beginTransaction()
                    .replace(R.id.fr_recipe_step_details, stepDetailFragment)
                    .commit();
        }
        else {
            Bundle b = new Bundle();
            b.putParcelable(getString(R.string.SELECTED_STEP_KEY), selectedStep);
            b.putParcelable(getString(R.string.SELECTED_RECIPE_KEY), currentRecipe);

            final Intent intent = new Intent(getActivity(), RecipeStepDetailActivity.class);
            intent.putExtras(b);
            startActivity(intent);
        }
    }
}