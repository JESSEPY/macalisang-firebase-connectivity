package com.example.macalisang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.macalisang.ui.theme.MacalisangTheme
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MacalisangTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var name by remember { mutableStateOf("Android") }
    var inputText by remember { mutableStateOf("") }
    val database = Firebase.database
    val myRef = database.getReference("messages")

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Greeting(
                name = name,
                modifier = Modifier.clickable {
                    // Send name to Firebase when the text is clicked
                    myRef.push().setValue(name)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text("Enter your message") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                // Send input text to Firebase when the button is clicked
                if (inputText.isNotBlank()) {
                    myRef.push().setValue(inputText)
                    inputText = "" // Clear the input field after sending
                }
            }) {
                Text("Submit")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MacalisangTheme {
        Greeting("Android")
    }
}