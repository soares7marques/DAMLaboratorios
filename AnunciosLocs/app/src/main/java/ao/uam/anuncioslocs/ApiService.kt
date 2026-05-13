package ao.uam.anuncioslocs

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.google.gson.annotations.SerializedName

// Modelos de dados (devem ser iguais aos do seu Spring Boot)
data class LoginRequest(val email: String, val senha: String)
data class RegistroRequest(val name: String, val email: String, val senha: String)

data class LoginResponseWrapper(
    @SerializedName("body") val body: AuthResponse?,
    @SerializedName("statusCode") val statusCode: String?,
    @SerializedName("statusCodeValue") val statusCodeValue: Int?
)

data class AuthResponse(
    @SerializedName("Ticket") val ticket: String?,      // Mapeia "Ticket" do JSON para "ticket"
    @SerializedName("SessionKey") val sessionKey: String? // Mapeia "SessionKey" para "sessionKey"
)
interface ApiService {
    @POST("utilizador/register") // Ajuste para o seu endpoint real do Spring
    suspend fun fazerRegistro(@Body request: RegistroRequest): Response<AuthResponse>

    @POST("auth/login") // Ajuste para o seu endpoint real do Spring
    suspend fun fazerLogin(@Body request: LoginRequest): Response<LoginResponseWrapper>
}