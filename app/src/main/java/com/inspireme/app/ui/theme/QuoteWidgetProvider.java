package com.inspireme.app.ui.theme;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.inspireme.app.R;

public class QuoteWidgetProvider extends AppWidgetProvider {

    private static final String ACTION_QUOTE_CLICKED = "com.inspireme.app.QUOTE_CLICKED";
    private static final String ACTION_REFRESH = "com.inspireme.app.REFRESH_WIDGET";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (ACTION_QUOTE_CLICKED.equals(intent.getAction()) || ACTION_REFRESH.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, QuoteWidgetProvider.class));

            for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            }
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        // Called when the first widget is added
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        // Called when the last widget is removed
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Quote quote = QuoteManager.getRandomQuote();

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.quote_widget);
        views.setTextViewText(R.id.widget_quote, quote.getText());
        views.setTextViewText(R.id.widget_author, "- " + quote.getAuthor());

        // Create click intent
        Intent intent = new Intent(context, QuoteWidgetProvider.class);
        intent.setAction(ACTION_QUOTE_CLICKED);

        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, flags);
        views.setOnClickPendingIntent(R.id.widget_container, pendingIntent);

        // Update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    // Method to manually refresh all widgets
    public static void refreshAllWidgets(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, QuoteWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}