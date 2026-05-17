package bo.com.bmsc.racha.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bo.com.bmsc.app.theme.MentaPrimary
import bo.com.bmsc.app.theme.MentaSurface
import bo.com.bmsc.racha.domain.model.ContributionFrequency
import bo.com.bmsc.racha.domain.model.SavingsGoal
import bo.com.bmsc.racha.presentation.CreateRachaViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmRachaScreen(
  viewModel: CreateRachaViewModel = koinViewModel(),
  onBack: () -> Unit = {},
  onConfirm: () -> Unit = {},
) {
  val config by viewModel.rachaConfig.collectAsStateWithLifecycle()
  val termsAccepted by viewModel.termsAccepted.collectAsStateWithLifecycle()
  val dynamicKey by viewModel.dynamicKey.collectAsStateWithLifecycle()
  val allParticipants by viewModel.availableParticipants.collectAsStateWithLifecycle()
  val selectedParticipants = allParticipants.filter { it.isSelected }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            "Crear racha · 3 de 3",
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
      LazyColumn(
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
      ) {
        item {
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
              text = "Confirmá tu racha",
              style = MaterialTheme.typography.headlineMedium,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "Revisá los detalles antes de crear tu temporada de ahorro.",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        // Resumen en tarjeta
        item {
          Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 2.dp
          ) {
            Column(
              modifier = Modifier.padding(20.dp),
              verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
              // Nombre de la temporada
              Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                  text = "NOMBRE DE LA TEMPORADA",
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                OutlinedTextField(
                  value = config.seasonName ?: "",
                  onValueChange = { viewModel.onSeasonNameChanged(it) },
                  modifier = Modifier.fillMaxWidth(),
                  placeholder = { Text("Ej: Los Ahorradores") },
                  shape = RoundedCornerShape(12.dp),
                  colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MentaSurface,
                    focusedContainerColor = MentaSurface
                  )
                )
              }

              Divider(color = Color.LightGray.copy(alpha = 0.3f))

              // Meta
              config.goal?.let { goal ->
                SummaryRow(
                  label = "Meta",
                  value = getGoalDisplayName(goal)
                )
              }

              // Participantes
              SummaryRow(
                label = "Participantes",
                value = "${selectedParticipants.size} persona${if (selectedParticipants.size > 1) "s" else ""}"
              )

              // Frecuencia
              SummaryRow(
                label = "Frecuencia",
                value = getFrequencyDisplayName(config.frequency)
              )

              // Monto mínimo
              SummaryRow(
                label = "Monto mínimo",
                value = "Bs ${config.minimumAmount}"
              )

              // Fecha de cierre
              if (config.closeDate.isNotBlank()) {
                SummaryRow(
                  label = "Fecha de cierre",
                  value = config.closeDate
                )
              }

              Divider(color = Color.LightGray.copy(alpha = 0.3f))

              // Fondos bloqueados
              Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MentaPrimary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(10.dp)
              ) {
                Row(
                  modifier = Modifier.padding(12.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = "Fondos bloqueados hasta:",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = MentaPrimary
                  )
                  if (config.closeDate.isNotBlank()) {
                    Text(
                      text = config.closeDate,
                      style = MaterialTheme.typography.bodySmall,
                      fontWeight = FontWeight.Bold,
                      color = MentaPrimary
                    )
                  }
                }
              }
            }
          }
        }

        // Términos y condiciones
        item {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
          ) {
            Checkbox(
              checked = termsAccepted,
              onCheckedChange = { viewModel.onTermsAcceptedToggle() },
              colors = CheckboxDefaults.colors(
                checkedColor = MentaPrimary
              )
            )
            Text(
              text = "Acepto los términos y condiciones del ahorro colaborativo. Los fondos quedarán bloqueados hasta la fecha de cierre.",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        // Clave dinámica
        item {
          Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
              text = "CLAVE DINÁMICA",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = "Ingresá tu clave dinámica de 6 dígitos para confirmar la creación.",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            OutlinedTextField(
              value = dynamicKey.digits.joinToString("") { if (it > 0) it.toString() else "" },
              onValueChange = { value ->
                if (value.length <= 6 && value.all { it.isDigit() }) {
                  value.forEachIndexed { index, char ->
                    viewModel.onDynamicKeyDigitChanged(index, char.digitToInt())
                  }
                }
              },
              modifier = Modifier.fillMaxWidth(),
              placeholder = { Text("• • • • • •") },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
              shape = RoundedCornerShape(12.dp),
              colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
              ),
              textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                letterSpacing = MaterialTheme.typography.headlineSmall.letterSpacing
              )
            )
          }
        }
      }

      // Botón Confirmar
      Button(
        onClick = {
          if (viewModel.canConfirmRacha()) {
            // TODO: Implementar lógica de confirmación y navegación a home
            onConfirm()
          }
        },
        enabled = viewModel.canConfirmRacha(),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 24.dp)
          .height(56.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = MentaPrimary,
          disabledContainerColor = Color.LightGray
        ),
        shape = RoundedCornerShape(16.dp)
      ) {
        Text(
          text = "Confirmar y crear racha",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}

@Composable
private fun SummaryRow(
  label: String,
  value: String,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Text(
      text = value,
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.Bold
    )
  }
}

// Extension functions para display names
private fun getGoalDisplayName(goal: SavingsGoal): String = when (goal) {
  SavingsGoal.TRAVEL -> "Viaje"
  SavingsGoal.LAPTOP -> "Laptop"
  SavingsGoal.EMERGENCY -> "Emergencia"
  SavingsGoal.STUDIES -> "Estudios"
  SavingsGoal.BUSINESS -> "Negocio"
  SavingsGoal.OTHER -> "Otro"
}

private fun getFrequencyDisplayName(frequency: ContributionFrequency): String = when (frequency) {
  ContributionFrequency.DAILY -> "Diaria"
  ContributionFrequency.WEEKLY -> "Semanal"
  ContributionFrequency.BIWEEKLY -> "Quincenal"
  ContributionFrequency.MONTHLY -> "Mensual"
}
