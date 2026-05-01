package ao.uam.anuncioslocs

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ao.uam.anuncioslocs.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarTextoLogin()
        configurarBotoes()


    }


    private fun configurarTextoLogin() {
        val textoCompleto = "Ja tenho conta"
        val spannable = SpannableString(textoCompleto)
        val inicio = textoCompleto.indexOf("Ja tenho conta")
        if (inicio != -1) {
            spannable.setSpan(UnderlineSpan(), inicio, textoCompleto.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.tvGoToLogin.text = spannable
        }
    }

    private fun configurarBotoes() {
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val utilizador = binding.etUsername.text.toString().trim()
            val senha = binding.etPassword.text.toString().trim()

            if (validarCampos(email, utilizador, senha)) {
                Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                navegarParaLogin()
            }
        }

        binding.tvGoToLogin.setOnClickListener {
            navegarParaLogin()
        }
    }

    private fun validarCampos(email: String, utilizador: String, senha: String): Boolean {
        var valido = true

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "E-mail inválido"
            valido = false
        } else {
            binding.tilEmail.error = null
        }

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

    private fun navegarParaLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}