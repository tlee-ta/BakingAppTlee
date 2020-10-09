package com.example.android.bakingapptlee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import com.example.android.bakingapptlee.databinding.ActivityRecipeStepBinding;
import com.example.android.bakingapptlee.fragments.RecipeIngredientsFragment;
import com.example.android.bakingapptlee.fragments.RecipeStepFragment;
import com.example.android.bakingapptlee.models.Recipe;

public class RecipeStepActivity extends AppCompatActivity {

    ActivityRecipeStepBinding recipeStepBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeStepBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_step);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent == null) {
                closeOnError();
            }

            if (intent.hasExtra(getString(R.string.SELECTED_RECIPE_KEY)) ) {
                final Recipe recipe = intent.getParcelableExtra(getString(R.string.SELECTED_RECIPE_KEY));

                if (recipe == null) {
                    closeOnError();
                    return;
                }

                setTitle(recipe.getRecipeName());

                if (!recipe.getRecipeServings().isEmpty()){
                    recipeStepBinding.tvServings.setVisibility(View.VISIBLE);
                    String servings = getString(R.string.servings) + recipe.getRecipeServings();
                    recipeStepBinding.tvServings.setText(servings);
                }
                else {
                    recipeStepBinding.tvServings.setVisibility(View.GONE);
                }

                FragmentManager fragmentManager = getSupportFragmentManager();

                RecipeIngredientsFragment ingredientsFragment = new RecipeIngredientsFragment();
                ingredientsFragment.setRecipeData(recipe);
                fragmentManager.beginTransaction()
                        .add(R.id.fr_recipe_ingredients, ingredientsFragment)
                        .commit();

                boolean twoPane = recipeStepBinding.stepsContainer != null;

                RecipeStepFragment stepFragment = new RecipeStepFragment();
                stepFragment.setRecipeData(recipe);
                stepFragment.setTwoPane(twoPane);
                fragmentManager.beginTransaction()
                        .add(R.id.fr_recipe_steps, stepFragment)
                        .commit();
            }
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
    }
}
