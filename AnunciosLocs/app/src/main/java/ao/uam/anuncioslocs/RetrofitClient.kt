package ao.uam.anuncioslocs

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // 1. DESCOBRIR SEU IP: No terminal do Linux digite 'hostname -I'
    // Substitua o 192.168.1.XX pelo seu IP real
    private const val BASE_URL1 = "http://192.168.43.80:8080/"
    private const val BASE_URL2 = "http://192.168.43.80:8081/"

    val apiServiceCentral: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL1)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    val apiServiceKerberos: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}