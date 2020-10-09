package com.example.android.bakingapptlee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.android.bakingapptlee.adapters.RecipeAdapter;
import com.example.android.bakingapptlee.databinding.ActivityMainBinding;
import com.example.android.bakingapptlee.models.Ingredient;
import com.example.android.bakingapptlee.models.Recipe;
import com.example.android.bakingapptlee.network.RecipeInterface;
import com.example.android.bakingapptlee.widget.RecipeWidget;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {

    private static final String RECIPES_BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    ActivityMainBinding mainBinding;

    private RecipeAdapter recipeAdapter;
    private RecipeInterface recipeInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int gridCount = (int)displayMetrics.widthPixels/800;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, gridCount);
        mainBinding.rvRecipeList.setLayoutManager(gridLayoutManager);
        mainBinding.rvRecipeList.setHasFixedSize(true);

        recipeAdapter = new RecipeAdapter(this);
        mainBinding.rvRecipeList.setAdapter(recipeAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RECIPES_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        recipeInterface = retrofit.create(RecipeInterface.class);
        getRecipes();
    }

    private void getRecipes() {
        Call<List<Recipe>> callRecipes = recipeInterface.getRecipes();

        callRecipes.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (!response.isSuccessful()) {
                    Log.i("Main", String.valueOf(response.code()));
                    mainBinding.tvErrorMessage.setVisibility(View.VISIBLE);
                    mainBinding.rvRecipeList.setVisibility(View.INVISIBLE);
                    return;
                }

                List<Recipe> recipesData = response.body();
                recipeAdapter.setRecipeData(recipesData);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.i("Main", t.getMessage());
                mainBinding.tvErrorMessage.setVisibility(View.VISIBLE);
                mainBinding.rvRecipeList.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onClick(Recipe selectedRecipe) {
        final SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.RECIPE_PREFS), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.RECIPE_NAME_PREFS), selectedRecipe.getRecipeName());
        editor.putString(getString(R.string.INGREDIENT_PREFS), getIngredients(selectedRecipe));
        editor.apply();

        sendRecipeToWidget(this);

        Intent intent = new Intent(this, RecipeStepActivity.class);
        intent.putExtra(getString(R.string.SELECTED_RECIPE_KEY), selectedRecipe);
        startActivity(intent);
    }

    public void sendRecipeToWidget(Context context) {
        Intent intent = new Intent(context, RecipeWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, RecipeWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }

    public String getIngredients(Recipe recipe) {
        StringBuilder ingredients = new StringBuilder();
        if (recipe != null) {
            for(int i = 0; i < recipe.getRecipeIngredients().size(); i++) {
                Ingredient ingredientInfo = recipe.getRecipeIngredients().get(i);
                String ingredientString = ingredientInfo.getIngredientQuantity() + " " + ingredientInfo.getIngredientMeasure() + " " + ingredientInfo.getIngredientIngredient();
                ingredients.append(ingredientString);
                ingredients.append(",");
            }
        }
        return ingredients.toString();
    }
}