package ao.uam.fc.anuncioslocs

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class CreateLocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_location)

        setupBotoes()
        setupBottomNavigation()
    }

    private fun setupBotoes() {
        val etDescricao  = findViewById<EditText>(R.id.etDescricaoLocal)
        val etLongitude  = findViewById<EditText>(R.id.etLongitude)
        val etLatitude   = findViewById<EditText>(R.id.etLatitude)

        findViewById<MaterialButton>(R.id.btnCriarLocalizacao).setOnClickListener {
            val descricao = etDescricao.text.toString().trim()
            val longitude = etLongitude.text.toString().trim()
            val latitude  = etLatitude.text.toString().trim()

            if (descricao.isEmpty()) {
                etDescricao.error = "Preencha a descrição"
                return@setOnClickListener
            }
            if (longitude.isEmpty()) {
                etLongitude.error = "Preencha a longitude"
                return@setOnClickListener
            }
            if (latitude.isEmpty()) {
                etLatitude.error = "Preencha a latitude"
                return@setOnClickListener
            }

            Toast.makeText(this, "Localização criada com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_location
        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_announce -> {
                    startActivity(Intent(this, AnunciosListActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_location -> {
                    startActivity(Intent(this, LocationListActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_profile -> true
                else -> false
            }
        }
    }
}