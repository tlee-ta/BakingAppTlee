package com.example.android.bakingapptlee.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapptlee.databinding.RecipeIngredientListItemBinding;
import com.example.android.bakingapptlee.models.Ingredient;

import java.util.List;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.RecipeIngredientsAdapterViewHolder> {

    private List<Ingredient> ingredientData;

    public class RecipeIngredientsAdapterViewHolder extends RecyclerView.ViewHolder {

        RecipeIngredientListItemBinding ingredientListItemBinding;

        public RecipeIngredientsAdapterViewHolder(@NonNull RecipeIngredientListItemBinding ingredientListItemBinding) {
            super(ingredientListItemBinding.getRoot());
            this.ingredientListItemBinding = ingredientListItemBinding;
        }
    }

    @NonNull
    @Override
    public RecipeIngredientsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        RecipeIngredientListItemBinding ingredientListItemBinding = RecipeIngredientListItemBinding.inflate(inflater, parent, false);

        return new RecipeIngredientsAdapterViewHolder(ingredientListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientsAdapterViewHolder holder, int position) {
        Ingredient ingredientInfo = ingredientData.get(position);

        String ingredientString = ingredientInfo.getIngredientQuantity() + " " + ingredientInfo.getIngredientMeasure() + " " + ingredientInfo.getIngredientIngredient();
        holder.ingredientListItemBinding.tvRecipeIngredient.setText(ingredientString);
    }

    @Override
    public int getItemCount() {
        if(ingredientData == null){
            return 0;
        }
        return ingredientData.size();
    }

    public void setIngredientData(List<Ingredient> ingredientData) {
        this.ingredientData = ingredientData;
        notifyDataSetChanged();
    }
}
