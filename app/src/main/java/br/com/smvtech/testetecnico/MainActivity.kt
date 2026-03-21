package br.com.smvtech.testetecnico

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import br.com.smvtech.testetecnico.data.local.sharedpreferences.SharedPrefsService
import br.com.smvtech.testetecnico.presentation.navigation.MainNavigation
import br.com.smvtech.testetecnico.presentation.navigation.Screens
import br.com.smvtech.testetecnico.presentation.ui.theme.AppTheme
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sharedPrefs: SharedPrefsService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val startNavigation = if (sharedPrefs.isLoggedIn()) {
            Screens.HOME_SCREEN.name
        } else {
            Screens.LOGIN_SCREEN.name
        }
        setContent {
            AppTheme {
                MainNavigation(startNavigation = startNavigation)
            }
        }
    }
}
