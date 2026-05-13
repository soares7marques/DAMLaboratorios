package ao.uam.anuncioslocs

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ao.uam.anuncioslocs.databinding.ActivityCreateLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class CreateLocationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateLocationBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            obterLocalizacaoGps()
        } else {
            Toast.makeText(this, "Permissão necessária para obter coordenadas", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setupBotoes()
        setupBottomNavigation()

        // Inicia a busca automática
        verificarPermissoesECapturar()

        binding.tilLatitude.setEndIconOnClickListener { verificarPermissoesECapturar() }
        binding.tilLongitude.setEndIconOnClickListener { verificarPermissoesECapturar() }
    }

    private fun verificarPermissoesECapturar() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            obterLocalizacaoGps()
        } else {
            requestPermissionLauncher.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        }
    }

    private fun obterLocalizacaoGps() {
        try {
            // ATIVAR ESTADO DE BUSCA
            setLoadingState(true)

            // 1. Tentar Cache (Rápido)
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    preencherCampos(location)
                    // Se a precisão for boa (menos de 20m), podemos parar o loading aqui
                    if (location.accuracy < 20) setLoadingState(false)
                }
            }

            // 2. Tentar Satélite (Preciso, mas pode demorar)
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).addOnSuccessListener { location: Location? ->
                if (location != null) {
                    preencherCampos(location)
                    setLoadingState(false) // DESATIVAR AO ENCONTRAR
                } else {
                    tentarUltimaLocalizacaoConhecida()
                }
            }.addOnFailureListener {
                setLoadingState(false)
                Log.e("GPS_ERROR", "Falha na busca")
            }
        } catch (e: SecurityException) {
            setLoadingState(false)
        }
    }

    private fun preencherCampos(location: Location) {
        binding.etLatitude.setText(location.latitude.toString())
        binding.etLongitude.setText(location.longitude.toString())
        binding.etRaio.setText(location.accuracy.toInt().toString())
    }

    // Função para controlar a interface enquanto busca
    private fun setLoadingState(isLoading: Boolean) {
        if (isLoading) {
            binding.btnCriarLocalizacao.isEnabled = false
            binding.btnCriarLocalizacao.text = "Buscando coordenadas..."

            // Opcional: colocar um aviso nos campos
            if (binding.etLatitude.text?.isEmpty() == true) {
                binding.etLatitude.hint = "Buscando..."
                binding.etLongitude.hint = "Buscando..."
            }
        } else {
            binding.btnCriarLocalizacao.isEnabled = true
            binding.btnCriarLocalizacao.text = "Criar Localização"
            binding.etLatitude.hint = "Latitude"
            binding.etLongitude.hint = "Longitude"
        }
    }

    private fun tentarUltimaLocalizacaoConhecida() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            setLoadingState(false)
            if (location != null) {
                preencherCampos(location)
            } else {
                Toast.makeText(this, "Sinal de GPS fraco. Tente mover-se.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupBotoes() {
        binding.btnCriarLocalizacao.setOnClickListener {
            val descricao = binding.etDescricaoLocal.text.toString().trim()
            val longitude = binding.etLongitude.text.toString().trim()
            val latitude  = binding.etLatitude.text.toString().trim()
            val raio      = binding.etRaio.text.toString().trim()

            if (validarCampos(descricao, longitude, latitude, raio)) {
                Toast.makeText(this, "Localização salva!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun validarCampos(desc: String, lon: String, lat: String, raio: String): Boolean {
        if (desc.isEmpty()) { binding.etDescricaoLocal.error = "Obrigatório"; return false }
        if (lon.isEmpty()) { Toast.makeText(this, "Aguarde o GPS", Toast.LENGTH_SHORT).show(); return false }
        return true
    }

    private fun setupBottomNavigation() {
        binding.bottomNav.selectedItemId = R.id.nav_location
        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> { startActivity(Intent(this, HomeActivity::class.java)); finish(); true }
                R.id.nav_announce -> { startActivity(Intent(this, AnunciosListActivity::class.java)); finish(); true }
                R.id.nav_location -> true
                else -> false
            }
        }
    }
}