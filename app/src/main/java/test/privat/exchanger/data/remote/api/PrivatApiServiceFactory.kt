package test.privat.exchanger.data.remote.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object PrivatApiServiceFactory {
    private const val CONNECTION_TIMEOUT_MS = 60_000L
    private const val READ_WRITE_TIMEOUT_MS = 60_000L

    fun newInstance(endpoint: String): PrivatApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(endpoint)
            .client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        return retrofit.create(PrivatApiService::class.java)
    }

    private fun okHttpClient(): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().apply {
            connectTimeout(CONNECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            readTimeout(READ_WRITE_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            writeTimeout(READ_WRITE_TIMEOUT_MS, TimeUnit.MILLISECONDS)
            addInterceptor { chain ->
                val request = chain.request()
                val builder = request
                    .newBuilder()
                    .method(request.method, request.body)
                val mutatedRequest = builder.build()
                val response = chain.proceed(mutatedRequest)
                response
            }
            addInterceptor(loggingInterceptor)
        }.build()
    }
}