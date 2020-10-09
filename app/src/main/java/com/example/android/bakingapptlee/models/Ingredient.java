package com.example.android.bakingapptlee.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {
    @SerializedName("quantity")
    private String ingredientQuantity;
    @SerializedName("measure")
    private String ingredientMeasure;
    @SerializedName("ingredient")
    private String ingredientIngredient;

    public Ingredient(String ingredientQuantity, String ingredientMeasure, String ingredientIngredient) {
        this.ingredientQuantity = ingredientQuantity;
        this.ingredientMeasure = ingredientMeasure;
        this.ingredientIngredient = ingredientIngredient;
    }

    public String getIngredientQuantity() {
        return ingredientQuantity;
    }

    public void setIngredientQuantity(String ingredientQuantity) {
        this.ingredientQuantity = ingredientQuantity;
    }

    public String getIngredientMeasure() {
        return ingredientMeasure;
    }

    public void setIngredientMeasure(String ingredientMeasure) {
        this.ingredientMeasure = ingredientMeasure;
    }

    public String getIngredientIngredient() {
        return ingredientIngredient;
    }

    public void setIngredientIngredient(String ingredientIngredient) {
        this.ingredientIngredient = ingredientIngredient;
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel parcel) {
            return new Ingredient(parcel);
        }

        @Override
        public Ingredient[] newArray(int i) {
            return new Ingredient[i];
        }
    };

    protected Ingredient(Parcel parcel) {
        ingredientQuantity = parcel.readString();
        ingredientMeasure = parcel.readString();
        ingredientIngredient = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ingredientQuantity);
        parcel.writeString(ingredientMeasure);
        parcel.writeString(ingredientIngredient);
    }
}
