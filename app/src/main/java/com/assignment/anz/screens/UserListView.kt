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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
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
            RenderPhoto(
                imageUrl = user.photo,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentDescription = stringResource(R.string.user_profile_picture)
            )

            val shortUserInfo = User(id = user.id, name = user.name, email = user.email)
            RenderUserInfo(shortUserInfo, textStyle = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun RenderPhoto(imageUrl: String?, modifier: Modifier, contentDescription: String? = null) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.circular_progress_bar),
        error = painterResource(R.drawable.ic_error)
    )
}


@Composable
fun RenderUserInfo(user: User, textStyle: TextStyle) {
    Column(modifier = Modifier.padding(16.dp)) {
        with(user) {

            Text(text = stringResource(R.string.user_id, id), style = textStyle)

            name?.let { name ->
                Text(text = stringResource(R.string.name, name), style = textStyle)
            }

            username?.let { userName ->
                Text(text = stringResource(R.string.username, userName), style = textStyle)
            }

            email?.let { email ->
                Text(text = stringResource(R.string.email, email), style = textStyle)
            }

            address?.let { address ->
                Text(text = stringResource(R.string.address, address), style = textStyle)
            }

            state?.let { state ->
                Text(text = stringResource(R.string.state, state), style = textStyle)
            }

            country?.let { country ->
                Text(text = stringResource(R.string.country, country), style = textStyle)
            }

            phone?.let { phone ->
                Text(text = stringResource(R.string.phone, phone), style = textStyle)
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
