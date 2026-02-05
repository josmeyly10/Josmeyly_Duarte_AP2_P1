package com.example.josmeyly_duarte_ap2_p1.presentacion.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object BorrameList : Screen()
    data object BorrameEdit : Screen()

}