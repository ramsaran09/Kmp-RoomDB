package dev.muthuram.roomdatabase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.muthuram.roomdatabase.presentation.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun App(
    navigateToNextScreen: () -> Unit
) {
    val viewmodel = koinViewModel<HomeViewModel>()
    val emailId by viewmodel.emailId.collectAsState()
    LaunchedEffect(key1 = Unit) {
        viewmodel.getEmail()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = emailId,
            onValueChange = { viewmodel.onEmailIdChange(it) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewmodel.onSaveUser()
                navigateToNextScreen()
            }
        ) {
            Text("Save")
        }
    }

}