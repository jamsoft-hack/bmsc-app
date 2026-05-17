package bo.com.bmsc.app.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val MentaLightColorScheme = lightColorScheme(
  primary               = MentaPrimary,
  onPrimary             = MentaOnPrimary,
  primaryContainer      = MentaPrimaryContainer,
  onPrimaryContainer    = MentaOnPrimaryContainer,
  secondary             = MentaSecondary,
  onSecondary           = MentaOnSecondary,
  secondaryContainer    = MentaSecondaryContainer,
  onSecondaryContainer  = MentaOnSecondaryContainer,
  tertiary              = MentaTertiary,
  onTertiary            = MentaOnTertiary,
  tertiaryContainer     = MentaTertiaryContainer,
  onTertiaryContainer   = MentaOnTertiaryContainer,
  error                 = MentaError,
  onError               = MentaOnError,
  errorContainer        = MentaErrorContainer,
  onErrorContainer      = MentaOnErrorContainer,
  background            = MentaBackground,
  onBackground          = MentaOnBackground,
  surface               = MentaSurface,
  onSurface             = MentaOnSurface,
  surfaceVariant        = MentaSurfaceVariant,
  onSurfaceVariant      = MentaOnSurfaceVariant,
  surfaceContainerLowest  = MentaSurfaceContainerLowest,
  surfaceContainerLow     = MentaSurfaceContainerLow,
  surfaceContainer        = MentaSurfaceContainer,
  surfaceContainerHigh    = MentaSurfaceContainerHigh,
  surfaceContainerHighest = MentaSurfaceContainerHighest,
  outline               = MentaOutline,
  outlineVariant        = MentaOutlineVariant,
  scrim                 = MentaScrim,
  inverseSurface        = MentaInverseSurface,
  inverseOnSurface      = MentaInverseOnSurface,
  inversePrimary        = MentaInversePrimary,
)

@Composable
fun BMSCTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = MentaLightColorScheme,
    typography  = appTypography(),
    shapes      = MentaShapes,
    content     = content
  )
}