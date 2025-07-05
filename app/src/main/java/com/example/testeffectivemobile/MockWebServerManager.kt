package com.example.testeffectivemobile


import android.util.Log
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection
import java.util.LinkedList
import java.util.Queue
import java.io.IOException
import android.content.Context
import okio.Buffer


class MockWebServerManager(private val context: Context, private val port: Int) {
    private lateinit var server: MockWebServer
    private val dispatcher = object : Dispatcher() {
        private val responses = mutableMapOf<String, Queue<Response>>()

        override fun dispatch(request: RecordedRequest): MockResponse {
            if (request.path == "/auth/login" && request.method == "POST") {
                val body = request.body.readUtf8()
                val login = body.substringAfter("\"email\":\"").substringBefore("\"")
                val password = body.substringAfter("\"password\":\"").substringBefore("\"")


                val (responseFile, responseCode) = when {
                    login == "user1@gmail.com" && password == "1234" -> {
                        "authUser1.json" to HttpURLConnection.HTTP_OK
                    }
                    else -> {
                        "error.json" to HttpURLConnection.HTTP_UNAUTHORIZED
                    }
                }

                val responseBody = getAssetFileContent(responseFile, "application/json")

                return MockResponse().apply {
                    //setResponseCode(HttpURLConnection.HTTP_OK)
                    setResponseCode(responseCode)
                    when (responseBody) {
                        is String -> setBody(responseBody)
                        is ByteArray -> {
                            val buffer = Buffer().write(responseBody)
                            setBody(buffer)
                        }

                        null -> {
                            setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
                        }
                    }
                }
            }

            // Обработка других запросов
            val response = responses[request.path]?.poll()

            if (response == null) {
                Log.w("MockWebServer", "No response found for path: ${request.path}")
                return MockResponse().apply {
                    setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                }
            }

            Log.d("MockWebServer", "Loading file from assets: ${response.assetFile}")

            val body = try {
                getAssetFileContent(
                    response.assetFile,
                    response.contentType
                ) // Метод для загрузки из assets
            } catch (e: IOException) {
                Log.e("MockWebServer", "File not found: ${response.assetFile}", e)
                return MockResponse().apply {
                    setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
                }
            }

            return MockResponse().apply {
                setResponseCode(response.statusCode)
                when (body) {
                    is ByteArray -> {
                        val buffer = Buffer().write(body)
                        setBody(buffer)
                    }

                    is String -> setBody(body)
                }
                response.contentType?.let { setHeader("Content-Type", it) }
            }
        }

        fun addMockResponse(requestUrl: String, response: Response) {
            val queue = responses.getOrPut(requestUrl) { LinkedList() }
            queue.add(response)
        }

    }


    private fun getAssetFileContent(assetFile: String?, contentType: String?): Any? {
        return when {
            assetFile == null -> null
            contentType?.startsWith("image/") == true -> {

                context.assets.open(assetFile).use { inputStream ->
                    inputStream.readBytes()
                }
            }

            else -> {
                context.assets.open(assetFile).bufferedReader().use { it.readText() }
            }
        }
    }

    fun start() {
        server = MockWebServer()
        server.dispatcher = dispatcher

        try {
            server.start(port)
            println("MockWebServer successfully launched on port $port")
        } catch (e: IOException) {
            println("Error when starting MockWebServer: ${e.message}")
        }
    }

    fun shutdown() {
        try {
            server.shutdown()
            println("MockWebServer successfully shut down.")
        } catch (e: Throwable) {
            println("Error when shutting down MockWebServer: ${e.message}")
        }
    }

    fun mockResponses(vararg pairs: Pair<String, Response>) {
        pairs.forEach { (request, response) ->
            dispatcher.addMockResponse(request, response)
        }
    }

    fun getUrl(): String {
        return server.url("/").toString()
    }
}

data class Response(
    val assetFile: String? = null,
    val statusCode: Int = HttpURLConnection.HTTP_OK,
    val contentType: String? = null
)
