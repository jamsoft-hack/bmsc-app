package bo.com.bmsc.auth.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bo.com.bmsc.app.theme.AppDimens
import bo.com.bmsc.app.theme.MentaOnBackground
import bo.com.bmsc.app.theme.MentaOnSurfaceVariant
import bo.com.bmsc.app.theme.MentaOutlineVariant
import bo.com.bmsc.app.theme.MentaPrimary
import bo.com.bmsc.app.theme.MentaSurfaceContainerHigh
import bo.com.bmsc.app.theme.MentaSurfaceContainerLow
import bo.com.bmsc.auth.presentation.LoginEvent
import bo.com.bmsc.auth.presentation.LoginViewModel
import bo.com.bmsc.core.composable.BrandLogo
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    onNavigateToHome: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onNavigateToForgotPassword: () -> Unit = {},
    onNavigateToFaceId: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                LoginEvent.NavigateToHome -> onNavigateToHome()
                LoginEvent.NavigateToRegister -> onNavigateToRegister()
                LoginEvent.NavigateToForgotPassword -> onNavigateToForgotPassword()
                LoginEvent.NavigateToFaceId -> onNavigateToFaceId()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MentaSurfaceContainerLow)
    ) {
        LoginBackground()

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = AppDimens.ContentPadding),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AppDimens.ContentPaddingMedium),
                ) {
                    BrandLogo(size = 120.dp)
                    Text(
                        text = "Mercantil Santa Cruz",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MentaOnBackground,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = AppDimens.ContentPadding,
                        vertical = AppDimens.ContentPadding,
                    ),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = AppDimens.ContentPadding, vertical = 28.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    LoginHeader()

                    CreateAccountButton(onClick = viewModel::onCreateAccountClick)

                    UsernameField(
                        value = uiState.username,
                        onValueChange = viewModel::onUsernameChange,
                        onNext = { focusManager.moveFocus(FocusDirection.Down) },
                    )

                    PasswordField(
                        value = uiState.password,
                        onValueChange = viewModel::onPasswordChange,
                        isVisible = uiState.isPasswordVisible,
                        onToggleVisibility = viewModel::onPasswordVisibilityToggle,
                        onDone = {
                            focusManager.clearFocus()
                            viewModel.onLoginClick()
                        },
                    )

                    RememberMeRow(
                        rememberMe = uiState.rememberMe,
                        onRememberMeChange = viewModel::onRememberMeChange,
                        onForgotPassword = viewModel::onForgotPasswordClick,
                    )

                    AnimatedVisibility(
                        visible = uiState.error != null,
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        uiState.error?.let { error ->
                            Text(
                                text = error,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }

                    LoginButton(
                        isEnabled = uiState.isLoginEnabled,
                        isLoading = uiState.isLoading,
                        onClick = viewModel::onLoginClick,
                    )

                    OrDivider()

                    FaceIdButton(onClick = viewModel::onFaceIdClick)
                }
            }
        }
    }
}

@Composable
private fun LoginBackground() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val center = Offset(size.width / 2f, size.height * 0.42f)
        val radii = listOf(size.width * 0.55f, size.width * 0.75f, size.width * 0.95f)
        radii.forEachIndexed { i, radius ->
            drawCircle(
                color = Color.White.copy(alpha = 0.25f - i * 0.07f),
                radius = radius,
                center = center,
                style = Stroke(width = 1.dp.toPx()),
            )
        }
    }
}

@Composable
private fun LoginHeader() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = "Hola de nuevo",
            style = MaterialTheme.typography.headlineSmall,
            color = MentaOnBackground,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Ingresá para continuar con tu racha",
            style = MaterialTheme.typography.bodyMedium,
            color = MentaOnSurfaceVariant,
        )
    }
}

@Composable
private fun CreateAccountButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(AppDimens.ButtonHeight),
        shape = CircleShape,
        border = BorderStroke(1.5.dp, MentaPrimary),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = MentaPrimary),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Crear cuenta",
                style = MaterialTheme.typography.bodyLarge,
                color = MentaPrimary,
                fontWeight = FontWeight.SemiBold,
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MentaPrimary,
            )
        }
    }
}

@Composable
private fun UsernameField(
    value: String,
    onValueChange: (String) -> Unit,
    onNext: () -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = "Usuario", color = MentaOnSurfaceVariant)
        },
        shape = CircleShape,
        singleLine = true,
        colors = loginFieldColors(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
        ),
        keyboardActions = KeyboardActions(onNext = { onNext() }),
    )
}

@Composable
private fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    isVisible: Boolean,
    onToggleVisibility: () -> Unit,
    onDone: () -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = "Contraseña", color = MentaOnSurfaceVariant)
        },
        shape = CircleShape,
        singleLine = true,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = onToggleVisibility) {
                Icon(
                    imageVector = if (isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = if (isVisible) "Ocultar contraseña" else "Mostrar contraseña",
                    tint = MentaOnSurfaceVariant,
                )
            }
        },
        colors = loginFieldColors(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(onDone = { onDone() }),
    )
}

@Composable
private fun RememberMeRow(
    rememberMe: Boolean,
    onRememberMeChange: (Boolean) -> Unit,
    onForgotPassword: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Switch(
                checked = rememberMe,
                onCheckedChange = onRememberMeChange,
                colors = SwitchDefaults.colors(
                    checkedTrackColor = MentaPrimary,
                    checkedThumbColor = Color.White,
                ),
            )
            Text(
                text = "Recordarme",
                style = MaterialTheme.typography.bodyMedium,
                color = MentaOnBackground,
            )
        }
        TextButton(onClick = onForgotPassword) {
            Text(
                text = "¿Olvidaste tu\ncontraseña?",
                style = MaterialTheme.typography.bodySmall,
                color = MentaPrimary,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun LoginButton(
    isEnabled: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(AppDimens.ButtonHeight),
        enabled = isEnabled,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MentaPrimary,
            contentColor = Color.White,
            disabledContainerColor = MentaSurfaceContainerHigh,
            disabledContentColor = Color.White,
        ),
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 2.dp,
                modifier = Modifier.size(22.dp),
            )
        } else {
            Text(
                text = "Acceder",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun OrDivider() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = MentaOutlineVariant)
        Text(
            text = "o",
            style = MaterialTheme.typography.bodySmall,
            color = MentaOnSurfaceVariant,
        )
        HorizontalDivider(modifier = Modifier.weight(1f), color = MentaOutlineVariant)
    }
}

@Composable
private fun FaceIdButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(AppDimens.ButtonHeight),
        shape = CircleShape,
        border = BorderStroke(1.dp, MentaOutlineVariant),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = MentaOnBackground),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = null,
                tint = MentaOnBackground,
            )
            Text(
                text = "Ingresar con Face ID",
                style = MaterialTheme.typography.bodyLarge,
                color = MentaOnBackground,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun loginFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = MentaPrimary,
    unfocusedBorderColor = MentaOutlineVariant,
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    focusedTextColor = MentaOnBackground,
    unfocusedTextColor = MentaOnBackground,
)
