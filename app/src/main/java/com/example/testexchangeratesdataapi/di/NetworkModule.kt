package com.example.testexchangeratesdataapi.di

import com.example.testexchangeratesdataapi.data.remote.ExchangeRatesApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import com.google.gson.internal.bind.TypeAdapters
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.apilayer.com/exchangerates_data/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    @Named("apiKeyInterceptor")
    fun provideApiKeyInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val request = original.newBuilder()
            .addHeader("apikey", "SFRpneZnQhd8nQIOVQTmJ0l5gg5NACy1")
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named("apiKeyInterceptor") apiKeyInterceptor: Interceptor
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
        gson: Gson
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    @Singleton
    @Provides
    fun provideGson(): Gson {
        val timeFormat = "HH:mm:ss"
        val timeFormatter = DateTimeFormatter.ofPattern(timeFormat)

        val dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val formatter = DateTimeFormatter.ofPattern(dateFormat)

        return GsonBuilder()
            .registerTypeAdapterFactory(TypeAdapters.UUID_FACTORY)
            .registerTypeAdapter(
                LocalTime::class.java,
                JsonDeserializer { json, _, _ ->
                    LocalTime.parse(json.asJsonPrimitive.asString)
                } as JsonDeserializer<LocalTime?>
            )
            .registerTypeAdapter(
                LocalTime::class.java,
                JsonSerializer<LocalTime?> { localTime, _, _ ->
                    JsonPrimitive(timeFormatter.format(localTime))
                }
            )
            .registerTypeAdapter(
                LocalDateTime::class.java,
                JsonDeserializer { json, _, _ ->
                    ZonedDateTime.parse(json.asJsonPrimitive.asString).toLocalDateTime()
                } as JsonDeserializer<LocalDateTime?>
            )
            .registerTypeAdapter(
                LocalDateTime::class.java,
                JsonSerializer<LocalDateTime?> { localDate, _, _ ->
                    JsonPrimitive(formatter.format(localDate))
                }
            )
            .serializeNulls()
            .create()
    }
    @Provides
    @Singleton
    fun provideExchangeRatesApiService(
        retrofit: Retrofit
    ): ExchangeRatesApiService =
        retrofit.create(ExchangeRatesApiService::class.java)
}

