package ao.uam.anuncioslocs

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import android.widget.Toast

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AnnouncementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupRecyclerView()
        setupBottomNavigation()
        setupButtons()
        loadAnnouncements()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.rvAnnouncements)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AnnouncementAdapter()
        recyclerView.adapter = adapter
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_home
        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> true

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


                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                    true
                }

                else -> false
            }
        }
    }

    private fun setupButtons() {
        val btnOldPosts = findViewById<MaterialButton>(R.id.btnOldPosts)
        val btnOldPosts2 = findViewById<MaterialButton>(R.id.btnOldPosts2)

        btnOldPosts.setOnClickListener {
            loadOldPosts()
        }

        btnOldPosts2.setOnClickListener {
            loadOldPosts()
        }

    }

    private fun loadAnnouncements() {
        val announcements = listOf(
            Announcement("Luanda", "Há 5 min.", "Aluguel de quarto espaçoso"),
            Announcement("Luanda", "Há 15 min.", "Venda de carro seminovo"),
            Announcement("Benguela", "Há 30 min.", "Apartamento para arrendar")
        )
        adapter.submitList(announcements)
    }

    private fun loadOldPosts() {
        val oldPosts = listOf(
            Announcement("Luanda", "Há 10 min.", "Aluguel de quarto"),
            Announcement("Luanda", "Há 2 dias", "Venda de móveis"),
            Announcement("Benguela", "Há 5 dias", "Curso de programação")
        )
        adapter.submitList(oldPosts)
    }
}