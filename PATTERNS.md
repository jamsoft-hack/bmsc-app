# BMSC - Patrones de Programación

Este documento describe los patrones de programación establecidos en el proyecto basándose en las implementaciones existentes de `home` y `gamification`.

## Arquitectura de Features

Cada feature sigue una arquitectura **Clean + MVVM** con 3 capas:

```
feature_name/
├── data/
│   └── datasource/
│       └── FeatureMockDataSource.kt    # Datos simulados (temporal)
├── domain/
│   └── model/
│       └── FeatureData.kt              # Modelos de dominio
└── presentation/
    ├── FeatureViewModel.kt              # Lógica de presentación
    ├── screen/
    │   └── FeatureScreen.kt            # Pantalla principal
    └── composable/                      # Componentes específicos
        ├── ComponentOne.kt
        └── ComponentTwo.kt
```

### Ejemplos en el proyecto:
- [`home/`](shared/src/commonMain/kotlin/bo/com/bmsc/home)
- [`gamification/`](shared/src/commonMain/kotlin/bo/com/bmsc/gamification)

---

## ViewModel Pattern

### Estructura Estándar

```kotlin
package bo.com.bmsc.feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bo.com.bmsc.core.common.ResultState
import bo.com.bmsc.feature.data.datasource.FeatureMockDataSource
import bo.com.bmsc.feature.domain.model.FeatureData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FeatureViewModel : ViewModel() {

  // DataSource (temporal - será Repository + API)
  private val dataSource = FeatureMockDataSource()

  // State principal envuelto en ResultState
  private val _featureState = MutableStateFlow<ResultState<FeatureData>>(ResultState.Idle)
  val featureState: StateFlow<ResultState<FeatureData>> = _featureState.asStateFlow()

  // Estados UI adicionales
  private val _isVisible = MutableStateFlow(true)
  val isVisible: StateFlow<Boolean> = _isVisible.asStateFlow()

  init {
    loadData()
  }

  fun loadData() {
    viewModelScope.launch {
      _featureState.value = ResultState.Loading
      delay(500) // Simular latencia de red
      _featureState.value = ResultState.Success(dataSource.getData())
    }
  }

  fun onActionClick(actionId: String) {
    // Manejar acciones del usuario
  }

  fun toggleVisibility() {
    _isVisible.value = !_isVisible.value
  }
}
```

### Ejemplos reales:
- 📄 [HomeViewModel.kt](shared/src/commonMain/kotlin/bo/com/bmsc/home/presentation/HomeViewModel.kt)
- 📄 [GamificationViewModel.kt](shared/src/commonMain/kotlin/bo/com/bmsc/gamification/presentation/GamificationViewModel.kt)

### Registrar en Koin

**Archivo:** `core/di/modules/ViewModules.kt`

```kotlin
val viewModules = module {
  viewModelOf(::HomeViewModel)
  viewModelOf(::GamificationViewModel)
  viewModelOf(::FeatureViewModel)  // ← Agregar aquí
}
```

---

## 📱 Screen Pattern (Composable)

### Estructura Estándar

```kotlin
package bo.com.bmsc.feature.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bo.com.bmsc.core.common.ResultState
import bo.com.bmsc.core.navigation.NavigationHelper
import bo.com.bmsc.feature.domain.model.FeatureData
import bo.com.bmsc.feature.presentation.FeatureViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FeatureScreen(
  viewModel: FeatureViewModel = koinViewModel(),
  navigationHelper: NavigationHelper = koinInject(),
  onAction: () -> Unit = {},
) {
  // Collect states con collectAsStateWithLifecycle()
  val featureState by viewModel.featureState.collectAsStateWithLifecycle()
  val isVisible by viewModel.isVisible.collectAsStateWithLifecycle()

  Box(modifier = Modifier.fillMaxSize()) {
    // Pattern: when exhaustivo sobre ResultState
    when (val state = featureState) {
      is ResultState.Success -> {
        FeatureContent(
          data = state.data,
          isVisible = isVisible,
          onAction = { viewModel.onActionClick(it) },
          onToggle = { viewModel.toggleVisibility() },
        )
      }
      is ResultState.Loading, is ResultState.Idle -> {
        Box(
          modifier = Modifier.fillMaxSize(),
          contentAlignment = Alignment.Center
        ) {
          CircularProgressIndicator()
        }
      }
      is ResultState.Error -> {
        Box(
          modifier = Modifier.fillMaxSize(),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Error: ${state.exception.message}",
            color = MaterialTheme.colorScheme.error
          )
        }
      }
    }
  }
}

@Composable
private fun FeatureContent(
  data: FeatureData,
  isVisible: Boolean,
  onAction: (String) -> Unit,
  onToggle: () -> Unit,
) {
  // Implementación del contenido principal
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
  ) {
    // UI components
  }
}
```

