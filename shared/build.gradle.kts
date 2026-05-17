import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.room)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    androidLibrary {
       namespace = "bo.com.bmsc.shared"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()
    
       compilerOptions {
           jvmTarget = JvmTarget.JVM_11
       }
       androidResources {
           enable = true
       }
       withHostTest {
           isIncludeAndroidResources = true
       }
    }
    
    sourceSets {
        commonMain {
            kotlin.srcDir(layout.buildDirectory.dir("generated/source/svg2compose/kotlin"))
        }

        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)

            // koin
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.koin.androidx.workmanager)

            // ktor
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.material.icons.extended)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)

            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)


            // koin
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.navigation.compose)

            // coil
            implementation(libs.coil.compose)
            implementation(libs.coil.svg)

            // ktor
            implementation(libs.ktor.client.core)
            implementation(libs.bundles.ktor)
            implementation(libs.bundles.coil)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

tasks.named("generateActualResourceCollectorsForAndroidMain") {
    dependsOn("generateFileResources")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>>().configureEach {
    dependsOn("generateFileResources")
}


// Configuration for SVG to Compose task
buildscript {
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        // Add SVG to Compose as a build script dependency
        classpath("com.github.DevSrSouza:svg-to-compose:0.11.0") {
            exclude(group = "com.sun.activation", module = "javax.activation")
            exclude(group = "javax.activation", module = "activation")
            exclude(group = "javax.activation", module = "javax.activation-api")
            exclude(group = "org.ogce", module = "xpp3")
        }
    }
}

tasks.register("generateFileResources") {
    val sep = File.separator
    var resourcePath = "${layout.projectDirectory.asFile.absolutePath}/src/commonMain/resources/"
    resourcePath = resourcePath.replace(oldValue = "/", sep)

    val assets = listOf("vectors", "icons")
    val outputPath = "${layout.buildDirectory.get()}/generated/source/svg2compose/kotlin"
    val outputDir = File(outputPath.replace(oldValue = "/", sep))

    doFirst {
        assets.forEach {
            val assetsDir = File("$resourcePath$it")

            assetsDir.mkdirs()
        }
        outputDir.mkdirs()
    }

    doLast {
        assets.forEach { asset ->
            val assetsDir = File("$resourcePath$asset")

            if (assetsDir.exists() && assetsDir.listFiles()?.any { it.extension == "svg" } == true) {
                br.com.devsrsouza.svg2compose.Svg2Compose.parse(
                    applicationIconPackage = "bo.com.bmsc.assets",
                    accessorName = "BMSC${asset.replaceFirstChar { it.uppercase() }}",
                    outputSourceDirectory = outputDir,
                    vectorsDirectory = assetsDir,
                    type = br.com.devsrsouza.svg2compose.VectorType.SVG,
                    generatePreview = false,
                    allAssetsPropertyName = "AllAssets"
                )
                println("SVG to Compose conversion completed. Files generated in ${outputDir.absolutePath}")
            } else {
                println("No SVG files found in ${assetsDir.absolutePath}")
            }
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}
