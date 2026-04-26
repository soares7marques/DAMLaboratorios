package ao.uam.fc.anuncioslocs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class LocationListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_list)

        setupRecyclerView()
        setupBottomNavigation()

        findViewById<MaterialButton>(R.id.btnCadastrarLocal).setOnClickListener {
            startActivity(Intent(this, CreateLocationActivity::class.java))
        }

        findViewById<TextView>(R.id.tvVerMais).setOnClickListener {
            loadMore()
        }

        loadLocations()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.rvLocations)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = LocationAdapter()
        recyclerView.adapter = adapter
    }

    private fun loadLocations() {
        adapter.submitList(listOf(
            Location("Luanda"),
            Location("Talatona"),
            Location("Samba"),
            Location("Camama")
        ))
    }

    private fun loadMore() {
        adapter.submitList(listOf(
            Location("Viana"),
            Location("Cacuaco"),
            Location("Kilamba"),
            Location("Benfica")
        ))
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
                R.id.nav_location -> true
                R.id.nav_profile -> true
                else -> false
            }
        }
    }
}