package com.example.trustvault

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

// Defines dataStore property for the current context and saves data in "user_preferences" file
private val Context.dataStore by preferencesDataStore(name = "user_preferences")

/**
 * This is a manager class that handles user preferences using Jetpack DataStore.
 *
 * This class provides an interface to save and retrieve user preferences such as theme,
 * language, and notification settings. It uses [DataStore] to store preferences as key-value pairs and
 * allows safe and efficient persistence of data.
 *
 * This class uses [Flow] to asynchronously observe the changes to the preferences and helps to
 * listen in a reactive way to updates in the user's settings.
 *
 * This class provides the following functionality:
 * - Retrieves current user settings for dark theme, language and notifications
 * - Saves the user's preferences
 *
 * The preferences are stored with the following keys:
 * - `THEME_KEY`: For storing the dark theme setting (boolean).
 * - `LANGUAGE_KEY`: For storing the language setting (string).
 * - `NOTIFICATIONS_KEY`: For storing the notifications enabled setting (boolean).
 *
 * Example usage:
 * ```
 * val preferencesManager = UserPreferencesManager(context)
 * preferencesManager.saveDarkTheme(true) // Save the dark theme setting
 * preferencesManager.darkThemeFlow.collect { isDarkMode -> // Observe dark theme changes
 *     println(isDarkMode)
 * }
 * ```
 *
 * @param context The context used to access the DataStore.
 * @author Alex Sugimoto
 */
class UserPreferencesManager(private val context: Context) {
    // Preferences keys
    private val THEME_KEY = booleanPreferencesKey("dark_theme")
    private val LANGUAGE_KEY = stringPreferencesKey("language")
    private val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications_enabled")

    /**
     * A flow that emits the current value of the dark theme preference.
     *
     * This flow retrieves the current setting of the "dark theme" preference from the
     * `DataStore` and emits it as a `Boolean`. If the preference has not been set yet,
     * it defaults to `false`, indicating that the dark theme is disabled by default.
     *
     * The flow is created using the [map] (Elvis) operator, which transforms the data emitted
     * by the `DataStore` into a boolean value. If the preference for dark theme (`THEME_KEY`)
     * does not exist in the `DataStore` or is not set, the Elvis operator (`?:`) ensures that
     * `false` is returned as the fallback value.
     *
     * @see [saveDarkTheme] to save the dark theme preference.
     * @see [getCurrentTheme] to synchronously get the current dark theme preference.
     *
     * @return A flow that emits the current dark theme setting, where `true` indicates dark mode is enabled
     *         and `false` indicates dark mode is disabled (or not set).
     *
     * @author Alex Sugimoto
     */

    val darkThemeFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: false // ?: basically catches a null return and emmits a false value
    }

    val languageFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[LANGUAGE_KEY] ?: "es"
    }

    val notificationsFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[NOTIFICATIONS_KEY] ?: true
    }

    // Save preferences
    // A suspend function can pause its execution at certain points and resume later.
    // A suspend function is useful to perform non-blocking operations (doesn't block the thread)
    suspend fun saveDarkTheme(isDarkMode: Boolean) {
        context.dataStore.edit { preferences -> preferences[THEME_KEY] = isDarkMode }
    }

    suspend fun saveLanguage(language: String) {
        context.dataStore.edit { preferences -> preferences[LANGUAGE_KEY] = language }
    }

    suspend fun saveNotifications(enabled: Boolean) {
        context.dataStore.edit { preferences -> preferences[NOTIFICATIONS_KEY] = enabled }
    }

    // Synchronous way to get values (for initialization in UI)
    fun getCurrentTheme(): Boolean = runBlocking { darkThemeFlow.first() }
    fun getCurrentLanguage(): String = runBlocking { languageFlow.first() }
    fun getCurrentNotifications(): Boolean = runBlocking { notificationsFlow.first() }
}