### Ejemplos reales:
- 📄 [HomeScreen.kt](shared/src/commonMain/kotlin/bo/com/bmsc/home/presentation/screen/HomeScreen.kt)
- 📄 [GamificationScreen.kt](shared/src/commonMain/kotlin/bo/com/bmsc/gamification/presentation/screen/GamificationScreen.kt)

---

## 🎁 ResultState Wrapper

**Ubicación:** `core/common/ResultState.kt`

### Definición

```kotlin
sealed class ResultState<out T: Any> {
    data class Success<out T : Any>(val data: T) : ResultState<T>()
    data class Error(val exception: CustomException) : ResultState<Nothing>()
    object Loading : ResultState<Nothing>()
    object Idle : ResultState<Nothing>()
}
```

### Uso en ViewModel

```kotlin
// ❌ INCORRECTO - No envolver
private val _data = MutableStateFlow<FeatureData?>(null)

// ✅ CORRECTO - Siempre usar ResultState
private val _data = MutableStateFlow<ResultState<FeatureData>>(ResultState.Idle)
```

### Uso en Screen

```kotlin
when (val state = dataState) {
    is ResultState.Success -> {
        // Usar state.data
        MyContent(data = state.data)
    }
    is ResultState.Loading, is ResultState.Idle -> {
        LoadingIndicator()
    }
    is ResultState.Error -> {
        ErrorMessage(state.exception.message)
    }
}
```

---

## 📦 Domain Models

**Ubicación:** `feature/domain/model/`

### Pattern: Data Classes Simples

```kotlin
package bo.com.bmsc.feature.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class FeatureData(
  val id: String,
  val title: String,
  val subtitle: String?,
  val items: List<Item>,
  val metadata: Metadata?,
)

data class Item(
  val id: String,
  val name: String,
  val icon: ImageVector,
  val type: ItemType,
  val isActive: Boolean = false,
)

enum class ItemType {
  TYPE_A,
  TYPE_B,
  TYPE_C,
}

data class Metadata(
  val createdAt: String,
  val updatedAt: String,
)
```

### Convenciones:
- ✅ Usar `data class` para modelos
- ✅ `Enum` para tipos fijos
- ✅ Valores por defecto cuando sea lógico
- ✅ Agrupar modelos relacionados en el mismo archivo si son pequeños
- ✅ `ImageVector` para íconos

### Ejemplos reales:
- 📄 [HomeData.kt](shared/src/commonMain/kotlin/bo/com/bmsc/home/domain/model/HomeData.kt)
- 📄 [GamificationData.kt](shared/src/commonMain/kotlin/bo/com/bmsc/gamification/domain/model/GamificationData.kt)

---

## 🗄️ DataSource Pattern (Mock)

**Ubicación:** `feature/data/datasource/`

### Pattern: Mock Data Source

