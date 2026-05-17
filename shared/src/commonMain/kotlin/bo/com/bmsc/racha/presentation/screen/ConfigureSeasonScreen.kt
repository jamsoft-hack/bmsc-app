package bo.com.bmsc.racha.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bo.com.bmsc.app.theme.MentaPrimary
import bo.com.bmsc.app.theme.MentaSurface
import bo.com.bmsc.racha.domain.model.ContributionFrequency
import bo.com.bmsc.racha.domain.model.RachaDuration
import bo.com.bmsc.racha.domain.model.SavingsGoal
import bo.com.bmsc.racha.presentation.CreateRachaViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigureSeasonScreen(
  viewModel: CreateRachaViewModel = koinViewModel(),
  onBack: () -> Unit = {},
  onContinue: () -> Unit = {},
) {
  val config by viewModel.rachaConfig.collectAsStateWithLifecycle()

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            "Crear racha · 2 de 3",
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
        verticalArrangement = Arrangement.spacedBy(24.dp)
      ) {
        item {
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
              text = "Configurá tu temporada",
              style = MaterialTheme.typography.headlineMedium,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "Elegí la duración, frecuencia y monto del ahorro compartido.",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        // Duración
        item {
          Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
              text = "DURACIÓN",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              DurationChip(
                label = "3 meses",
                isSelected = config.duration == RachaDuration.THREE_MONTHS,
                onClick = { viewModel.onDurationSelected(RachaDuration.THREE_MONTHS) },
                modifier = Modifier.weight(1f)
              )
              DurationChip(
                label = "6 meses",
                isSelected = config.duration == RachaDuration.SIX_MONTHS,
                onClick = { viewModel.onDurationSelected(RachaDuration.SIX_MONTHS) },
                modifier = Modifier.weight(1f)
              )
              DurationChip(
                label = "12 meses",
                isSelected = config.duration == RachaDuration.TWELVE_MONTHS,
                onClick = { viewModel.onDurationSelected(RachaDuration.TWELVE_MONTHS) },
                modifier = Modifier.weight(1f)
              )
            }
          }
        }

        // Frecuencia
        item {
          Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
              text = "FRECUENCIA DE AHORRO",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              FrequencyChip(
                label = "Diaria",
                isSelected = config.frequency == ContributionFrequency.DAILY,
                onClick = { viewModel.onFrequencySelected(ContributionFrequency.DAILY) },
                modifier = Modifier.weight(1f)
              )
              FrequencyChip(
                label = "Semanal",
                isSelected = config.frequency == ContributionFrequency.WEEKLY,
                onClick = { viewModel.onFrequencySelected(ContributionFrequency.WEEKLY) },
                modifier = Modifier.weight(1f)
              )
            }
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              FrequencyChip(
                label = "Quincenal",
                isSelected = config.frequency == ContributionFrequency.BIWEEKLY,
                onClick = { viewModel.onFrequencySelected(ContributionFrequency.BIWEEKLY) },
                modifier = Modifier.weight(1f)
              )
              FrequencyChip(
                label = "Mensual",
                isSelected = config.frequency == ContributionFrequency.MONTHLY,
                onClick = { viewModel.onFrequencySelected(ContributionFrequency.MONTHLY) },
                modifier = Modifier.weight(1f)
              )
            }
          }
        }

        // Monto mínimo
        item {
          Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
              text = "MONTO MÍNIMO",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            OutlinedTextField(
              value = if (config.minimumAmount > 0) config.minimumAmount.toString() else "",
              onValueChange = { value ->
                value.toIntOrNull()?.let { viewModel.onMinimumAmountChanged(it) }
              },
              modifier = Modifier.fillMaxWidth(),
              placeholder = { Text("Ingresá el monto mínimo en Bs") },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
              shape = RoundedCornerShape(12.dp),
              colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
              )
            )
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              listOf(10, 20, 30, 50).forEach { amount ->
                AmountQuickSelectChip(
                  amount = amount,
                  isSelected = config.minimumAmount == amount,
                  onClick = { viewModel.onMinimumAmountChanged(amount) },
                  modifier = Modifier.weight(1f)
                )
              }
            }
          }
        }

        // Meta opcional
        item {
          Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
              text = "META (OPCIONAL)",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = "¿Para qué están ahorrando?",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                GoalChip(
                  label = "Viaje",
                  goal = SavingsGoal.TRAVEL,
                  isSelected = config.goal == SavingsGoal.TRAVEL,
                  onClick = { viewModel.onGoalSelected(SavingsGoal.TRAVEL) },
                  modifier = Modifier.weight(1f)
                )
                GoalChip(
                  label = "Laptop",
                  goal = SavingsGoal.LAPTOP,
                  isSelected = config.goal == SavingsGoal.LAPTOP,
                  onClick = { viewModel.onGoalSelected(SavingsGoal.LAPTOP) },
                  modifier = Modifier.weight(1f)
                )
                GoalChip(
                  label = "Emergencia",
                  goal = SavingsGoal.EMERGENCY,
                  isSelected = config.goal == SavingsGoal.EMERGENCY,
                  onClick = { viewModel.onGoalSelected(SavingsGoal.EMERGENCY) },
                  modifier = Modifier.weight(1f)
                )
              }
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
              ) {
                GoalChip(
                  label = "Estudios",
                  goal = SavingsGoal.STUDIES,
                  isSelected = config.goal == SavingsGoal.STUDIES,
                  onClick = { viewModel.onGoalSelected(SavingsGoal.STUDIES) },
                  modifier = Modifier.weight(1f)
                )
                GoalChip(
                  label = "Negocio",
                  goal = SavingsGoal.BUSINESS,
                  isSelected = config.goal == SavingsGoal.BUSINESS,
                  onClick = { viewModel.onGoalSelected(SavingsGoal.BUSINESS) },
                  modifier = Modifier.weight(1f)
                )
                GoalChip(
                  label = "Otro",
                  goal = SavingsGoal.OTHER,
                  isSelected = config.goal == SavingsGoal.OTHER,
                  onClick = { viewModel.onGoalSelected(SavingsGoal.OTHER) },
                  modifier = Modifier.weight(1f)
                )
              }
            }
          }
        }
      }

      // Botón Continuar
      Button(
        onClick = {
          if (viewModel.canContinueFromStep2()) {
            viewModel.goToNextStep()
            onContinue()
          }
        },
        enabled = viewModel.canContinueFromStep2(),
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 24.dp)
          .height(56.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = MentaPrimary
        ),
        shape = RoundedCornerShape(16.dp)
      ) {
        Text(
          text = "Continuar",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
      }
    }
  }
}

