package ao.uam.anuncioslocs

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MeusAnunciosActivity : AppCompatActivity() {

    private lateinit var adapter: AnnouncementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meus_anuncios)

        val recyclerView = findViewById<RecyclerView>(R.id.rvMeusAnuncios)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AnnouncementAdapter()
        recyclerView.adapter = adapter

        // Simula anúncios do utilizador actual
        adapter.submitList(listOf(
            Announcement("Luanda", "Há 5 min.", "Aluguel de quarto espaçoso"),
            Announcement("Luanda", "Há 2 dias", "Venda de carro seminovo")
        ))

        setupBottomNavigation()
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
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish(); true
                }
                else -> false
            }
        }
    }
}