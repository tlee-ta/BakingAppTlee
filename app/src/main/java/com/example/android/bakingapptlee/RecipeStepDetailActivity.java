package com.example.android.bakingapptlee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.bakingapptlee.databinding.ActivityRecipeStepDetailBinding;
import com.example.android.bakingapptlee.fragments.RecipeStepDetailFragment;
import com.example.android.bakingapptlee.models.Recipe;
import com.example.android.bakingapptlee.models.Step;

public class RecipeStepDetailActivity extends AppCompatActivity {

    ActivityRecipeStepDetailBinding recipeStepDetailBinding;

    private int stepSize;
    private int stepId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeStepDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_step_detail);

        if(savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent == null){
                closeOnError();
            }

            if (intent.hasExtra(getString(R.string.SELECTED_STEP_KEY))) {
                final Step step = intent.getParcelableExtra(getString(R.string.SELECTED_STEP_KEY));
                final Recipe recipe = intent.getParcelableExtra(getString(R.string.SELECTED_RECIPE_KEY));

                if(step == null) {
                    closeOnError();
                    return;
                }

                if(recipe == null) {
                    closeOnError();
                    return;
                }

                setTitle(recipe.getRecipeName());

                stepSize = recipe.getRecipeSteps().size() - 1;
                stepId = step.getStepId();

                updateStepDetailsUI(step);

                if (stepId == stepSize) {
                    recipeStepDetailBinding.btnNextStep.setEnabled(false);
                }
                else if (stepId == 0) {
                    recipeStepDetailBinding.btnPreviousStep.setEnabled(false);
                }

                recipeStepDetailBinding.btnPreviousStep.setOnClickListener(view -> updateStepDetails(false, recipe));

                recipeStepDetailBinding.btnNextStep.setOnClickListener(view -> updateStepDetails(true, recipe));
            }
        }
    }

    public void updateStepDetails(boolean increaseStep, Recipe currentRecipe) {
        if(increaseStep && stepSize > stepId) {
            stepId++;
            recipeStepDetailBinding.btnPreviousStep.setEnabled(true);
            if (stepId == stepSize) {
                recipeStepDetailBinding.btnNextStep.setEnabled(false);
            }
        }
        else if(!increaseStep && stepId > 0) {
            stepId--;
            recipeStepDetailBinding.btnNextStep.setEnabled(true);
            if (stepId == 0) {
                recipeStepDetailBinding.btnPreviousStep.setEnabled(false);
            }
        }
        updateStepDetailsUI(currentRecipe.getRecipeSteps().get(stepId));
    }

    public void updateStepDetailsUI(Step step) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeStepDetailFragment stepDetailFragment = new RecipeStepDetailFragment();
        stepDetailFragment.setStepData(step);
        fragmentManager.beginTransaction()
                .replace(R.id.fr_recipe_step_details, stepDetailFragment)
                .commit();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
    }
}