@Composable
private fun DurationChip(
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier
      .height(48.dp)
      .clickable(onClick = onClick),
    color = if (isSelected) MentaPrimary else Color.White,
    shape = RoundedCornerShape(12.dp),
    border = if (!isSelected) BorderStroke(1.dp, Color.LightGray) else null
  ) {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = label,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
      )
    }
  }
}

@Composable
private fun FrequencyChip(
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier
      .height(48.dp)
      .clickable(onClick = onClick),
    color = if (isSelected) MentaPrimary else Color.White,
    shape = RoundedCornerShape(12.dp),
    border = if (!isSelected) BorderStroke(1.dp, Color.LightGray) else null
  ) {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = label,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
      )
    }
  }
}

@Composable
private fun AmountQuickSelectChip(
  amount: Int,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier
      .height(40.dp)
      .clickable(onClick = onClick),
    color = if (isSelected) MentaPrimary else Color.White,
    shape = RoundedCornerShape(10.dp),
    border = if (!isSelected) BorderStroke(1.dp, Color.LightGray) else null
  ) {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "Bs $amount",
        style = MaterialTheme.typography.bodySmall,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
      )
    }
  }
}

@Composable
private fun GoalChip(
  label: String,
  goal: SavingsGoal,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier
      .height(44.dp)
      .clickable(onClick = onClick),
    color = if (isSelected) MentaPrimary else Color.White,
    shape = RoundedCornerShape(10.dp),
    border = if (!isSelected) BorderStroke(1.dp, Color.LightGray) else null
  ) {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = label,
        style = MaterialTheme.typography.bodySmall,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
      )
    }
  }
}
