package com.arnoldgoncharenko.smarttasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.arnoldgoncharenko.smarttasks.navigation.SmartTasksNavHost
import com.arnoldgoncharenko.tasks.navigation.TASK_LIST_ROUTE
import com.arnoldgoncharenko.ui.theme.SmartTasksTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            SmartTasksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    SmartTasksNavHost(
                        navController = navController,
                        startDestination = TASK_LIST_ROUTE
                    )
                }
            }
        }
    }
}