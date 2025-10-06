import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kspCompose)
    alias(libs.plugins.room)

}

kotlin {
    sourceSets.commonMain {
        kotlin.srcDir("build/generated/ksp/metadata")
    }
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            // put your Multiplatform dependencies here
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.composeVM)
            //Room
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
            //Datastore
            implementation(libs.datastore)
            implementation(libs.datastore.preferences)
        }
        val iosMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                // Add Koin core dependency for iOS
                api(libs.koin.core)
            }
        }
        androidMain.dependencies {
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.navigation.compose)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.room.runtime.android)

        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.mockk.common)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}


android {
    namespace = "dev.muthuram.roomdatabase.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    add("kspAndroid", libs.room.compiler)
    add("kspCommonMainMetadata", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
}