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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.oktaygenc.cinechoice.R

@Composable
fun ProfileContent(isDarkTheme: Boolean, onThemeChange: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profil Fotoğrafı
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colors.primary, CircleShape)
                .padding(2.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Profil fotoğrafını buraya ekleyebilirsiniz
                contentDescription = "Profile Picture", modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Kullanıcı Adı
        Text(
            text = "Oktay Genç", style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // E-posta
        ProfileDetail(label = "Email", value = "oktaygenc@gmail.com")

        Spacer(modifier = Modifier.height(16.dp))

        // Dark theme seçeneği
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Dark Theme", modifier = Modifier.weight(1f))
            Switch(
                checked = isDarkTheme, onCheckedChange = onThemeChange
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Çıkış Butonu
        Button(
            onClick = { /* Çıkış işlemi yapılacak */ }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log Out")
        }
    }
}