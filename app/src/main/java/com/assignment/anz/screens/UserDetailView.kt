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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.assignment.anz.R
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
        Text(text = stringResource(R.string.detail_screen), style = MaterialTheme.typography.headlineMedium)

        RenderPhoto(
            imageUrl = user.photo,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape),
            contentDescription = stringResource(R.string.user_profile_picture)
        )

        Spacer(modifier = Modifier.height(16.dp))
        RenderUserInfo(user = user, textStyle = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onBack) {
            Text(stringResource(R.string.back_to_list))
        }
    }
}