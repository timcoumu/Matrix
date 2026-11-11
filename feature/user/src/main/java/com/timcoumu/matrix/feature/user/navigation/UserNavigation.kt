package com.timcoumu.matrix.feature.user.navigation

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
    composable<ProfileDestination> {
        val profileDestination = it.toRoute<ProfileDestination>()
        ProfileRoute(
            onBack = onBack,
            showMessage = showMessage,
            userId = profileDestination.userId
        )
    }
}
