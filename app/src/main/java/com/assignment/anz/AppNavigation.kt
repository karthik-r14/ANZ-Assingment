package com.assignment.anz

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assignment.anz.screens.UserDetailView
import com.assignment.anz.screens.UserListView
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
            UserListView(
                viewModel = viewModel, onUserClick = { user ->
                    viewModel.selectUser(user)
                    navController.navigate(UserDetailRoute)
                })
        }

        composable<UserDetailRoute> {
            val user = viewModel.selectedUser

            user?.let {
                UserDetailView(
                    user = user,
                    onBack = { navController.popBackStack() })
            }
        }
    }
}