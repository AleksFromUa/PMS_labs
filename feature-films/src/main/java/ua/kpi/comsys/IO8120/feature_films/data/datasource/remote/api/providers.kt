package ua.kpi.comsys.IO8120.feature_films.data.datasource.remote.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@ExperimentalSerializationApi
inline fun <reified T> provideApi(apiKey: String): T {
    val client = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(apiKey))
        .addInterceptor(PageInterceptor(1))
        .build()

    val json = Json {
        ignoreUnknownKeys = true
    }.asConverterFactory(MediaType.parse("application/json")!!)

    val retrofit = Retrofit.Builder()
        .baseUrl("http://www.omdbapi.com/")
        .client(client)
        .addConverterFactory(json)
        .build()

    return retrofit.create(T::class.java)
}