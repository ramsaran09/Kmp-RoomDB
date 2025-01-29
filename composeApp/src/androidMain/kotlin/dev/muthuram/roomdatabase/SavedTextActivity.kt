package dev.muthuram.roomdatabase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.muthuram.roomdatabase.presentation.SavedTextViewModel
import org.koin.androidx.compose.koinViewModel

class SavedTextActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowSavedTextScreen(
                navigateBack = { finish() }
            )
        }
    }

    @Composable
    fun ShowSavedTextScreen(
        navigateBack: () -> Unit,
    ) {
        val viewmodel = koinViewModel<SavedTextViewModel>()
        Log.d("TAG", "NextScreen: ${viewmodel.emailId.value}")
        LaunchedEffect(key1 = Unit) {
            viewmodel.getEmail()
        }
        MaterialTheme {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = viewmodel.emailId.value )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    navigateBack()
                }) {
                    Text("Back")
                }
            }
        }
    }
}