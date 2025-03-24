import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.cr.articlesjava"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.cr.articlesjava"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    fun loadLocalProperties(rootDir: File): Properties {
        val propertiesFile = File(rootDir, "local.properties")
        val properties = Properties()
        if (propertiesFile.exists()) {
            propertiesFile.inputStream().use { inputStream ->
                properties.load(inputStream)
            }
        }
        return properties
    }

    // Load properties
    val localProperties = loadLocalProperties(rootDir)

    // Signing Config
    val keystorePath: String = localProperties.getProperty("keystorePath")
    val keystorePass: String = localProperties.getProperty("keystorePass")
    val keyID: String = localProperties.getProperty("keyID")
    val keyPass: String = localProperties.getProperty("keyPass")

    signingConfigs {
        getByName("debug") {
            storeFile = file(keystorePath)
            storePassword = keystorePass
            keyAlias = keyID
            keyPassword = keyPass
        }
        create("release") {
            storeFile = file(keystorePath)
            storePassword = keystorePass
            keyAlias = keyID
            keyPassword = keyPass
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    gradle.projectsEvaluated {
        tasks.withType(JavaCompile::class.java) {
            options.compilerArgs.add("-Xmaxerrs")
            options.compilerArgs.add("500")
        }
    }

}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

//    Retrofit
    implementation(libs.bundles.retrofit)

//    Lifecycle
    implementation(libs.bundles.lifecycle)
    annotationProcessor(libs.lifecycle.compiler)

//    RxJava
    implementation(libs.bundles.rxjava)

//    Dagger
    implementation(libs.bundles.dagger)
    annotationProcessor(libs.dagger.compiler)
    annotationProcessor(libs.dagger.android.processor)

//    Glide
    implementation(libs.glide)

}