```kotlin
package bo.com.bmsc.feature.data.datasource

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import bo.com.bmsc.feature.domain.model.*

class FeatureMockDataSource {

  fun getData(): FeatureData {
    return FeatureData(
      id = "1",
      title = "Mi Feature",
      subtitle = "Descripción",
      items = getItems(),
      metadata = getMetadata(),
    )
  }

  private fun getItems(): List<Item> {
    return listOf(
      Item(
        id = "item1",
        name = "Item 1",
        icon = Icons.Filled.Star,
        type = ItemType.TYPE_A,
        isActive = true,
      ),
      Item(
        id = "item2",
        name = "Item 2",
        icon = Icons.Filled.Favorite,
        type = ItemType.TYPE_B,
        isActive = false,
      ),
    )
  }

  private fun getMetadata(): Metadata {
    return Metadata(
      createdAt = "2024-01-15",
      updatedAt = "2024-01-20",
    )
  }
}
```

### Convenciones:
- ✅ Nombre: `FeatureMockDataSource`
- ✅ Métodos públicos retornan modelos de dominio
- ✅ Métodos privados para construir sub-modelos
- ✅ Datos hardcodeados realistas

**Nota:** Los `MockDataSource` son temporales. Serán reemplazados por:
- **Repository** (interfaz + implementación)
- **RemoteDataSource** (llamadas API con Ktor)
- **LocalDataSource** (Room database)

### Ejemplos reales:
- 📄 [HomeMockDataSource.kt](shared/src/commonMain/kotlin/bo/com/bmsc/home/data/datasource/HomeMockDataSource.kt)
- 📄 [GamificationMockDataSource.kt](shared/src/commonMain/kotlin/bo/com/bmsc/gamification/data/datasource/GamificationMockDataSource.kt)

---

## 🎨 Composables Reutilizables

**Ubicación:** `core/composable/tipo/` o `feature/presentation/composable/`

### Pattern: Composable Público + Privados

```kotlin
package bo.com.bmsc.feature.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bo.com.bmsc.feature.domain.model.Item

/**
 * Componente público - API clara y parametrizada
 */
@Composable
fun ItemsSection(
    items: List<Item>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.forEach { item ->
            ItemCard(
                item = item,
                onClick = { onItemClick(item.id) }
            )
        }
    }
}

/**
 * Componente privado - detalles de implementación
 */
@Composable
private fun ItemCard(
    item: Item,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.name,
                tint = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
```

### Convenciones:
- ✅ Composables públicos: bien documentados, parámetros claros
- ✅ Composables privados: `private`, detalles internos
- ✅ `Modifier` siempre como último parámetro con default
- ✅ Callbacks con nombres descriptivos: `onItemClick`, `onToggle`
- ✅ Usar Theme colors: `MaterialTheme.colorScheme.primary`

### Ejemplos reales:
- 📄 [QuickActionsSection.kt](shared/src/commonMain/kotlin/bo/com/bmsc/home/presentation/composable/QuickActionsSection.kt)
- 📄 [BadgesTabContent.kt](shared/src/commonMain/kotlin/bo/com/bmsc/gamification/presentation/composable/BadgesTabContent.kt)
- 📄 [BaseElevatedCard.kt](shared/src/commonMain/kotlin/bo/com/bmsc/core/composable/card/BaseElevatedCard.kt)

---

## 🧭 Navegación

### Definir Rutas

**Archivo:** `Route.kt`

```kotlin
interface Route {
  @Serializable
  data object MyFeature : Route
  
  @Serializable
  data class FeatureDetail(val id: String) : Route
  
  @Serializable
  data class FeatureWithParams(
    val id: String,
    val showDialog: Boolean = false
  ) : Route
}
```

### Navegar desde cualquier Composable

```kotlin
@Composable
fun MyScreen(
  navigationHelper: NavigationHelper = koinInject()
) {
  Column {
    Button(
      onClick = { navigationHelper.navigateTo(Route.MyFeature) }
    ) {
      Text("Ir a Feature")
    }
    
    Button(
      onClick = { navigationHelper.navigateTo(Route.FeatureDetail("123")) }
    ) {
      Text("Ver detalle")
    }
    
    Button(
      onClick = { navigationHelper.navigateBack() }
    ) {
      Text("Volver")
    }
  }
}
```

