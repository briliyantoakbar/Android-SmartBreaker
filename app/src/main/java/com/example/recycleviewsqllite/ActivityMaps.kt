package com.example.recycleviewsqllite

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

private lateinit var fusedLocationClient: FusedLocationProviderClient
private var googleMap: GoogleMap? = null
var latitude = 0.0
var longitude = 0.0
var statemap = false
lateinit var btnkoordinat: Button
lateinit var btnposisi_user: Button
private lateinit var locationCallback: LocationCallback

class ActivityMaps : AppCompatActivity(), OnMapReadyCallback, onMaps {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        btnkoordinat = findViewById<Button>(R.id.koordinat)
        btnposisi_user = findViewById<Button>(R.id.posisi_user)
        var id1 = intent.getStringExtra("Id")
        var Uuid1 = intent.getStringExtra("Uuid").toString()
        var Nama1 = intent.getStringExtra("NamaRuang")
        var TipeRuang1 = intent.getStringExtra("Tiperuang")
        var Relay1 = intent.getStringExtra("Relay")
        var NamaProduk1 = intent.getStringExtra("NamaProduk")
        var TipeProduk1 = intent.getStringExtra("TipeProduk")
        var UuidProduk1 = intent.getStringExtra("UuidProduk")
        var Kondisi1 = intent.getStringExtra("Kondisi")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        btnkoordinat.setOnClickListener {
            Toast.makeText(this, "Latitude: $latitude\nLongitude: $longitude", Toast.LENGTH_LONG)
                .show()
            val intent1 = Intent(this, Detail::class.java)
            intent1.putExtra("Id", id1)
            intent1.putExtra("Uuid", Uuid1)
            intent1.putExtra("NamaRuang", Nama1)
            intent1.putExtra("Tiperuang", TipeRuang1)
            intent1.putExtra("Relay", Relay1)
            intent1.putExtra("NamaProduk", NamaProduk1)
            intent1.putExtra("TipeProduk", TipeProduk1)
            intent1.putExtra("UuidProduk", UuidProduk1)
            intent1.putExtra("Kondisi", Kondisi1)
            intent1.putExtra("latitude", latitude);
            intent1.putExtra("longitude", longitude);
            this.startActivity(intent1)
            finish()
        }
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        map!!.setOnMapClickListener { point -> //myMap.addMarker(new MarkerOptions().position(point).title(point.toString()));

            //The code below demonstrate how to convert between LatLng and Location
            map.clear()
            //Convert LatLng to Location
            val location = Location("Test")
            location.latitude = point.latitude
            latitude = point.latitude
            longitude = point.longitude
            location.longitude = point.longitude

            //Convert Location to LatLng
            val newLatLng = LatLng(location.latitude, location.longitude)
            val markerOptions = MarkerOptions()
                .position(newLatLng)
                .title(newLatLng.toString())
            map.addMarker(markerOptions)
        }
        if (map != null) {
            googleMap = map
            btnposisi_user.setOnClickListener(View.OnClickListener {
                statemap=false
                val locationRequest = LocationRequest.create().apply {
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    interval = 10000
                    fastestInterval = 5000
                    googleMap!!.clear()
                    map.clear()
                }

                locationCallback = object : LocationCallback() {

                    override fun onLocationResult(locationResult: LocationResult) {

                        if (statemap.equals(false)) {
                            map.clear()
                            googleMap!!.clear()
                            for (location in locationResult.locations) {
                                latitude = location.latitude
                                longitude = location.longitude
                                val formattedLatitude = String.format("%.10f", latitude)
                                val formattedLongitude = String.format("%.10f", longitude)
                                Log.d(
                                    "LocationUpdateService",
                                    "Location: ${formattedLatitude}, ${formattedLongitude}"
                                )

                                // Handle the location update, e.g., send it to the UI or log it
                            }

//                            var locationA = Location("point A")
//                            locationA.latitude = latitudefist
//                            locationA.longitude = longitudefirst
//
//                            val locationB = Location("point B")
//                            locationB.latitude = latitude
//                            locationB.longitude = longitude
//
//                            distance = locationA.distanceTo(locationB).toDouble()
                            val userLatLng = LatLng(latitude, longitude)
                            googleMap!!.addMarker(
                                MarkerOptions().position(userLatLng).title("You are here")
                            )
                            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(userLatLng, 15f)
                            //                                    googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            googleMap!!.setMapType(GoogleMap.MAP_TYPE_HYBRID)
                            googleMap!!.moveCamera(cameraUpdate)
                            statemap = true

                        }
                    }
                }

                val permission = android.Manifest.permission.ACCESS_FINE_LOCATION
                if (androidx.core.content.ContextCompat.checkSelfPermission(
                        this,
                        permission
                    ) == android.content.pm.PackageManager.PERMISSION_GRANTED
                ) {
                    fusedLocationClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.getMainLooper()
                    )


                }


//                if (ContextCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION
//                    ) == PackageManager.PERMISSION_GRANTED
//                ) {
//                    val locationTask = fusedLocationClient!!.lastLocation
//                    locationTask.addOnSuccessListener { location ->
//                        if (location != null) {
//                            latitude = location.latitude
//                            longitude = location.longitude
//                            val userLatLng = LatLng(latitude, longitude)
//                            googleMap!!.addMarker(
//                                MarkerOptions().position(userLatLng).title("You are here")
//                            )
//                            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(userLatLng, 15f)
//                            //                                    googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//                            googleMap!!.setMapType(GoogleMap.MAP_TYPE_HYBRID)
//                            googleMap!!.moveCamera(cameraUpdate)
//                        }
//                    }
//                }


            })


        }

    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}