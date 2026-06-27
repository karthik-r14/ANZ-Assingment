package com.assignment.anz.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.assignment.anz.R
import com.assignment.anz.model.User
import com.assignment.anz.model.UserUiState
import com.assignment.anz.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListView(
    viewModel: UserViewModel, onUserClick: (User) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("User List") })
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is UserUiState.Loading -> CircularProgressIndicator()
                is UserUiState.Error -> ErrorScreen(state)
                is UserUiState.Success -> UsersListScreen(
                    state,
                    onUserClick,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                )
            }
        }
    }
}

@Composable
fun UsersListScreen(
    successState: UserUiState.Success,
    onUserClick: (User) -> Unit,
    modifier: Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        modifier = modifier, contentPadding = contentPadding
    ) {
        items(successState.users, key = { it.id }) { user ->
            UserCard(
                user,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { onUserClick(user) })
        }
    }
}

@Composable
fun UserCard(user: User, modifier: Modifier) {
    Card(
        modifier = modifier
    ) {

        Row {
            AsyncImage(
                model = user.photo,
                contentDescription = "User profile picture",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.circular_progress_bar),
                error = painterResource(R.drawable.ic_error)
            )


            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = user.name, style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = user.email, style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun ErrorScreen(errorState: UserUiState.Error) {
    Text(
        text = "Error: ${errorState.message}", color = MaterialTheme.colorScheme.error
    )
}