### NavigationHelper

**Ubicación:** `core/navigation/NavigationHelper.kt`

**Métodos:**
- `navigateTo(route: Route)` - Navegar a una ruta
- `navigateBack()` - Regresar
- `replaceWith(route: Route)` - Reemplazar (limpia back stack)

---

## 🎨 Tema y Colores

### Usar Colores del Tema

```kotlin
import bo.com.bmsc.app.theme.MentaPrimary
import bo.com.bmsc.app.theme.MentaOnSurface
import bo.com.bmsc.app.theme.MentaSurface
import bo.com.bmsc.app.theme.PiggyGreen
import bo.com.bmsc.app.theme.StreakOrange

@Composable
fun MyComponent() {
  Box(
    modifier = Modifier
      .background(MentaPrimary)
      .padding(16.dp)
  ) {
    Text(
      text = "Texto principal",
      color = MentaOnSurface,
      style = MaterialTheme.typography.bodyLarge
    )
  }
}
```

### Colores Principales

**Ubicación:** `app/theme/Color.kt` y `app/theme/color/`

```kotlin
// Colores del tema Menta
val MentaPrimary = Color(0xFF4CAF50)
val MentaOnPrimary = Color(0xFFFFFFFF)
val MentaSurface = Color(0xFFFAFAFA)
val MentaOnSurface = Color(0xFF1C1B1F)

// Colores custom
val PiggyGreen = Color(0xFF81C784)
val PiggyGreenContainer = Color(0xFFE8F5E9)
val StreakOrange = Color(0xFFFF9800)
val StreakOrangeContainer = Color(0xFFFFF3E0)
```

---

## 📏 Convenciones de Código

### Nombrado

| Elemento | Convención | Ejemplo |
|----------|-----------|---------|
| Feature | Singular | `home`, `gamification` (no `homes`) |
| ViewModel | `FeatureViewModel` | `HomeViewModel` |
| Screen | `FeatureScreen` | `GamificationScreen` |
| DataSource | `FeatureMockDataSource` | `HomeMockDataSource` |
| Model principal | `FeatureData` | `HomeData`, `GamificationData` |
| StateFlow privado | `_nombreState` | `_homeState`, `_isVisible` |
| StateFlow público | `nombreState` | `homeState`, `isVisible` |

### Paquetes

```
bo.com.bmsc.
├── core/                    # Código compartido
│   ├── common/             # Utilidades comunes (ResultState, etc.)
│   ├── composable/         # UI components reutilizables
│   ├── data/               # HTTP, DB factories
│   ├── di/                 # Dependency Injection
│   ├── domain/             # Excepciones, modelos core
│   ├── helper/             # Helpers (expect/actual)
│   └── navigation/         # NavigationHelper
├── app/
│   └── theme/              # Theme, colores, tipografía
└── feature/                # Features (home, gamification, etc.)
    ├── data/
    ├── domain/
    └── presentation/
```

### Imports

```kotlin
// ✅ CORRECTO - Orden:
// 1. Android/Compose
import androidx.compose.foundation.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.*

// 2. Recursos generados
import bmsc.shared.generated.resources.*

// 3. Tema
import bo.com.bmsc.app.theme.*

// 4. Core
import bo.com.bmsc.core.*

// 5. Feature actual
import bo.com.bmsc.feature.domain.model.*

// 6. Koin
import org.koin.compose.*

// 7. Otros
import kotlinx.coroutines.*
```

---

## 🚀 Checklist para Crear un Nuevo Feature

### 1. Crear estructura de carpetas
```
feature_name/
├── data/datasource/
├── domain/model/
└── presentation/
    ├── screen/
    └── composable/
```

### 2. Domain Models (`domain/model/FeatureData.kt`)
```kotlin
data class FeatureData(...)
```

### 3. DataSource (`data/datasource/FeatureMockDataSource.kt`)
```kotlin
class FeatureMockDataSource {
  fun getData(): FeatureData = ...
}
```

