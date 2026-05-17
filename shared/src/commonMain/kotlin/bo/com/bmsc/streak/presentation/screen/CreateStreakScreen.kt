package bo.com.bmsc.streak.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bo.com.bmsc.app.theme.AppDimens
import bo.com.bmsc.app.theme.MentaOnBackground
import bo.com.bmsc.app.theme.MentaOnSurfaceVariant
import bo.com.bmsc.app.theme.MentaOutlineVariant
import bo.com.bmsc.app.theme.MentaPrimary
import bo.com.bmsc.app.theme.MentaSurfaceContainerLow
import bo.com.bmsc.streak.domain.model.Contact
import bo.com.bmsc.streak.presentation.CreateStreakEvent
import bo.com.bmsc.streak.presentation.CreateStreakUiState
import bo.com.bmsc.streak.presentation.CreateStreakViewModel
import bo.com.bmsc.streak.presentation.InviteMethod
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.absoluteValue

// Pastel palette matching the design — assigned by hashing the contact id
private val AvatarPalette = listOf(
    Color(0xFFF4A4A0), // salmon
    Color(0xFF7DC89C), // mint green
    Color(0xFFAFA4D8), // lavender
    Color(0xFFFFBD6B), // amber
    Color(0xFF9BBCE8), // sky blue
    Color(0xFFF4C4AC), // peach
)

private fun avatarColorFor(id: String): Color =
    AvatarPalette[id.hashCode().absoluteValue % AvatarPalette.size]

@Composable
fun CreateStreakScreen(
    viewModel: CreateStreakViewModel = koinViewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToNextStep: (List<String>) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                CreateStreakEvent.NavigateBack -> onNavigateBack()
                is CreateStreakEvent.NavigateToNextStep -> onNavigateToNextStep(event.participantIds)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MentaSurfaceContainerLow),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CreateStreakTopBar(onBackClick = viewModel::onBackClick)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = AppDimens.ContentPadding),
            ) {
                Spacer(modifier = Modifier.height(AppDimens.ContentPadding))

                Text(
                    text = "Invitá a tu gente",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MentaOnBackground,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "De 1 a 6 personas. Pueden ser amigos, familia o pareja.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MentaOnSurfaceVariant,
                )

                Spacer(modifier = Modifier.height(AppDimens.ContentPadding))

                InviteMethodRow(
                    selected = uiState.inviteMethod,
                    onSelect = viewModel::onInviteMethodChange,
                )

                Spacer(modifier = Modifier.height(AppDimens.ContentPadding))

                Text(
                    text = "DE MIS CONTACTOS",
                    style = MaterialTheme.typography.labelSmall,
                    color = MentaOnSurfaceVariant,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp,
                )

                Spacer(modifier = Modifier.height(AppDimens.ContentPaddingSmall))

                ContactListCard(uiState = uiState, onToggle = viewModel::onContactToggle)

                AnimatedVisibility(
                    visible = uiState.error != null,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    uiState.error?.let { err ->
                        Spacer(modifier = Modifier.height(AppDimens.ContentPaddingSmall))
                        Text(
                            text = err,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(AppDimens.ContentPadding))
            }

            ContinueButton(uiState = uiState, onClick = viewModel::onContinueClick)
        }
    }
}

// ─── Top bar ─────────────────────────────────────────────────────────────────

@Composable
private fun CreateStreakTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = AppDimens.ContentPaddingMedium,
                vertical = AppDimens.ContentPaddingMedium,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.dp, MentaOutlineVariant, CircleShape)
                .clickable(onClick = onBackClick),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = MentaOnBackground,
                modifier = Modifier.size(20.dp),
            )
        }

        Text(
            text = "Crear racha · 1 de 3",
            style = MaterialTheme.typography.titleMedium,
            color = MentaOnBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = AppDimens.ContentPaddingMedium),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )

        // Mirror the back button size to keep the title visually centered
        Spacer(modifier = Modifier.size(40.dp))
    }
}

