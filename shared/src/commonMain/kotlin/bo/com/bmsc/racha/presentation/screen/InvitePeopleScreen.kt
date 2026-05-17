package bo.com.bmsc.racha.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bo.com.bmsc.app.theme.MentaPrimary
import bo.com.bmsc.app.theme.MentaSurface
import bo.com.bmsc.racha.domain.model.InviteMethod
import bo.com.bmsc.racha.domain.model.Participant
import bo.com.bmsc.racha.presentation.CreateRachaViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvitePeopleScreen(
  viewModel: CreateRachaViewModel = koinViewModel(),
  onBack: () -> Unit = {},
  onContinue: () -> Unit = {},
) {
  val participants by viewModel.availableParticipants.collectAsStateWithLifecycle()
  val selectedMethod by viewModel.selectedInviteMethod.collectAsStateWithLifecycle()
  val selectedCount = viewModel.getSelectedParticipantsCount()

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            "Crear racha · 1 de 3",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
        },
        navigationIcon = {
          IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MentaSurface
        )
      )
    },
    containerColor = MentaSurface
  ) { padding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding)
    ) {
      // Contenido con scroll
      LazyColumn(
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        item {
          // Título y descripción
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
              text = "Invitá a tu gente",
              style = MaterialTheme.typography.headlineMedium,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "De 1 a 6 personas. Pueden ser amigos, familia o pareja.",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        item {
          // Métodos de invitación
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            InviteMethodButton(
              icon = Icons.Filled.Person,
              label = "Contactos",
              isSelected = selectedMethod == InviteMethod.CONTACTS,
              onClick = { viewModel.onInviteMethodSelected(InviteMethod.CONTACTS) },
              modifier = Modifier.weight(1f)
            )
            InviteMethodButton(
              icon = Icons.Filled.Link,
              label = "Link",
              isSelected = selectedMethod == InviteMethod.LINK,
              onClick = { viewModel.onInviteMethodSelected(InviteMethod.LINK) },
              modifier = Modifier.weight(1f)
            )
            InviteMethodButton(
              icon = Icons.Filled.QrCode,
              label = "QR",
              isSelected = selectedMethod == InviteMethod.QR,
              onClick = { viewModel.onInviteMethodSelected(InviteMethod.QR) },
              modifier = Modifier.weight(1f)
            )
          }
        }

        item {
          Text(
            text = "DE MIS CONTACTOS",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold
          )
        }

        // Lista de contactos
        items(participants) { participant ->
          ParticipantItem(
            participant = participant,
            onToggle = { viewModel.onParticipantToggle(participant.id) }
          )
        }
      }

      // Botón Continuar
      Button(
        onClick = {
          if (viewModel.canContinueFromStep1()) {
            viewModel.goToNextStep()
            onContinue()
          }
        },
        enabled = viewModel.canContinueFromStep1(),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical= 24.dp)
          .height(56.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = MentaPrimary
        ),
        shape = RoundedCornerShape(16.dp)
      ) {
        Text(
          text = if (selectedCount > 0) "Continuar · $selectedCount persona${if (selectedCount > 1) "s" else ""}"
          else "Continuar",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}

@Composable
private fun InviteMethodButton(
  icon: ImageVector,
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  OutlinedButton(
    onClick = onClick,
    modifier = modifier.height(56.dp),
    colors = ButtonDefaults.outlinedButtonColors(
      containerColor = if (isSelected) MentaPrimary.copy(alpha = 0.1f) else Color.White,
      contentColor = if (isSelected) MentaPrimary else MaterialTheme.colorScheme.onSurface
    ),
    border = ButtonDefaults.outlinedButtonBorder.copy(
      width = if (isSelected) 2.dp else 1.dp
    ),
    shape = RoundedCornerShape(12.dp)
  ) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(4.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
      Text(label, style = MaterialTheme.typography.bodyMedium)
    }
  }
}

@Composable
private fun ParticipantItem(
  participant: Participant,
  onToggle: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier
      .fillMaxWidth()
      .clickable(onClick = onToggle),
    color = Color.White,
    shape = RoundedCornerShape(12.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Avatar
        Box(
          modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color(android.graphics.Color.parseColor(participant.avatarColor))),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = participant.initials,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
          )
        }

        // Nombre
        Text(
          text = participant.name,
          style = MaterialTheme.typography.bodyLarge,
          fontWeight = FontWeight.Medium
        )
      }

      // Checkbox
      Box(
        modifier = Modifier
          .size(28.dp)
          .clip(CircleShape)
          .background(if (participant.isSelected) MentaPrimary else Color.Transparent)
          .then(
            if (!participant.isSelected) Modifier.background(
              Color.LightGray.copy(alpha = 0.3f),
              CircleShape
            ) else Modifier
          ),
        contentAlignment = Alignment.Center
      ) {
        if (participant.isSelected) {
          Icon(
            Icons.Filled.Check,
            contentDescription = "Seleccionado",
            tint = Color.White,
            modifier = Modifier.size(18.dp)
          )
        }
      }
    }
  }
}
