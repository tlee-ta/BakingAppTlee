package com.example.android.bakingapptlee.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.bakingapptlee.R;
import com.example.android.bakingapptlee.databinding.RecipeListItemBinding;
import com.example.android.bakingapptlee.models.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private List<Recipe> recipeData;

    private final RecipeAdapterOnClickHandler recipeAdapterOnClickHandler;

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe selectedRecipe);
    }

    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler) {
        recipeAdapterOnClickHandler = clickHandler;
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RecipeListItemBinding recipeListItemBinding;

        public RecipeAdapterViewHolder(@NonNull RecipeListItemBinding recipeListItemBinding) {
            super(recipeListItemBinding.getRoot());
            this.recipeListItemBinding = recipeListItemBinding;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Recipe selectedRecipe = recipeData.get(position);
            recipeAdapterOnClickHandler.onClick(selectedRecipe);
        }
    }

    @NonNull
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        RecipeListItemBinding recipeListItemBinding = RecipeListItemBinding.inflate(inflater, parent, false);

        return new RecipeAdapterViewHolder(recipeListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapterViewHolder holder, int position) {
        Recipe recipeInfo = recipeData.get(position);
        holder.recipeListItemBinding.tvRecipeName.setText(recipeInfo.getRecipeName());
        if (!recipeInfo.getRecipeImage().isEmpty()) {
            Glide.with(holder.itemView)
                    .load(recipeInfo.getRecipeImage())
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .fallback(R.drawable.ic_launcher_background)
                    .into(holder.recipeListItemBinding.ivRecipeImageBg);
        }
    }

    @Override
    public int getItemCount() {
        if(recipeData == null){
            return 0;
        }
        return recipeData.size();
    }

    public void setRecipeData(List<Recipe> recipeData) {
        this.recipeData = recipeData;
        notifyDataSetChanged();
    }
}
