package br.com.smvtech.testetecnico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.smvtech.testetecnico.presentation.navigation.MainNavigation
import br.com.smvtech.testetecnico.presentation.navigation.Screens
import br.com.smvtech.testetecnico.presentation.screens.HomeScreen
import br.com.smvtech.testetecnico.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val startNavigation = Screens.LOGIN_SCREEN.name
                MainNavigation(startNavigation = startNavigation)
            }
        }
    }
}