package com.example.android.bakingapptlee.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    @SerializedName("id")
    private int recipeId;
    @SerializedName("name")
    private String recipeName;
    @SerializedName("servings")
    private String recipeServings;
    @SerializedName("image")
    private String recipeImage;
    @SerializedName("ingredients")
    private List<Ingredient> recipeIngredients;
    @SerializedName("steps")
    private List<Step> recipeSteps;

    public Recipe(int recipeId, String recipeName, String recipeServings, String recipeImage, List<Ingredient> recipeIngredients, List<Step> recipeSteps) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeServings = recipeServings;
        this.recipeImage = recipeImage;
        this.recipeIngredients = recipeIngredients;
        this.recipeSteps = recipeSteps;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeServings() {
        return recipeServings;
    }

    public void setRecipeServings(String recipeServings) {
        this.recipeServings = recipeServings;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public List<Ingredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<Ingredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public List<Step> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(List<Step> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }


    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel parcel) {
            return new Recipe(parcel);
        }

        @Override
        public Recipe[] newArray(int i) {
            return new Recipe[i];
        }
    };

    protected Recipe(Parcel parcel) {
        recipeId = parcel.readInt();
        recipeName = parcel.readString();
        recipeServings = parcel.readString();
        recipeImage = parcel.readString();
        recipeIngredients = new ArrayList<>();
        parcel.readList(recipeIngredients, Ingredient.class.getClassLoader());
        recipeSteps = new ArrayList<>();
        parcel.readList(recipeSteps, Step.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(recipeId);
        parcel.writeString(recipeName);
        parcel.writeString(recipeServings);
        parcel.writeString(recipeImage);
        parcel.writeList(recipeIngredients);
        parcel.writeList(recipeSteps);
    }
}
