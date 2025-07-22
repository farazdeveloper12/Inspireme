package com.inspireme.app.ui.theme;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FavoriteManager {

    private static final String PREF_NAME = "FavoriteQuotes";
    private static final String KEY_FAVORITES = "favorites";

    public static boolean isFavorite(Context context, String quote) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> favorites = prefs.getStringSet(KEY_FAVORITES, new HashSet<String>());
        return favorites.contains(quote);
    }

    public static boolean toggleFavorite(Context context, String quote) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> favorites = new HashSet<>(prefs.getStringSet(KEY_FAVORITES, new HashSet<String>()));

        boolean isFavorite;
        if (favorites.contains(quote)) {
            favorites.remove(quote);
            isFavorite = false;
        } else {
            favorites.add(quote);
            isFavorite = true;
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(KEY_FAVORITES, favorites);
        editor.apply();

        return isFavorite;
    }

    public static void addFavorite(Context context, String quote) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> favorites = new HashSet<>(prefs.getStringSet(KEY_FAVORITES, new HashSet<String>()));
        favorites.add(quote);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(KEY_FAVORITES, favorites);
        editor.apply();
    }

    public static void removeFavorite(Context context, String quote) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> favorites = new HashSet<>(prefs.getStringSet(KEY_FAVORITES, new HashSet<String>()));
        favorites.remove(quote);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(KEY_FAVORITES, favorites);
        editor.apply();
    }

    public static ArrayList<String> getFavoriteQuotes(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> favorites = prefs.getStringSet(KEY_FAVORITES, new HashSet<String>());
        return new ArrayList<>(favorites);
    }

    public static void clearAllFavorites(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(KEY_FAVORITES);
        editor.apply();
    }

    public static int getFavoriteCount(Context context) {
        return getFavoriteQuotes(context).size();
    }
}