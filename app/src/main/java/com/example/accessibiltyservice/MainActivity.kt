package com.example.accessibiltyservice
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                AntiScrollApp()

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AntiScrollApp() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Anti Scroll App") }) },
        content = { paddingValues ->  // `paddingValues` is passed here
            MainScreenContent(paddingValues)
        }
    )
}

@Composable
fun MainScreenContent(paddingValues: PaddingValues) {
    // Get the context using LocalContext
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("AntiScroll Accessibility Service", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // Button to open Accessibility Settings
        Button(onClick = {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent) // Use the correct context reference here
        }) {
            Text("Enable Accessibility Service")
        }
    }
}




