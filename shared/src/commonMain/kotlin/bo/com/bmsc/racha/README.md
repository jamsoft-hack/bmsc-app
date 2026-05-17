# Racha (Mi Chanchito) - Feature Documentation

## Descripción
Módulo de ahorro colaborativo "Mi Chanchito" que permite a grupos de usuarios crear "rachas" de ahorro con fondos bloqueados hasta una fecha específica.

## Estructura del Módulo

```
racha/
├── data/
│   └── datasource/
│       └── RachaMockDataSource.kt      # Mock de participantes y datos
├── domain/
│   └── model/
│       └── RachaModels.kt              # Modelos de dominio (config, enums, etc.)
└── presentation/
    ├── CreateRachaViewModel.kt         # ViewModel principal del flujo
    └── screen/
        ├── RachaOnboardingScreen.kt    # Pantalla inicial (onboarding)
        ├── InvitePeopleScreen.kt       # Paso 1/3: Invitar participantes
        ├── ConfigureSeasonScreen.kt    # Paso 2/3: Configurar temporada
        └── ConfirmRachaScreen.kt       # Paso 3/3: Confirmar y crear
```

## Flujo de Usuario

### 1. Onboarding
- **Ruta:** `Route.RachaOnboarding`
- **Pantalla:** `RachaOnboardingScreen`
- **Función:** Introducción al concepto de racha con ilustración de chanchito
- **Acciones:**
  - Skip: Volver atrás
  - Continuar: Ir a paso 1/3

### 2. Paso 1/3: Invitar Gente
- **Ruta:** `Route.RachaInvitePeople`
- **Pantalla:** `InvitePeopleScreen`
- **Función:** Seleccionar participantes (1-6 personas)
- **Opciones:** 
  - Contactos (lista con checkboxes)
  - Link de invitación
  - Código QR
- **Validación:** Requiere al menos 1 participante seleccionado
- **Acciones:**
  - Volver: Retroceder un paso
  - Continuar: Ir a paso 2/3

### 3. Paso 2/3: Configurar Temporada
- **Ruta:** `Route.RachaConfigureSeason`
- **Pantalla:** `ConfigureSeasonScreen`
- **Configuración:**
  - **Duración:** 3, 6 o 12 meses
  - **Frecuencia:** Diaria, Semanal, Quincenal, Mensual
  - **Monto mínimo:** Input personalizado + chips rápidos (Bs 10, 20, 30, 50)
  - **Meta (opcional):** Viaje, Laptop, Emergencia, Estudios, Negocio, Otro
- **Validación:** Requiere duración, frecuencia y monto mínimo
- **Acciones:**
  - Volver: Retroceder un paso
  - Continuar: Ir a paso 3/3

### 4. Paso 3/3: Confirmar Racha
- **Ruta:** `Route.RachaConfirm`
- **Pantalla:** `ConfirmRachaScreen`
- **Contenido:**
  - Resumen completo de configuración
  - Input de nombre de temporada (ej: "Los Ahorradores")
  - Detalle de participantes, frecuencia, monto, fecha de cierre
  - Aviso de fondos bloqueados
  - Checkbox de términos y condiciones
  - Input de clave dinámica (6 dígitos)
- **Validación:** Requiere nombre, términos aceptados y clave de 6 dígitos
- **Acciones:**
  - Volver: Retroceder un paso
  - Confirmar: Crear racha y volver a home

## Modelos de Dominio

### RachaConfig
Configuración principal de la racha:
```kotlin
data class RachaConfig(
  val seasonName: String? = null,
  val duration: RachaDuration? = null,
  val frequency: ContributionFrequency? = null,
  val minimumAmount: Double? = null,
  val savingsGoal: SavingsGoal? = null,
  val closeDate: Date? = null,
)
```

### Enums
- **RachaDuration:** THREE_MONTHS, SIX_MONTHS, TWELVE_MONTHS
- **ContributionFrequency:** DAILY, WEEKLY, BIWEEKLY, MONTHLY  
- **SavingsGoal:** TRAVEL, LAPTOP, EMERGENCY, STUDIES, BUSINESS, OTHER
- **InviteMethod:** CONTACTS, LINK, QR

### Participant
```kotlin
data class Participant(
  val id: String,
  val name: String,
  val initials: String,
  val avatarColor: String,
  var isSelected: Boolean = false,
)
```

## State Management

El `CreateRachaViewModel` maneja todo el estado del flujo:

### StateFlows principales
- `rachaConfig`: Configuración actual de la racha
- `currentStep`: Paso actual (1, 2 o 3)
- `availableParticipants`: Lista de participantes disponibles
- `selectedInviteMethod`: Método de invitación seleccionado
- `termsAccepted`: Términos aceptados (boolean)
- `dynamicKey`: Clave dinámica de 6 dígitos

### Funciones de validación
- `canContinueFromStep1()`: Al menos 1 participante seleccionado
- `canContinueFromStep2()`: Duración, frecuencia y monto definidos
- `canConfirmRacha()`: Nombre, términos, y clave de 6 dígitos

### Funciones de navegación
- `goToNextStep()`: Avanzar al siguiente paso
- `goToPreviousStep()`: Retroceder al paso anterior

## Registro en DI

El ViewModel está registrado en `ViewModules.kt`:
```kotlin
viewModelOf(::CreateRachaViewModel)
```

## Rutas de Navegación

Definidas en `Route.kt`:
```kotlin
@Serializable data object RachaOnboarding : Route
@Serializable data object RachaInvitePeople : Route
@Serializable data object RachaConfigureSeason : Route
@Serializable data object RachaConfirm : Route
```

Registradas en `App.kt` con navegación secuencial.

## Temas y Diseño

- **Color principal:** `MentaPrimary` (verde menta)
- **Superficie:** `MentaSurface` (gris claro)
- **Chips seleccionados:** Fondo MentaPrimary con texto blanco
- **Chips no seleccionados:** Fondo blanco con borde LightGray
- **Botones principales:** 56dp de altura, esquinas redondeadas 16dp

## Mock Data

### Participantes predefinidos
1. Ana Vargas (AV) - #FF6B9D
2. Carlos Rojas (CR) - #4A90E2
3. Lucía Mamani (LM) - #F39C12
4. Diego Sotomayor (DS) - #9B59B6
5. Sofía López (SL) - #16A085
6. Mateo Quiroz (MQ) - #E67E22

## TODO / Mejoras Futuras
- [ ] Integrar con backend real (crear racha en servidor)
- [ ] Mostrar modal de éxito al confirmar racha
- [ ] Implementar flujo de invitación por link/QR
- [ ] Agregar validación de clave dinámica con backend
- [ ] Persistir racha en base de datos local (Room)
- [ ] Agregar pantallas de detalle de racha activa
- [ ] Implementar notificaciones de recordatorio de ahorro
