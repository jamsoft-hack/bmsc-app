package bo.com.bmsc.racha.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bo.com.bmsc.racha.data.datasource.RachaMockDataSource
import bo.com.bmsc.racha.domain.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateRachaViewModel : ViewModel() {

  private val dataSource = RachaMockDataSource()

  // Estado de configuración actual
  private val _rachaConfig = MutableStateFlow(RachaConfig())
  val rachaConfig: StateFlow<RachaConfig> = _rachaConfig.asStateFlow()

  // Paso actual del flujo
  private val _currentStep = MutableStateFlow(1)
  val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

  // Lista de participantes disponibles
  private val _availableParticipants = MutableStateFlow<List<Participant>>(emptyList())
  val availableParticipants: StateFlow<List<Participant>> = _availableParticipants.asStateFlow()

  // Método de invitación seleccionado
  private val _selectedInviteMethod = MutableStateFlow(InviteMethod.CONTACTS)
  val selectedInviteMethod: StateFlow<InviteMethod> = _selectedInviteMethod.asStateFlow()

  // Términos aceptados
  private val _termsAccepted = MutableStateFlow(false)
  val termsAccepted: StateFlow<Boolean> = _termsAccepted.asStateFlow()

  // Clave dinámica
  private val _dynamicKey = MutableStateFlow(DynamicKey())
  val dynamicKey: StateFlow<DynamicKey> = _dynamicKey.asStateFlow()

  init {
    loadParticipants()
  }

  private fun loadParticipants() {
    viewModelScope.launch {
      _availableParticipants.value = dataSource.getMockParticipants()
    }
  }

  // ========== PASO 1: INVITAR GENTE ==========

  fun onInviteMethodSelected(method: InviteMethod) {
    _selectedInviteMethod.value = method
  }

  fun  onParticipantToggle(participantId: String) {
    _availableParticipants.update { participants ->
      participants.map { participant ->
        if (participant.id == participantId) {
          participant.copy(isSelected = !participant.isSelected)
        } else {
          participant
        }
      }
    }
  }

  fun getSelectedParticipantsCount(): Int {
    return _availableParticipants.value.count { it.isSelected }
  }

  fun canContinueFromStep1(): Boolean {
    return getSelectedParticipantsCount() in 1..6
  }

  // ========== PASO 2: CONFIGURAR TEMPORADA ==========

  fun onDurationSelected(duration: RachaDuration) {
    _rachaConfig.update { it.copy(duration = duration) }
    updateCloseDate()
  }

  fun onFrequencySelected(frequency: ContributionFrequency) {
    _rachaConfig.update { it.copy(frequency = frequency) }
  }

  fun onMinimumAmountChanged(amount: Int) {
    _rachaConfig.update { it.copy(minimumAmount = amount) }
  }

  fun onGoalSelected(goal: SavingsGoal?) {
    _rachaConfig.update { it.copy(goal = goal) }
  }

  fun canContinueFromStep2(): Boolean {
    return _rachaConfig.value.minimumAmount > 0
  }

  private fun updateCloseDate() {
    val closeDate = dataSource.generateCloseDate(_rachaConfig.value.duration.months)
    _rachaConfig.update { it.copy(closeDate = closeDate) }
  }

  // ========== PASO 3: CONFIRMAR RACHA ==========

  fun onSeasonNameChanged(name: String) {
    _rachaConfig.update { it.copy(seasonName = name) }
  }

  fun onTermsAcceptedToggle() {
    _termsAccepted.value = !_termsAccepted.value
  }

  fun onDynamicKeyDigitChanged(index: Int, digit: Int) {
    if (index in 0..5 && digit in 0..9) {
      val newDigits = _dynamicKey.value.digits.toMutableList()
      newDigits[index] = digit
      _dynamicKey.value = DynamicKey(newDigits)
    }
  }

  fun canConfirmRacha(): Boolean {
    return _termsAccepted.value &&
           _dynamicKey.value.digits.all { it > 0 } &&
           _rachaConfig.value.seasonName.isNotBlank()
  }

  fun getRachaSummary(): RachaSummary {
    val config = _rachaConfig.value
    val selectedCount = getSelectedParticipantsCount()
    
    return RachaSummary(
      seasonName = config.seasonName.ifBlank { "Mi Racha" },
      goalLabel = config.goal?.let { "${it.label} · ${config.duration.label}" } 
                  ?: config.duration.label,
      duration = config.duration.label,
      participantCount = selectedCount + 1, // +1 para el usuario actual
      frequency = "${config.frequency.label} · ${config.frequency.details}",
      minimumAmount = config.minimumAmount,
      closeDate = config.closeDate,
      fundsLocked = config.fundsLocked
    )
  }

  // ========== NAVEGACIÓN ==========

  fun goToNextStep() {
    if (_currentStep.value < 3) {
      _currentStep.value += 1
    }
  }

  fun goToPreviousStep() {
    if (_currentStep.value > 1) {
      _currentStep.value -= 1
    }
  }

  fun confirmAndCreateRacha() {
    viewModelScope.launch {
      val selectedParticipants = _availableParticipants.value.filter { it.isSelected }
      _rachaConfig.update { it.copy(participants = selectedParticipants) }
      
      // Aquí iría la lógica para crear la racha en el backend
      // Por ahora solo simulamos
    }
  }
}
