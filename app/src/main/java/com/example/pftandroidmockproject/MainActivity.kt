package com.example.pftandroidmockproject

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pftandroidmockproject.presentation.profile.OnboardingScreen
import com.example.pftandroidmockproject.presentation.profile.ProfileViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            HealthTrackerApplication()
            OnboardingScreen(
                onProfileSaved = {
                    Toast.makeText(
                        this,
                        "Profile saved",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }
}