### 4. ViewModel (`presentation/FeatureViewModel.kt`)
```kotlin
class FeatureViewModel : ViewModel() {
  private val _state = MutableStateFlow<ResultState<FeatureData>>(ResultState.Idle)
  val state = _state.asStateFlow()
  
  init { loadData() }
  fun loadData() { ... }
}
```

### 5. Registrar ViewModel (`core/di/modules/ViewModules.kt`)
```kotlin
val viewModules = module {
  viewModelOf(::FeatureViewModel)
}
```

### 6. Screen (`presentation/screen/FeatureScreen.kt`)
```kotlin
@Composable
fun FeatureScreen(
  viewModel: FeatureViewModel = koinViewModel(),
) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  
  when (val s = state) {
    is ResultState.Success -> Content(s.data)
    is ResultState.Loading -> Loading()
    is ResultState.Error -> Error()
  }
}
```

### 7. Añadir ruta (`Route.kt`)
```kotlin
@Serializable
data object FeatureName : Route
```

### 8. Navegación
```kotlin
navigationHelper.navigateTo(Route.FeatureName)
```

---

## 📚 Recursos de Referencia

### Archivos Principales

- [CLAUDE.md](CLAUDE.md) - Guía para Claude
- [WORKFLOW.md](WORKFLOW.md) - Guía de trabajo del proyecto
- [libs.versions.toml](gradle/libs.versions.toml) - Catálogo de versiones

### Implementaciones de Referencia

**Home Feature:**
- [HomeViewModel.kt](shared/src/commonMain/kotlin/bo/com/bmsc/home/presentation/HomeViewModel.kt)
- [HomeScreen.kt](shared/src/commonMain/kotlin/bo/com/bmsc/home/presentation/screen/HomeScreen.kt)
- [HomeData.kt](shared/src/commonMain/kotlin/bo/com/bmsc/home/domain/model/HomeData.kt)
- [HomeMockDataSource.kt](shared/src/commonMain/kotlin/bo/com/bmsc/home/data/datasource/HomeMockDataSource.kt)

**Gamification Feature:**
- [GamificationViewModel.kt](shared/src/commonMain/kotlin/bo/com/bmsc/gamification/presentation/GamificationViewModel.kt)
- [GamificationScreen.kt](shared/src/commonMain/kotlin/bo/com/bmsc/gamification/presentation/screen/GamificationScreen.kt)
- [GamificationData.kt](shared/src/commonMain/kotlin/bo/com/bmsc/gamification/domain/model/GamificationData.kt)

**Core:**
- [ResultState.kt](shared/src/commonMain/kotlin/bo/com/bmsc/core/common/ResultState.kt)
- [NavigationHelper.kt](shared/src/commonMain/kotlin/bo/com/bmsc/core/navigation/NavigationHelper.kt)
- [ViewModules.kt](shared/src/commonMain/kotlin/bo/com/bmsc/core/di/modules/ViewModules.kt)

---

## Tips y Buenas Prácticas

### ViewModel
- Siempre usar `ResultState` para estados asíncronos
- `viewModelScope.launch` para coroutines
- `StateFlow` privado (`_state`) + público (`state`)
- `asStateFlow()` para exponer el flow

### Screen/Composables
- `collectAsStateWithLifecycle()` para observar flows
- `by` delegate para estados
- `Modifier` como último parámetro
- Parámetros opcionales con defaults
- Composables internos marcados `private`

### Navegación
- Usar `NavigationHelper` inyectado
- Rutas en `Route.kt` con `@Serializable`
- Type-safe navigation

### Colores y Tema
- Usar colores del tema (`Menta*`, `Piggy*`, etc.)
- Evitar hardcodear colores
- `MaterialTheme.typography` para texto
- `MaterialTheme.shapes` para formas

### DI (Koin)
- `koinViewModel()` para ViewModels
- `koinInject()` para otros inyectables
- Registrar en módulos apropiados

---

**Última actualización:** Mayo 2026
