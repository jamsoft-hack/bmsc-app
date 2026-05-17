package bo.com.bmsc.racha.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bmsc.shared.generated.resources.Res
import bmsc.shared.generated.resources.small_pig_mascot
import bo.com.bmsc.app.theme.MentaPrimary
import bo.com.bmsc.app.theme.MentaSurface
import bo.com.bmsc.core.navigation.NavigationHelper
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun RachaOnboardingScreen(
  navigationHelper: NavigationHelper = koinInject(),
  onContinue: () -> Unit = {},
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(MentaSurface)
      .statusBarsPadding()
  ) {
    // Botón Saltar en la parte superior
    TextButton(
      onClick = { navigationHelper.navigateBack() },
      modifier = Modifier
        .align(Alignment.TopStart)
        .padding(16.dp)
    ) {
      Text(
        text = "Saltar",
        color = MentaPrimary,
        style = MaterialTheme.typography.bodyLarge
      )
    }

    // Indicador de progreso
    LinearProgressIndicator(
      progress = { 0.33f },
      modifier = Modifier
        .align(Alignment.TopCenter)
        .padding(top = 16.dp)
        .width(100.dp)
        .height(4.dp),
      color = MentaPrimary,
      trackColor = Color.LightGray.copy(alpha = 0.3f),
    )

    // Contenido central
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 32.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Spacer(modifier = Modifier.height(40.dp))

      // Ilustración del chanchito
      Image(
        painter = painterResource(Res.drawable.small_pig_mascot),
        contentDescription = "Chanchito ahorrador",
        modifier = Modifier.size(200.dp)
      )

      Spacer(modifier = Modifier.height(48.dp))

      // Título
      Text(
        text = "Creá tu racha",
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Subtítulo
      Text(
        text = "Invitá a tu gente y ahorren juntos con una meta común.",
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        lineHeight = 24.sp
      )

      Spacer(modifier = Modifier.weight(1f))
    }

    // Botón Continuar
    Button(
      onClick = onContinue,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp, vertical = 40.dp)
        .height(56.dp)
        .align(Alignment.BottomCenter),
      colors = ButtonDefaults.buttonColors(
        containerColor = MentaPrimary
      ),
      shape = RoundedCornerShape(16.dp)
    ) {
      Text(
        text = "Continuar",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = Color.White
      )
    }
  }
}
