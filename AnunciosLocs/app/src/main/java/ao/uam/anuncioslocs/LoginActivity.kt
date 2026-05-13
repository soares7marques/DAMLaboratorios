package ao.uam.anuncioslocs

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope // Necessário para lifecycleScope
import ao.uam.anuncioslocs.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch // Necessário para o launch
import android.util.Log// ... dentro da função fazerLogin ...
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configurarTextoRegistar()
        configurarBotoes()
    }

    private fun configurarTextoRegistar() {
        val textoCompleto = "Nao tem uma conta? Cadastrar-se"
        val spannable = SpannableString(textoCompleto)
        val inicio = textoCompleto.indexOf("Cadastrar-se")
        if (inicio != -1) {
            spannable.setSpan(UnderlineSpan(), inicio, textoCompleto.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.tvGoToRegister.text = spannable
        }
    }

    private fun configurarBotoes() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val senha = binding.etPassword.text.toString().trim()

            if (validarCampos(email, senha)) {
                // INÍCIO DA IMPLEMENTAÇÃO DO LOGIN REAL
                fazerLogin(email, senha)
            }
        }

        binding.tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Funcionalidade em breve!", Toast.LENGTH_SHORT).show()
        }

        binding.tvGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fazerLogin(email: String, senha: String) {
        lifecycleScope.launch {
            try {
                // 1. Chama o serviço Kerberos
                val response = RetrofitClient.apiServiceKerberos.fazerLogin(LoginRequest(email, senha))

                if (response.isSuccessful) {
                    // 'wrapper' contém o JSON inteiro (com headers, body, statusCode)
                    val wrapper = response.body()
                    Log.d("LOGIN_DEBUG", "JSON Completo: $wrapper") // Verifica se o wrapper não está nulo

                    // 'auth' contém apenas o que está dentro da tag "body"
                    val auth = wrapper?.body
                    Log.d("LOGIN_DEBUG", "JSON Completo: $auth") // Verifica se o body esta mapeiado


                    // 2. CAPTURA E LOG DOS DADOS (Verifique se na Data Class é Ticket ou ticket)
                    val ticket = auth?.ticket
                    val sessionKey = auth?.sessionKey

                    Log.d("LOGIN_SUCESSO", "Ticket recebido: $ticket")
                    Log.d("LOGIN_SUCESSO", "SessionKey recebida: $sessionKey")

                    // 3. NAVEGAÇÃO ÚNICA PASSANDO OS DADOS
                    // Usamos this@LoginActivity porque estamos dentro de um launch
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    intent.putExtra("TICKET", ticket)
                    intent.putExtra("SESSION_KEY", sessionKey)

                    Toast.makeText(this@LoginActivity, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show()

                    startActivity(intent)
                    finish() // Fecha a tela de login

                } else {
                    Toast.makeText(this@LoginActivity, "Erro: Usuário ou senha inválidos", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("LOGIN_ERRO", "Erro de rede: ${e.message}")
                Toast.makeText(this@LoginActivity, "Erro de rede: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validarCampos(email: String, senha: String): Boolean {
        var valido = true
        if (email.isEmpty()) {
            binding.etEmail.error = "Preencha o utilizador"
            valido = false
        } else {
            binding.etEmail.error = null
        }

        if (senha.length < 6) {
            binding.tilPassword.error = "Mínimo 6 caracteres"
            valido = false
        } else {
            binding.tilPassword.error = null
        }
        return valido
    }

    private fun navegarParaHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}