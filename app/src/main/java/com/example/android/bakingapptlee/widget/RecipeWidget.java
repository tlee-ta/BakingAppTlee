package com.example.android.bakingapptlee.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.android.bakingapptlee.MainActivity;
import com.example.android.bakingapptlee.R;
import com.example.android.bakingapptlee.widget.RecipeWidgetService;


public class RecipeWidget extends AppWidgetProvider {

    String recipeName;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);

            Intent intentMain = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentMain, 0);
            views.setOnClickPendingIntent(R.id.recipe_widget, pendingIntent);

            Intent intent = new Intent(context, RecipeWidgetService.class);
            views.setRemoteAdapter(R.id.lv_widget_ingredients, intent);

            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.RECIPE_PREFS), Context.MODE_PRIVATE);
            recipeName = sharedPreferences.getString(context.getString(R.string.RECIPE_NAME_PREFS), "");
            if (recipeName == null) {
                recipeName = "Selected recipe will show here";
            }
            views.setTextViewText(R.id.tv_widget_recipe, recipeName);

            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lv_widget_ingredients);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

