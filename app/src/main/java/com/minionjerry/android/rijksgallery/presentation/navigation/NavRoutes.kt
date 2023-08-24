package com.minionjerry.android.rijksgallery.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument


private const val ROUTE_ARTOBJECTS = "artobjects"
private const val ROUTE_ARTOBJECT = "artobjects/%s"
private const val ARG_ARTOBJECT_NUMBER = "objectnumber"

sealed class NavRoutes(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object ArtObjects : NavRoutes(ROUTE_ARTOBJECTS)

    object ArtObject : NavRoutes(
        route = String.format(ROUTE_ARTOBJECT, "{$ARG_ARTOBJECT_NUMBER}"),
        arguments = listOf(navArgument(ARG_ARTOBJECT_NUMBER) {
            type = NavType.StringType
        })
    ) {
        fun routeForArtObject(objectNumber: String) =
            String.format(ROUTE_ARTOBJECT, objectNumber)

        fun fromEntry(entry: NavBackStackEntry): String {
            return entry.arguments?.getString(ARG_ARTOBJECT_NUMBER) ?: ""
        }
    }
}
