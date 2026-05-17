package bo.com.bmsc.streak.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bo.com.bmsc.core.domain.Result
import bo.com.bmsc.streak.domain.model.Contact
import bo.com.bmsc.streak.domain.model.CreateStreakRequest
import bo.com.bmsc.streak.domain.repository.CreateStreakRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class InviteMethod { CONTACTS, LINK, QR }

data class CreateStreakUiState(
    val contacts: List<Contact> = emptyList(),
    val selectedIds: Set<String> = emptySet(),
    val inviteMethod: InviteMethod = InviteMethod.CONTACTS,
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,
    val error: String? = null,
) {
    val selectedCount: Int get() = selectedIds.size
    val canContinue: Boolean get() = selectedCount in 1..6 && !isSubmitting
    val buttonLabel: String
        get() = if (selectedCount == 0) "Continuar"
                else "Continuar · $selectedCount ${if (selectedCount == 1) "persona" else "personas"}"
}

sealed interface CreateStreakEvent {
    data object NavigateBack : CreateStreakEvent
    data class NavigateToNextStep(val participantIds: List<String>) : CreateStreakEvent
}

class CreateStreakViewModel(
    private val repository: CreateStreakRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateStreakUiState())
    val uiState: StateFlow<CreateStreakUiState> = _uiState.asStateFlow()

    private val _events = Channel<CreateStreakEvent>()
    val events = _events.receiveAsFlow()

    init {
        loadContacts()
    }

    private fun loadContacts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = repository.getContacts()) {
                is Result.Success -> _uiState.update {
                    it.copy(contacts = result.data, isLoading = false)
                }
                is Result.Error -> _uiState.update {
                    it.copy(isLoading = false, error = "No se pudieron cargar los contactos.")
                }
            }
        }
    }

    fun onContactToggle(contactId: String) {
        _uiState.update { state ->
            val newIds = if (contactId in state.selectedIds) {
                state.selectedIds - contactId
            } else if (state.selectedIds.size < 6) {
                state.selectedIds + contactId
            } else {
                state.selectedIds
            }
            state.copy(selectedIds = newIds)
        }
    }

    fun onInviteMethodChange(method: InviteMethod) {
        _uiState.update { it.copy(inviteMethod = method) }
    }

    fun onContinueClick() {
        val state = _uiState.value
        if (!state.canContinue) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSubmitting = true, error = null) }
            val result = repository.createStreak(
                CreateStreakRequest(participantIds = state.selectedIds.toList())
            )
            when (result) {
                is Result.Success -> {
                    _uiState.update { it.copy(isSubmitting = false) }
                    _events.send(CreateStreakEvent.NavigateToNextStep(state.selectedIds.toList()))
                }
                is Result.Error -> _uiState.update {
                    it.copy(isSubmitting = false, error = "Error al crear la racha. Intentá de nuevo.")
                }
            }
        }
    }

    fun onBackClick() {
        viewModelScope.launch { _events.send(CreateStreakEvent.NavigateBack) }
    }
}
