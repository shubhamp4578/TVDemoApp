package com.example.smartbuddyai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.tv.material3.ExperimentalTvMaterial3Api
import com.example.smartbuddyai.core.ui.SmartStudyApp
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            SmartStudyApp()
        }
    }
}
