package com.timcoumu.matrix.feature.auth.navigation

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
    composable<LoginDestination>{
        LoginRoute(
            onBack = onBack,
            onLoggedIn = onLoggedIn,
            onOpenRegister = onOpenRegister,
            showMessage = showMessage,
        )
    }
    composable<RegisterDestination>{
        RegisterRoute(
            onBack = onBack,
            onOpenLogin = onOpenLogin,
            showMessage = showMessage,
        )
    }
}