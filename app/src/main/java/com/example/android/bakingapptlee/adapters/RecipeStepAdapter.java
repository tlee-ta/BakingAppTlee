package com.example.android.bakingapptlee.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapptlee.databinding.RecipeStepListItemBinding;
import com.example.android.bakingapptlee.models.Recipe;
import com.example.android.bakingapptlee.models.Step;

import java.util.List;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepAdapterViewHolder> {

    private List<Step> stepData;
    private Recipe recipe;

    private final RecipeStepAdapterOnClickHandler recipeStepAdapterOnClickHandler;

    public interface RecipeStepAdapterOnClickHandler {
        void onClick(Step selectedStep, Recipe currentRecipe);
    }

    public RecipeStepAdapter(RecipeStepAdapterOnClickHandler clickHandler, Recipe recipe) {
        recipeStepAdapterOnClickHandler = clickHandler;
        this.recipe = recipe;
    }

    public class RecipeStepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RecipeStepListItemBinding stepListItemBinding;

        public RecipeStepAdapterViewHolder(@NonNull RecipeStepListItemBinding stepListItemBinding) {
            super(stepListItemBinding.getRoot());
            this.stepListItemBinding = stepListItemBinding;
            stepListItemBinding.btnRecipeStep.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Step selectedStep = stepData.get(position);
            recipeStepAdapterOnClickHandler.onClick(selectedStep, recipe);
        }
    }

    @NonNull
    @Override
    public RecipeStepAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        RecipeStepListItemBinding recipeStepListItemBinding = RecipeStepListItemBinding.inflate(inflater, parent, false);

        return new RecipeStepAdapterViewHolder(recipeStepListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepAdapterViewHolder holder, int position) {
        Step stepInfo = stepData.get(position);
        holder.stepListItemBinding.btnRecipeStep.setText(stepInfo.getStepShortDescription());
    }

    @Override
    public int getItemCount() {
        if(stepData == null){
            return 0;
        }
        return stepData.size();
    }

    public void setStepData(List<Step> stepData) {
        this.stepData = stepData;
        notifyDataSetChanged();
    }
}