// ─── Invite method chips ──────────────────────────────────────────────────────

@Composable
private fun InviteMethodRow(
    selected: InviteMethod,
    onSelect: (InviteMethod) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppDimens.ContentPaddingSmall),
    ) {
        InviteMethodChip(
            label = "Contactos",
            icon = Icons.Default.Person,
            isSelected = selected == InviteMethod.CONTACTS,
            onClick = { onSelect(InviteMethod.CONTACTS) },
        )
        InviteMethodChip(
            label = "Link",
            icon = Icons.Default.Link,
            isSelected = selected == InviteMethod.LINK,
            onClick = { onSelect(InviteMethod.LINK) },
        )
        InviteMethodChip(
            label = "QR",
            icon = Icons.Default.QrCode,
            isSelected = selected == InviteMethod.QR,
            onClick = { onSelect(InviteMethod.QR) },
        )
    }
}

@Composable
private fun InviteMethodChip(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(AppDimens.ButtonRadius),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = if (isSelected) MentaPrimary else MentaOutlineVariant,
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isSelected) MentaPrimary.copy(alpha = 0.06f) else Color.White,
            contentColor = if (isSelected) MentaPrimary else MentaOnBackground,
        ),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            horizontal = AppDimens.ContentPaddingMedium,
            vertical = 8.dp,
        ),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
        )
        Spacer(modifier = Modifier.size(AppDimens.ContentPaddingSmall))
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

// ─── Contact list ─────────────────────────────────────────────────────────────

@Composable
private fun ContactListCard(
    uiState: CreateStreakUiState,
    onToggle: (String) -> Unit,
) {
    if (uiState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(color = MentaPrimary)
        }
        return
    }

    Surface(
        shape = RoundedCornerShape(AppDimens.CardRadius),
        color = Color.White,
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            uiState.contacts.forEachIndexed { index, contact ->
                val isSelected = contact.id in uiState.selectedIds
                ContactItem(
                    contact = contact,
                    isSelected = isSelected,
                    onToggle = { onToggle(contact.id) },
                )
                if (index < uiState.contacts.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(start = (AppDimens.ContentPadding.value + 48 + 12).dp),
                        color = MentaOutlineVariant.copy(alpha = 0.5f),
                        thickness = 0.5.dp,
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactItem(
    contact: Contact,
    isSelected: Boolean,
    onToggle: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle)
            .padding(
                horizontal = AppDimens.ContentPadding,
                vertical = AppDimens.ContentPaddingMedium,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppDimens.ContentPaddingMedium),
    ) {
        ContactAvatar(contact = contact)

        Text(
            text = contact.name,
            style = MaterialTheme.typography.bodyLarge,
            color = MentaOnBackground,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f),
        )

        SelectionIndicator(isSelected = isSelected)
    }
}

@Composable
private fun ContactAvatar(contact: Contact) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .background(color = avatarColorFor(contact.id), shape = CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = contact.initials,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun SelectionIndicator(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .then(
                if (isSelected) {
                    Modifier.background(color = MentaPrimary, shape = CircleShape)
                } else {
                    Modifier.border(width = 1.5.dp, color = MentaOutlineVariant, shape = CircleShape)
                }
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp),
            )
        }
    }
}

// ─── Continue button ──────────────────────────────────────────────────────────

@Composable
private fun ContinueButton(
    uiState: CreateStreakUiState,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = AppDimens.ContentPadding,
                vertical = AppDimens.ContentPadding,
            ),
    ) {
        androidx.compose.material3.Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(AppDimens.ButtonHeight),
            enabled = uiState.canContinue,
            shape = CircleShape,
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = MentaPrimary,
                contentColor = Color.White,
                disabledContainerColor = MentaOutlineVariant.copy(alpha = 0.4f),
                disabledContentColor = Color.White,
            ),
        ) {
            if (uiState.isSubmitting) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(22.dp),
                )
            } else {
                Text(
                    text = uiState.buttonLabel,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}
