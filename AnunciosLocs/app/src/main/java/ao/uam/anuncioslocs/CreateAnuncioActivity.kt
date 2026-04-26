package ao.uam.anuncioslocs

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class CreateAnuncioActivity : AppCompatActivity() {

    private lateinit var spinnerLocal: Spinner
    private lateinit var etDescricao: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_anuncio)

        spinnerLocal = findViewById(R.id.spinnerLocal)
        etDescricao  = findViewById(R.id.etDescricao)

        setupSpinner()
        setupBotoes()
        setupBottomNavigation()
    }

    private fun setupSpinner() {
        val locais = listOf("Selecione o Local", "Luanda", "Benguela", "Huambo", "Lobito", "Malanje")
        val adapter = ArrayAdapter(this, R.layout.spinner_item, locais)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerLocal.adapter = adapter
    }

    private fun setupBotoes() {
        findViewById<MaterialButton>(R.id.btnCriarAnuncio).setOnClickListener {
            val local = spinnerLocal.selectedItem.toString()
            val descricao = etDescricao.text.toString().trim()

            if (local == "Selecione o Local") {
                Toast.makeText(this, "Selecione um local", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (descricao.isEmpty()) {
                etDescricao.error = "Preencha a descrição"
                return@setOnClickListener
            }

            Toast.makeText(this, "Anúncio criado com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        }

        findViewById<MaterialButton>(R.id.btnLocalizacao).setOnClickListener {
            Toast.makeText(this, "Funcionalidade de GPS em breve", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_announce -> true
                R.id.nav_location -> true
                R.id.nav_profile  -> true
                else -> false
            }
        }
    }
}