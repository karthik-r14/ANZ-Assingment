package com.assignment.anz

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assignment.anz.ui_screens.UserDetailScreen
import com.assignment.anz.ui_screens.UserListComposeView
import com.assignment.anz.viewmodel.UserViewModel

import kotlinx.serialization.Serializable

@Serializable
object UserListRoute

@Serializable
object UserDetailRoute

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: UserViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = UserListRoute) {
        composable<UserListRoute> {
            UserListComposeView(
                viewModel = viewModel, onUserClick = { user ->
                    viewModel.selectUser(user)
                    navController.navigate(UserDetailRoute)
                })
        }

        composable<UserDetailRoute> {
            val user = viewModel.selectedUser

            user?.let {
                UserDetailScreen(
                    name = user.name,
                    username = user.username,
                    email = user.email,
                    onBack = { navController.popBackStack() })
            }
        }
    }
}