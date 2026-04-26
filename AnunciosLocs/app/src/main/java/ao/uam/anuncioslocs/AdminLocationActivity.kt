package ao.uam.anuncioslocs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

data class LocationRequest(
    val id: Int,
    val nome: String,
    val utilizador: String,
    val longitude: String,
    val latitude: String,
    var status: String = "Pendente"
)

class AdminLocationActivity : AppCompatActivity() {

    private lateinit var adapter: LocationRequestAdapter
    private val pendingLocations = mutableListOf(
        LocationRequest(1, "Talatona Sul", "João Silva", "-8.9145", "13.1876"),
        LocationRequest(2, "Viana Norte", "Maria Costa", "-8.9035", "13.3745"),
        LocationRequest(3, "Benfica Leste", "Pedro Neto", "-8.8765", "13.2134"),
        LocationRequest(4, "Cacuaco Centro", "Ana Ferreira", "-8.7823", "13.3456")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_location)

        setupRecyclerView()
        setupBottomNavigation()

        // Contadores
        atualizarContadores()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rvPendingLocations)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = LocationRequestAdapter(
            onAprovar = { location ->
                location.status = "Aprovada"
                Toast.makeText(this, "${location.nome} aprovada!", Toast.LENGTH_SHORT).show()
                pendingLocations.removeIf { it.id == location.id }
                adapter.submitList(pendingLocations.toList())
                atualizarContadores()
            },
            onRejeitar = { location ->
                location.status = "Rejeitada"
                Toast.makeText(this, "${location.nome} rejeitada!", Toast.LENGTH_SHORT).show()
                pendingLocations.removeIf { it.id == location.id }
                adapter.submitList(pendingLocations.toList())
                atualizarContadores()
            }
        )
        recyclerView.adapter = adapter
        adapter.submitList(pendingLocations.toList())
    }

    private fun atualizarContadores() {
        findViewById<TextView>(R.id.tvTotalPendentes).text =
            "${pendingLocations.size} pendentes"
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

class LocationRequestAdapter(
    private val onAprovar: (LocationRequest) -> Unit,
    private val onRejeitar: (LocationRequest) -> Unit
) : ListAdapter<LocationRequest, LocationRequestAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNome: TextView       = itemView.findViewById(R.id.tvLocationNome)
        val tvUtilizador: TextView = itemView.findViewById(R.id.tvUtilizador)
        val tvCoordenadas: TextView = itemView.findViewById(R.id.tvCoordenadas)
        val btnAprovar: MaterialButton  = itemView.findViewById(R.id.btnAprovar)
        val btnRejeitar: MaterialButton = itemView.findViewById(R.id.btnRejeitar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_location_request, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.tvNome.text        = item.nome
        holder.tvUtilizador.text  = "Por: ${item.utilizador}"
        holder.tvCoordenadas.text = "Lat: ${item.latitude} | Long: ${item.longitude}"
        holder.btnAprovar.setOnClickListener  { onAprovar(item) }
        holder.btnRejeitar.setOnClickListener { onRejeitar(item) }
    }

    class DiffCallback : DiffUtil.ItemCallback<LocationRequest>() {
        override fun areItemsTheSame(a: LocationRequest, b: LocationRequest) = a.id == b.id
        override fun areContentsTheSame(a: LocationRequest, b: LocationRequest) = a == b
    }
}