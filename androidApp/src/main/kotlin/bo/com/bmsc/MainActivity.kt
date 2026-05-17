package bo.com.bmsc

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import bo.com.bmsc.core.di.initModules
import org.koin.core.logger.Level

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var instance: MainActivity
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        instance = this

        initModules(
            config = {
//                androidLogger(Level.DEBUG)
//                androidContext(this)
            },
        )


        setContent {
            val darkStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)

            enableEdgeToEdge(
                statusBarStyle = darkStyle,
                navigationBarStyle = darkStyle,
            )

            App()
        }
    }

    private fun isDarkThemeOn(): Boolean {
        return resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}


@Preview
@Composable
fun AppAndroidPreview() {
    App()
}