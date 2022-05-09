package pp.developer.ejercicio06

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    companion object{
        const val REQUEST_CODE_LOCATION=0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createFragment()
        val btnLasRanas = findViewById<Button>(R.id.button)
        btnLasRanas.setOnClickListener {
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(LatLng(19.0453683,-98.1987612), 18f),
                4000,
                null
            )
        }
        val btnLaBici = findViewById<Button>(R.id.button2)
        btnLaBici.setOnClickListener {
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(LatLng(19.0519172,-98.2182697), 18f),
                4000,
                null
            )
        }
        val btnLaOriental = findViewById<Button>(R.id.button3)
        btnLaOriental.setOnClickListener {
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(LatLng(19.0695555,-98.1721628), 18f),
                4000,
                null
            )
        }
        val btnFondaStaClara = findViewById<Button>(R.id.button4)
        btnFondaStaClara.setOnClickListener {
            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(LatLng(19.0475761,-98.2010554), 18f),
                4000,
                null
            )
        }

    }
    // Fragment como Mapa
    private fun createFragment(){
        val appFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        appFragment.getMapAsync(this)
    }
    // Pins
    private fun createMarker(){
        var coords = LatLng(19.0453683,-98.1987612)
        var mrk = MarkerOptions().position(coords).title("Las Ranas")
        map.addMarker(mrk)
        coords = LatLng(19.0519172,-98.2182697)
        mrk = MarkerOptions().position(coords).title("Las Bici de Cleta")
        map.addMarker(mrk)
        coords = LatLng(19.0695555,-98.1721628)
        mrk = MarkerOptions().position(coords).title("Las Oriental")
        map.addMarker(mrk)
        coords = LatLng(19.0475761,-98.2010554)
        mrk = MarkerOptions().position(coords).title("Fonda Sta Clara")
        map.addMarker(mrk)
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        createMarker()
        enableLocation()
    }

    private fun enableLocation(){
        if(!::map.isInitialized) return

        if(isLocationPermissionGranted()){
            map.isMyLocationEnabled = true
        }else requestLocationPermission()
    }

    private fun isLocationPermissionGranted()=
        ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED

    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            //Mostrar la ventan de permiso
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                map.isBuildingsEnabled = true
            }else{
                Toast.makeText(this,"Activar permisos", Toast.LENGTH_SHORT).show() }
            else -> {}
        }

    }
}