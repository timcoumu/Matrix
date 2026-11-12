package com.timcoumu.matrix.feature.auth.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.timcoumu.matrix.feature.auth.Route.LoginRoute
import com.timcoumu.matrix.feature.auth.Route.RegisterRoute
import kotlinx.serialization.Serializable

@Serializable
object LoginDestination
@Serializable
object RegisterDestination
fun NavGraphBuilder.authScreen(
    onBack:()-> Unit,
    onLoggedIn:()->Unit,
    onOpenRegister:()->Unit,
    onOpenLogin:()->Unit,
    showMessage:(value: String)-> Unit
){
    composable<LoginDestination>(
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
        LoginRoute(
            onBack = onBack,
            onLoggedIn = onLoggedIn,
            onOpenRegister = onOpenRegister,
            showMessage = showMessage,
        )
    }
    composable<RegisterDestination>(
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
        RegisterRoute(
            onBack = onBack,
            onOpenLogin = onOpenLogin,
            showMessage = showMessage,
        )
    }
}