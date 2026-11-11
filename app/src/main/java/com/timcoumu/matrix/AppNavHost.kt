package com.timcoumu.matrix

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.timcoumu.core.matrix.model.RoomType
import com.timcoumu.matrix.feature.auth.navigation.LoginDestination
import com.timcoumu.matrix.feature.auth.navigation.RegisterDestination
import com.timcoumu.matrix.feature.auth.navigation.authScreen
import com.timcoumu.matrix.feature.chat.navigation.ChatDestination
import com.timcoumu.matrix.feature.chat.navigation.chatScreen
import com.timcoumu.matrix.feature.main.navigation.HomeDestination
import com.timcoumu.matrix.feature.main.navigation.mainScreen
import com.timcoumu.matrix.feature.room.navigation.CreateDestination
import com.timcoumu.matrix.feature.room.navigation.InviteBoxDestination
import com.timcoumu.matrix.feature.room.navigation.InviteDestination
import com.timcoumu.matrix.feature.room.navigation.RoomInSpaceDestination
import com.timcoumu.matrix.feature.room.navigation.RoomManagerDestination
import com.timcoumu.matrix.feature.room.navigation.createScreen
import com.timcoumu.matrix.feature.user.navigation.ProfileDestination
import com.timcoumu.matrix.feature.user.navigation.userScreen
import com.timcoumu.matrix.ui.MainBottomBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val scope = CoroutineScope(Dispatchers.IO)
    val snackbarHostState = remember { SnackbarHostState() }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val isTopLevel = TopLevelDestination.entries.any { it.route::class.qualifiedName == currentDestination }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            if (isTopLevel)MainBottomBar(
                destinations = TopLevelDestination.entries,
                currentDestination = currentDestination,
                onNavigate = {
                    topDestination ->
                    navController.navigate(topDestination.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
            )
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = HomeDestination
        ) {
            mainScreen(
                onBack = { navController.popBackStack() },
                onOpenLogin = {
                    navController.navigate(LoginDestination)
                },
                onOpenRegister = {
                    navController.navigate(RegisterDestination)
                },
                onOpenChat = {
                    navController.navigate(
                        ChatDestination(
                            roomId = it
                        )
                    )
                },
                onOpenCreate = {
                    navController.navigate(
                        CreateDestination(
                            type = it,
                            isEditor = false
                        )
                    )
                },
                onOpenSpace = {
                    navController.navigate(
                        RoomInSpaceDestination(
                            spaceId = it.id
                        )
                    )
                },
                onOpenEditor = { spaceId, spaceName, spaceAvatar, spaceTopic, spaceJoinRule, type ->
                    navController.navigate(
                        CreateDestination(
                            type = type,
                            isEditor = true,
                            spaceId = spaceId,
                            spaceName = spaceName,
                            spaceAvatar = spaceAvatar,
                            spaceTopic = spaceTopic,
                            spaceJoinRule = spaceJoinRule
                        )
                    )
                },
                onOpenInviteBox = {
                    navController.navigate(InviteBoxDestination)
                },
                onOpenProfile = {
                    navController.navigate(ProfileDestination(it))
                }
            )
            authScreen(
                onBack = { navController.popBackStack() },
                onLoggedIn = { navController.popBackStack() },
                onOpenRegister = {
                    navController.navigate(RegisterDestination) {
                        popUpTo(LoginDestination) { inclusive = true }
                    }
                },
                onOpenLogin = {
                    navController.navigate(LoginDestination) {
                        popUpTo(RegisterDestination) { inclusive = true }
                    }
                },
                showMessage = {
                    scope.launch {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            )
            chatScreen(
                onBack = {
                    navController.popBackStack()
                },
                onOpenRoomManager = {
                    navController.navigate(RoomManagerDestination(it))
                }
            )
            createScreen(
                onBack = {
                    navController.popBackStack()
                },
                showMessage = {
                    scope.launch {
                        snackbarHostState.showSnackbar(it)
                    }
                },
                onLongClick = {},
                onOpenChat = {
                    navController.navigate(
                        ChatDestination(
                            roomId = it
                        )
                    )
                },
                onOpenHome = {
                    navController.navigate(HomeDestination) {
                        popUpTo(RegisterDestination) { inclusive = true }
                    }
                },
                onOpenInvite = {
                    navController.navigate(
                        InviteDestination(
                            roomId = it
                        )
                    )
                }
            )
            userScreen(
                onBack = {
                    navController.popBackStack()
                },
                showMessage = {
                    scope.launch {
                        snackbarHostState.showSnackbar(it)
                    }
                }
            )
        }
    }
}