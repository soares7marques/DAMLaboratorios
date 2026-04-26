package ao.uam.anuncioslocs

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class ProfileActivity : AppCompatActivity() {

    private val preferencias = mutableListOf("Futebol", "Morada", "Gostos em comum")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupPreferencias()
        setupBotoes()
        setupBottomNavigation()
    }

    private fun setupPreferencias() {
        atualizarListaPreferencias()

        findViewById<TextView>(R.id.tvAdicionarPreferencia).setOnClickListener {
            mostrarDialogAddPreferencia()
        }
    }

    private fun atualizarListaPreferencias() {
        val container = findViewById<LinearLayout>(R.id.llPreferencias)
        container.removeAllViews()

        for (pref in preferencias) {
            val tv = TextView(this).apply {
                text = pref
                textSize = 15f
                setTextColor(0xFF333333.toInt())
                setPadding(0, 12, 0, 12)
            }
            container.addView(tv)
        }
    }

    private fun mostrarDialogAddPreferencia() {
        val input = EditText(this).apply {
            hint = "Nova preferência"
            setPadding(40, 20, 40, 20)
        }

        AlertDialog.Builder(this)
            .setTitle("Adicionar Preferência")
            .setView(input)
            .setPositiveButton("Adicionar") { _, _ ->
                val nova = input.text.toString().trim()
                if (nova.isNotEmpty()) {
                    preferencias.add(nova)
                    atualizarListaPreferencias()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun setupBotoes() {
        findViewById<MaterialButton>(R.id.btnAlterarSenha).setOnClickListener {
            mostrarDialogAlterarSenha()
        }

        findViewById<MaterialButton>(R.id.btnMeusAnuncios).setOnClickListener {
            startActivity(Intent(this, MeusAnunciosActivity::class.java))
        }
    }

    private fun mostrarDialogAlterarSenha() {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 20, 40, 20)
        }

        val etAtual = EditText(this).apply {
            hint = "Senha actual"
            inputType = android.text.InputType.TYPE_CLASS_TEXT or
                    android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        val etNova = EditText(this).apply {
            hint = "Nova senha"
            inputType = android.text.InputType.TYPE_CLASS_TEXT or
                    android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        val etConfirmar = EditText(this).apply {
            hint = "Confirmar nova senha"
            inputType = android.text.InputType.TYPE_CLASS_TEXT or
                    android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        layout.addView(etAtual)
        layout.addView(etNova)
        layout.addView(etConfirmar)

        AlertDialog.Builder(this)
            .setTitle("Alterar Palavra-Passe")
            .setView(layout)
            .setPositiveButton("Guardar") { _, _ ->
                val nova = etNova.text.toString()
                val confirmar = etConfirmar.text.toString()
                if (nova.length < 6) {
                    Toast.makeText(this, "Mínimo 6 caracteres", Toast.LENGTH_SHORT).show()
                } else if (nova != confirmar) {
                    Toast.makeText(this, "Senhas não coincidem", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Senha alterada com sucesso!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_profile
        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish(); true
                }
                R.id.nav_announce -> {
                    startActivity(Intent(this, AnunciosListActivity::class.java))
                    finish(); true
                }
                R.id.nav_location -> {
                    startActivity(Intent(this, LocationListActivity::class.java))
                    finish(); true
                }
                R.id.nav_profile -> true
                else -> false
            }
        }
    }
}