package com.oktaygenc.cinechoice.ui.presentation.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oktaygenc.cinechoice.R
import com.oktaygenc.cinechoice.ui.presentation.profile.viewmodel.ProfileViewModel
import com.oktaygenc.cinechoice.ui.theme.SelectedButtonColor
import com.oktaygenc.cinechoice.ui.theme.TextSelectedButtonColor

@Composable
fun ProfileContent(
    viewModel: ProfileViewModel,
    onLogoutSuccess: () -> Unit,
) {
    // Main container for the profile screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile picture container with circular clipping and border
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape) // Clip the image to a circle
                .border(2.dp, SelectedButtonColor, CircleShape) // Border around the circle
                .padding(2.dp)
        ) {
            // Profile picture (placeholder image in this case)
            Image(
                painter = painterResource(id = R.drawable.nonamefoto), // Placeholder image
                contentDescription = "Profile Picture",
                modifier = Modifier.fillMaxSize()
            )
        }

        // Spacer between the profile picture and username
        Spacer(modifier = Modifier.height(16.dp))

        // Display the user's name
        Text(
            text = viewModel.getUserName(), // Fetch username from ViewModel
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )

        // Spacer between username and other profile details
        Spacer(modifier = Modifier.height(8.dp))

        // Display the user's username label and value
        ProfileDetail(
            label = "Username",
            value = viewModel.getUserName(), // Fetch username from ViewModel
        )

        // Spacer between the username and email details
        Spacer(modifier = Modifier.height(16.dp))

        // Display the user's email label and value
        ProfileDetail(
            label = "Email",
            value = viewModel.getUserEmail(), // Fetch user email from ViewModel
        )

        // Spacer before the logout button
        Spacer(modifier = Modifier.height(30.dp))

        // Log out button
        Button(
            onClick = { viewModel.logout(onLogoutSuccess) }, // Call logout function from ViewModel
            modifier = Modifier.fillMaxWidth(), // Button takes full width
            colors = ButtonDefaults.buttonColors(
                contentColor = TextSelectedButtonColor, backgroundColor = SelectedButtonColor // Custom button colors
            )
        ) {
            // Button label
            Text("Log Out", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}
