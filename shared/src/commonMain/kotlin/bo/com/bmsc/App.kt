package bo.com.bmsc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import bo.com.bmsc.app.theme.BMSCTheme
import bo.com.bmsc.home.presentation.screen.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object Home

@Composable
@Preview
fun App() {
    BMSCTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen()
        }
    }
}