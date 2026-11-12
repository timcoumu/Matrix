package com.timcoumu.matrix.feature.main.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.timcoumu.core.matrix.model.RoomType
import com.timcoumu.core.matrix.model.SpaceState
import com.timcoumu.matrix.feature.main.route.HomeRoute
import com.timcoumu.matrix.feature.main.route.MeRoute
import com.timcoumu.matrix.feature.main.route.SettingRoute
import com.timcoumu.matrix.feature.main.route.SpaceRoute
//import com.timcoumu.matrix.feature.main.ui.MeScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

@Serializable
object SpaceDestination

@Serializable
object SettingDestination

@Serializable
object MeDestination
fun NavGraphBuilder.mainScreen(
    onOpenInviteBox:()-> Unit,
    onBack:()->Unit,
    onOpenLogin:()-> Unit,
    onOpenRegister:()-> Unit,
    onOpenProfile:(userId:String)-> Unit,
    onOpenChat:(roomId: String)-> Unit,
    onOpenCreate:(roomType: RoomType)-> Unit,
    onOpenSpace:(space: SpaceState)-> Unit,
    onOpenEditor:(spaceId: String, spaceName: String, spaceAvatar: String, spaceTopic: String, spaceJoinRule: String, type: RoomType)-> Unit
){
    composable<HomeDestination>(
//        enterTransition = {
//            slideInHorizontally(tween(300)) { it }
//        },
//        exitTransition={
//            slideOutHorizontally(tween(300)) { -it }
//        },
        popEnterTransition = {
            slideInHorizontally(tween(300)) { -it }
        },
//        popExitTransition = {
//            slideOutHorizontally(tween(300)) { it }
//        }
    ){
        HomeRoute(
            onBack = onBack,
            onOpenChat = onOpenChat,
            onOpenCreate = onOpenCreate,
//            onOpenSpace = onOpenSpace,
//            onOpenSetting = onOpenSetting,
//            onOpenMe = onOpenMe
        )
    }
    composable<SpaceDestination>(
//        enterTransition = {
//            slideInHorizontally(tween(300)) { it }
//        },
//        exitTransition={
//            slideOutHorizontally(tween(300)) { -it }
//        },
        popEnterTransition = {
            slideInHorizontally(tween(300)) { -it }
        },
//        popExitTransition = {
//            slideOutHorizontally(tween(300)) { it }
//        }
    ) {
        SpaceRoute(
            onBack = onBack,
            onOpenSpace = onOpenSpace,
            onOpenEditor = onOpenEditor,
//            onOpenHome = onOpenHome,
//            onOpenSetting = onOpenSetting,
//            onOpenMe = onOpenMe
        )
    }
    composable<SettingDestination>(
//        enterTransition = {
//            slideInHorizontally(tween(300)) { it }
//        },
//        exitTransition={
//            slideOutHorizontally(tween(300)) { -it }
//        },
        popEnterTransition = {
            slideInHorizontally(tween(300)) { -it }
        },
//        popExitTransition = {
//            slideOutHorizontally(tween(300)) { it }
//        }
    ) {
        SettingRoute(
            onBack = onBack,
            onOpenInviteBox = onOpenInviteBox,
//            onOpenHome = onOpenHome,
//            onOpenSpace = onOpenSpace,
//            onOpenMe = onOpenMe
        )
    }
    composable<MeDestination>(
//        enterTransition = {
//            slideInHorizontally(tween(300)) { it }
//        },
//        exitTransition={
//            slideOutHorizontally(tween(300)) { -it }
//        },
        popEnterTransition = {
            slideInHorizontally(tween(300)) { -it }
        },
//        popExitTransition = {
//            slideOutHorizontally(tween(300)) { it }
//        }
    ) {
        MeRoute(
            onBack = onBack,
            onOpenLogin = onOpenLogin,
            onOpenRegister = onOpenRegister,
            onOpenProfile = onOpenProfile
        )
    }
}