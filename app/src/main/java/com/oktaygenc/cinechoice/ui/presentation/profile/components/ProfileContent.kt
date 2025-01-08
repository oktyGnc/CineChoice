package com.oktaygenc.cinechoice.ui.presentation.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    viewModel: ProfileViewModel,
    onLogoutSuccess: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, SelectedButtonColor, CircleShape)
                .padding(2.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.nonamefoto),
                contentDescription = "Profile Picture",
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = viewModel.getUserName(),
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        ProfileDetail(
            label = "Email",
            value = viewModel.getUserEmail(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Dark Theme", modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
            Switch(
                checked = isDarkTheme,
                onCheckedChange = onThemeChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = SelectedButtonColor,
                    uncheckedThumbColor = Color.Gray,
                    checkedTrackColor = SelectedButtonColor.copy(alpha = 0.3f),
                    uncheckedTrackColor = Color.LightGray
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.logout(onLogoutSuccess) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                contentColor = TextSelectedButtonColor,
                backgroundColor = SelectedButtonColor
            )
        ) {
            Text("Log Out", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}