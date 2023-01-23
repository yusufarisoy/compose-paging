object Versions {
    const val hilt = "2.42"
    const val compose = "1.3.3"
    const val retrofit = "2.9.0"
    const val lifecycle = "2.4.1"
    const val coroutines = "1.5.2"
}

object Libs {
    const val toolsGradlePlugin = "com.android.tools.build:gradle:7.1.1"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    // Core
    const val coreKtx = "androidx.core:core-ktx:1.9.0"
    // AppCompat
    const val appCompat = "androidx.appcompat:appcompat:1.6.0"
    // Activity
    const val activityCompose = "androidx.activity:activity-compose:1.4.0"
    // Material
    const val material = "com.google.android.material:material:1.5.0"
    // Gson
    const val gson = "com.google.code.gson:gson:2.8.7"
    // Coil
    const val coilCompose = "io.coil-kt:coil-compose:1.4.0"
    // Lifecycle
    const val lifecycleFragment = "androidx.fragment:fragment-ktx:1.4.1"
    const val lifecycleRunTime = "androidx.lifecycle:lifecycle-runtime-ktx:2.5.0-alpha02"
    const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    // Coroutines
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    // Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    // Hilt
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    // Compose
    const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeCompiler = "androidx.compose.compiler:compiler:1.3.2"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val composeUiTest = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    const val composeMaterial = "androidx.compose.material:material:1.3.1"
    const val composeTooling = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    // Test
    const val testJunit = "junit:junit:4.+"
    const val testExtJunit = "androidx.test.ext:junit:1.1.3"
    const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
}