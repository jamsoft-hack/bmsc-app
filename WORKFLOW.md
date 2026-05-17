# BMSC - Guía Rápida

## Añadir Librerías con Version Catalogs

**Archivo:** `gradle/libs.versions.toml`

```toml
[versions]
nueva-lib = "1.0.0"

[libraries]
nombre-alias = { module = "grupo:artefacto", version.ref = "nueva-lib" }
```

**Uso en build.gradle.kts:**
```kotlin
commonMain.dependencies {
    implementation(libs.nombre.alias)  // Puntos, no guiones
}
```

**Para bundles:**
```toml
[bundles]
mi-bundle = ["lib1", "lib2"]
```
```kotlin
implementation(libs.bundles.mi.bundle)
```

---

## Stack Tecnológico

| Librería | Versión | Uso |
|----------|---------|-----|
| Kotlin Multiplatform | 2.3.21 | Lenguaje |
| Compose Multiplatform | 1.11.0 | UI |
| Material3 | 1.11.0-alpha07 | Diseño |
| Koin | 4.1.0-Beta11 | Dependency Injection |
| Room | 2.7.2 | Base de datos |
| Ktor | 3.0.1 | HTTP Client |
| Coil | 3.0.3-3.0.4 | Carga de imágenes |
| Navigation Compose | 2.9.0-beta03 | Navegación |
| Kotlinx Serialization | 1.11.0 | Serialización JSON |

---

## Comandos Útiles

```bash
# Build Android
./gradlew :androidApp:assembleDebug

# Tests Android
./gradlew :shared:testAndroidHostTest

# Tests iOS
./gradlew :shared:iosSimulatorArm64Test

# Generar recursos SVG
./gradlew :shared:generateFileResources
```

---

## Trabajar con Recursos SVG

### 1. Añadir SVGs:
```
shared/src/commonMain/resources/
├── vectors/   # ← Colocar SVGs aquí
└── icons/     # ← O aquí
```

### 2. Generar código Kotlin:
```bash
./gradlew :shared:generateFileResources
```

### 3. Usar en código:
```kotlin
import bo.com.bmsc.assets.BMSCVectors
import bo.com.bmsc.assets.BMSCIcons

Icon(
    painter = BMSCVectors.MiIcono,
    contentDescription = null
)
```

---

## Más Información

- **Arquitectura del proyecto:** Ver [CLAUDE.md](./CLAUDE.md)
- **Patrones de programación:** Ver [PATTERNS.md](./PATTERNS.md) 
- **Documentación KMP:** Ver [README.md](./README.md)
- **Catálogo de versiones:** [gradle/libs.versions.toml](./gradle/libs.versions.toml)
