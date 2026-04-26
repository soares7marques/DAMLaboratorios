package ao.uam.anuncioslocs

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class AnunciosListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AnnouncementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anuncios_list)

        setupRecyclerView()
        setupBottomNavigation()

        findViewById<MaterialButton>(R.id.btnCadastrar).setOnClickListener {
            startActivity(Intent(this, CreateAnuncioActivity::class.java))
        }

        findViewById<TextView>(R.id.tvVerMais).setOnClickListener {
            loadMoreAnnouncements()
        }

        loadAnnouncements()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.rvAnnouncements)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AnnouncementAdapter()
        recyclerView.adapter = adapter
    }

    private fun loadAnnouncements() {
        adapter.submitList(listOf(
            Announcement("Luanda", "Há 5 min.", "Aluguel de quarto espaçoso"),
            Announcement("Luanda", "Há 15 min.", "Venda de carro seminovo"),
            Announcement("Benguela", "Há 30 min.", "Apartamento para arrendar")
        ))
    }

    private fun loadMoreAnnouncements() {
        adapter.submitList(listOf(
            Announcement("Luanda", "Há 1 hora", "Curso de inglês online"),
            Announcement("Huambo", "Há 2 horas", "Venda de electrodomésticos"),
            Announcement("Luanda", "Há 3 horas", "Serviço de reparação de carros")
        ))
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_announce
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