package com.example.testeffectivemobile

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class MockServerManagerWrapper(context: Context) {
    companion object {
        private const val MOCK_SERVER_PORT = 8090
    }

    private val mockWebServerManager = MockWebServerManager(context, MOCK_SERVER_PORT)

  suspend fun startMockServer() {
        mockWebServerManager.start()
      repeat(10){
          mockWebServerManager.mockResponses(
              "/courses" to Response("courses.json", HttpURLConnection.HTTP_OK),
              )
      }

    }

    fun getUrl(): String = mockWebServerManager.getUrl()
}

private const val CONNECT_TIMEOUT = 10L
private const val WRITE_TIMEOUT = 10L
private const val READ_TIMEOUT = 10L

@OptIn(ExperimentalSerializationApi::class)
fun networkModule(baseUrl: String) = module {

    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(baseUrl)
            .addConverterFactory(get())
            .build()
    }


    single {
        OkHttpClient().newBuilder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(get<Interceptor>())
            .build()
    }

    single<Interceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        Json.asConverterFactory("application/json; charset=UTF8".toMediaType())
    }
}