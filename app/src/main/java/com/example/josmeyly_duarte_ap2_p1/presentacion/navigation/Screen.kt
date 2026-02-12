package com.example.josmeyly_duarte_ap2_p1.presentacion.navigation

sealed class Screen(val route: String) {
    data object ListCerveza : Screen("list_cerveza")
    data object EditCerveza : Screen("edit_cerveza/{cervezaId}") {
        fun createRoute(cervezaId: Int?) = if (cervezaId != null) {
            "edit_cerveza/$cervezaId"
        } else {
            "edit_cerveza/0"
        }
    }
}