package com.example.android.bakingapptlee.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapptlee.R;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private String[] ingredients;

    public RecipeRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
    }

    public void getPrefData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.RECIPE_PREFS), Context.MODE_PRIVATE);
        String allIngredients = sharedPreferences.getString(mContext.getString(R.string.INGREDIENT_PREFS), "");
        ingredients = allIngredients.split(",");
    }

    @Override
    public void onCreate() {
        getPrefData();
    }

    @Override
    public void onDataSetChanged() {
        getPrefData();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        if (ingredients == null) return 1;
        return ingredients.length;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_list_item);
        if (ingredients != null) {
            views.setTextViewText(R.id.tv_widget_ingredient_item, ingredients[i]);
        }

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}