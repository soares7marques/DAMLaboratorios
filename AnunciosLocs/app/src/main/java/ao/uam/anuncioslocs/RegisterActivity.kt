package ao.uam.anuncioslocs

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ao.uam.anuncioslocs.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarBotoes()
    }

    private fun configurarBotoes() {
        binding.btnRegister.setOnClickListener {
            // Captura os 3 campos
            val name = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim() // Seu email
            val senha = binding.etPassword.text.toString().trim()

            // Valida os 4 parâmetros (incluindo o nome)
            if (validarCampos(name,email, senha)) {
                executarCadastro(name,email, senha)
            }
        }

        // AÇÃO PARA VOLTAR AO LOGIN
        binding.tvGoToLogin.setOnClickListener {
            finish() // Fecha a RegisterActivity e volta para a LoginActivity
        }
    }

    private fun executarCadastro(name: String, email: String, senha: String) {
        lifecycleScope.launch {
            try {
                // Criar o objeto com Nome, email e Senha
                val request = RegistroRequest(
                    name = name,
                    email = email,
                    senha = senha
                )

                // Chama o Retrofit
                val response = RetrofitClient.apiServiceCentral.fazerRegistro(request)

                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                } else {
                    val erro = response.errorBody()?.string() ?: "Erro desconhecido"
                    Toast.makeText(this@RegisterActivity, "Falha: $erro", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                // Erro de rede (IP do Hotspot, Firewall ou Servidor desligado)
                Toast.makeText(this@RegisterActivity, "Erro de rede: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validarCampos(name: String, email: String, senha: String): Boolean {
        var valido = true

        // Validação do Nome
        if (name.isEmpty()) {
            binding.etUsername.error = "O nome é obrigatório"
            valido = false
        } else {
            binding.etUsername.error = null
        }

        // Validação do Email/Utilizador
        if (email.isEmpty()) {
            binding.etEmail.error = "Campo obrigatório"
            valido = false
        } else {
            binding.etEmail.error = null
        }

        // Validação da Senha
        if (senha.length < 6) {
            binding.etPassword.error = "Mínimo 6 caracteres"
            valido = false
        } else {
            binding.etPassword.error = null
        }
        return valido
    }
}