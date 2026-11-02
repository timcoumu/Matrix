package com.timcoumu.matrix

import com.timcoumu.matrix.feature.main.navigation.HomeDestination
import com.timcoumu.matrix.feature.main.navigation.MeDestination
import com.timcoumu.matrix.feature.main.navigation.SettingDestination
import com.timcoumu.matrix.feature.main.navigation.SpaceDestination

enum class TopLevelDestination(
    val route: Any,
) {
    HOME(
        route = HomeDestination
    ),
    SPACE(
        route = SpaceDestination
    ),
    SETTING(
        route = SettingDestination
    ),
    ME(
        route = MeDestination
    )
}