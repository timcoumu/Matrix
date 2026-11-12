package com.timcoumu.matrix.feature.user.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.timcoumu.matrix.feature.user.route.ProfileRoute
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDestination(
    val userId: String,
)
fun NavGraphBuilder.userScreen(
    onBack:()-> Unit,
    showMessage:(String)-> Unit
){
    composable<ProfileDestination>(
        enterTransition = {
            slideInHorizontally(tween(300)) { it }
        },
        exitTransition={
            slideOutHorizontally(tween(300)) { -it }
        },
        popEnterTransition = {
            slideInHorizontally(tween(300)) { -it }
        },
        popExitTransition = {
            slideOutHorizontally(tween(300)) { it }
        }
    ){
        val profileDestination = it.toRoute<ProfileDestination>()
        ProfileRoute(
            onBack = onBack,
            showMessage = showMessage,
            userId = profileDestination.userId
        )
    }
}
