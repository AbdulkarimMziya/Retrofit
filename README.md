# Retrofit Guide

## Setting Up Dependencies for Retrofit, OkHttp, and Coroutines in an Android Project

Follow the steps below to set up Retrofit, OkHttp, and Kotlin Coroutines in your Android project using Gradle Kotlin DSL (`build.gradle.kts`):

## 1. Adding Dependencies

Open the `build.gradle.kts` file of your module (`Module:app`) and add the following dependencies:

### Retrofit and OkHttp Dependencies
```kotlin
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.okhttp3:okhttp:4.9.0")
```

### Kotlin Coroutines Dependencies
```kotlin
// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")

// Coroutine Lifecycle Scopes
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
```

## 2. Enabling View Binding

In the `build.gradle.kts` file of your module (`Module:app`), enable view binding by adding the following block:

```kotlin
android {
    buildFeatures {
        viewBinding = true
    }
}
```

## 3. Adding Internet Permission

Ensure your application has permission to access the internet. Open the `AndroidManifest.xml` file and add the following line:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## 4. Creating the Retrofit Client Object

Create a Retrofit client object to handle HTTP requests. Below is an example:

```kotlin
object RetrofitClient {
    private const val BASE_URL = "https://localhost:8090/v1/"

    fun getClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }
}
```

### Notes
- The base URL in Retrofit must end with a `/` character; otherwise, the requests will fail.

## 5. Creating the API Interface

Define the endpoints you will be using by creating an API interface. For example:

```kotlin
interface UserAPI {
    @GET("users")
    fun getUsers(): Call<List<User>>
}
```
## 6. Creating the Data Transfer Object

You will need to create a data transfer object (DTO) to represent the data that will be exchanged with the API. Below is an example:

```kotlin
data class User(
    @JsonProperty("id") val id: Long,
    @JsonProperty("name") val name: String,
    @JsonProperty("age") val age: Int
)
```
