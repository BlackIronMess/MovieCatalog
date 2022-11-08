package com.example.themoviecatalog.database.preferences

import android.content.Context

class AppPreferences (context: Context)  {

    companion object {

        //Información de perfil de usuario
        private const val USER_FAVORITES_LIST = "userFavoritesList"
        private const val NAVIGATION_SELECTED_MOVIE = "selectedMovie"
        private const val NAVIGATION_SEARCH_KEYWORD = "searchKeyword"
        private const val APP_TOOL_LAST_REQUESTED_PAGE = "lastRequestPage"

    }

    private val preferences by lazy { context.getSharedPreferences("sharedData", Context.MODE_PRIVATE) }


    //Información de perfil de usuario
    fun setUserFavoritesList(userFavoritesList : Set<String>) = preferences.edit().putStringSet(USER_FAVORITES_LIST, userFavoritesList).apply()
    fun getUserFavoritesList(): Set<String>? = preferences.getStringSet(USER_FAVORITES_LIST, mutableSetOf() )

    fun setSelectedMovie(selectedMovie : String) = preferences.edit().putString(NAVIGATION_SELECTED_MOVIE, selectedMovie).apply()
    fun getSelectedMovie(): String? = preferences.getString(NAVIGATION_SELECTED_MOVIE, "" )

    fun setSearchKeyword(searchKeyword : String) = preferences.edit().putString(NAVIGATION_SEARCH_KEYWORD, searchKeyword).apply()
    fun getSearchKeyword(): String? = preferences.getString(NAVIGATION_SEARCH_KEYWORD, "" )

    fun setLastRequestedPage(page : Int) = preferences.edit().putInt(APP_TOOL_LAST_REQUESTED_PAGE, page).apply()
    fun getLastRequestedPage(): Int? = preferences.getInt(APP_TOOL_LAST_REQUESTED_PAGE, 0)


}