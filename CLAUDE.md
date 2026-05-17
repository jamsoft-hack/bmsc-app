
# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

BMSC is a Kotlin Multiplatform project targeting Android and iOS using Compose Multiplatform. The shared business logic, UI components, and data layer reside in the `shared` module, while platform-specific entry points exist in `androidApp` and `iosApp`.

## Development Commands

### Building
- Android debug build: `./gradlew :androidApp:assembleDebug`
- iOS: Open the `iosApp` directory in Xcode and run from there

### Testing
- Android tests: `./gradlew :shared:testAndroidHostTest`
- iOS tests: `./gradlew :shared:iosSimulatorArm64Test`

### Generating SVG Resources
After adding SVG files to `src/commonMain/resources/vectors/` or `icons/`:
```bash
./gradlew :shared:generateFileResources
```
This generates Kotlin Painters in `build/generated/source/svg2compose/kotlin/` via the `svg-to-compose` plugin.

## Architecture

### Module Structure
- **shared/**: Multiplatform shared code
  - `commonMain/`: Platform-agnostic Kotlin code
  - `androidMain/`: Android-specific implementations (OkHttp, Android DI module)
  - `iosMain/`: iOS-specific implementations (Darwin HTTP engine, iOS DI module)
- **androidApp/**: Android application entry point
- **iosApp/**: iOS application entry point (SwiftUI + Kotlin framework)

### Layer Organization (in `shared/src/commonMain/kotlin/bo/com/bmsc/`)

**core/di/**: Koin dependency injection
- `ModulesHandler.kt`: Initializes Koin with all modules
- `modules/`: Feature modules (cardModule, commonModules, resourceModules, viewModules)
- Pattern: `platformModule` is `expect`-ed in common, `actual`-ized per platform

**core/database/**: Room database with expect/actual pattern
- `DatabaseFactory.kt`: Common expect declaration, platform-specific builders
- `migration/`: Database migrations
- Schemas stored in `shared/schemas/`

**app/theme/**: Material3 design system
- `Theme.kt`: `BMSCTheme` composable with "Menta" color scheme
- `Color.kt`, `AppFont.kt`, `AppDimens.kt`, `Shape.kt`: Design tokens

**core/composable/**: Reusable UI components organized by type
- `button/`, `card/`, `dialog/`, `slider/`, `radio/`, etc.

**core/domain/**: Domain models and error handling
- `Result.kt`: Wrapper type for operations that can fail
- `CustomException.kt`: Domain-specific exceptions

**core/data/**: Networking with Ktor
- `HttpClientFactory.kt`: Creates Ktor client with JSON/content negotiation

**core/helper/**: Platform-specific utilities via expect/actual
- `keyboardHeightAsState.kt`: Keyboard height observation
- `DeviceInfo.kt`: Device information

**Route.kt**: Navigation routes using Jetpack Navigation Compose (sealed interface of `@Serializable` objects)

### Dependency Injection Pattern

Use `expect val platformModule: Module` in common code with `actual val` implementations in `androidMain` and `iosMain`. The `initModules()` function in `ModulesHandler.kt` assembles all modules.

### Platform-Specific Implementations

When adding platform-specific code:
1. Create `expect` declaration in `commonMain`
2. Create `actual` implementations in `androidMain/` and `iosMain/`
3. Common examples: `DatabaseFactory`, `platformModule`, `DeviceInfo`

### Resources

- Compose Multiplatform resources: `src/commonMain/resources/` accessed via `Res.*` generated class
- SVG icons: Add to `resources/vectors/` or `resources/icons/`, run `generateFileResources` task