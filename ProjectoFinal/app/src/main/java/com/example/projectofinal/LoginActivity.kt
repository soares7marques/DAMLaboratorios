package ao.uam.fc.anuncioslocs

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ao.uam.fc.anuncioslocs.databinding.ActivityLoginBinding

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
            val utilizador = binding.etUsername.text.toString().trim()
            val senha = binding.etPassword.text.toString().trim()

            if (validarCampos(utilizador, senha)) {
                navegarParaHome()
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

    private fun validarCampos(utilizador: String, senha: String): Boolean {
        var valido = true
        if (utilizador.isEmpty()) {
            binding.tilUsername.error = "Preencha o utilizador"
            valido = false
        } else {
            binding.tilUsername.error = null
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