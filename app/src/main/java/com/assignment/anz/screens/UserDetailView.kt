package com.assignment.anz.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.assignment.anz.model.User

@Composable
fun UserDetailView(
    user: User, onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Detail Screen", style = MaterialTheme.typography.headlineMedium)

        RenderPhoto(
            imageUrl = user.photo,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape),
            contentDescription = "User profile picture",
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Name: ${user.name}", style = MaterialTheme.typography.titleLarge)
        Text(text = "Email: ${user.email}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Username: ${user.username}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onBack) {
            Text("Back to List")
        }
    }
}