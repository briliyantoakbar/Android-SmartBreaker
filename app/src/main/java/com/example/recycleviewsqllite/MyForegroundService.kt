package com.example.recycleviewsqllite

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


var latitudefist:Double=0.00
var longitudefirst:Double=0.00
var s:Int=0
var id1:String=""
var Uuid1:String=""
var Nama1:String=""
var TipeRuang1:String=""
var Relay1:String=""
var NamaProduk1:String=""
var TipeProduk1:String=""
var UuidProduk1:String=""
var Kondisi1:String=""
var jr:Int=0
var statedalam:String=""
var stateluar:String=""
var distance:Double=0.00
var state1=true
var state2=true
var stateup200=false
var statedown200=false
private lateinit var locationCallback: LocationCallback
class MyForegroundService:Service() {
    var latitude:Double=0.00
    var longitude:Double=0.00
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val channelId = "LocationServiceChannel"
    private val notificationId = 999

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        createNotificationChannel()
        startForeground(notificationId, createNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        id1 = intent?.getStringExtra("Id").toString()
        Uuid1 = intent?.getStringExtra("Uuid").toString()
        Nama1 = intent?.getStringExtra("NamaRuang").toString()
        TipeRuang1 = intent?.getStringExtra("Tiperuang").toString()
        Relay1 = intent?.getStringExtra("Relay").toString()
        NamaProduk1 = intent?.getStringExtra("NamaProduk").toString()
        TipeProduk1 = intent?.getStringExtra("TipeProduk").toString()
        UuidProduk1 = intent?.getStringExtra("UuidProduk").toString()
        Kondisi1 = intent?.getStringExtra("Kondisi").toString()

        statedalam = intent?.getStringExtra("statedalam").toString()
        stateluar = intent?.getStringExtra("stateluar").toString()
        latitudefist= intent?.getDoubleExtra("latitude",0.00)!!
        longitudefirst= intent?.getDoubleExtra("longitude",0.00)!!
        Log.d("longitudefirst",longitudefirst.toString())
        requestLocationUpdates(this)

        return START_STICKY

    }

    private fun createNotification(): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, pendingIntentFlags)
        val notification = NotificationCompat.Builder(this, "CHANNEL_ID")
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Location Service")
            .setContentText("Running...$distance")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                channelId,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun requestLocationUpdates(context: Context) {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000
            fastestInterval = 5000
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    latitude=location.latitude
                    longitude=location.longitude
                    val formattedLatitude = String.format("%.10f", latitude)
                    val formattedLongitude = String.format("%.10f", longitude)
                    Log.d("LocationUpdateServiceForeground", "Location: ${formattedLatitude}, ${formattedLongitude}")
                    // Handle the location update, e.g., send it to the UI or log it
                }

                var locationA = Location("point A")
                locationA.latitude = latitudefist
                locationA.longitude = longitudefirst

                val locationB = Location("point B")
                locationB.latitude = latitude
                locationB.longitude = longitude

                 distance = locationA.distanceTo(locationB).toDouble()
                jr= distance.toInt()
                sendData(jr.toString())
                Log.d("JARAK:", String.format("%.10f", distance))
                val datalistmaps=ArrayList<DataItem>()
                datalistmaps.clear()
                if(distance<200){
                   statedown200=true
                    stateup200=false
                    state2=true
                    }

                if(distance>200){
                    stateup200=true
                    state1=true
                    statedown200=false
                }
                if(statedown200==true && state1==true){
                    Log.d("dalamrumah:","true")

                    val lit=ArrayList<DataItem>()
                    val helper = Helper(context)
                    helper.UpdateData(
                        id1.toString(),
                        Uuid1.toString(),
                        Nama1.toString(),
                        TipeRuang1.toString(),
                        Relay1.toString(),
                        NamaProduk1.toString(),
                        TipeProduk1.toString(),
                        UuidProduk1.toString(),
                        statedalam)
                    lit.clear()
                    val datamaps=DataItem()
                    datamaps.namaItem =Nama1
                        datamaps.uuidItem =Uuid1
                        datamaps.relayItem =Relay1
                        datamaps.kondisiItem =statedalam
                        datamaps.uuiddeviceItem =UuidProduk1
                    lit.add(datamaps)
                    val post = Post("https://fastapiskripsi-be199a487d88.herokuapp.com/postperintah")
                    post.HTTPPost(context,  lit)
                    state1=false

                }

                if(stateup200==true &&state2==true){
                    Log.d("luarrumah:","true")
                    val lit=ArrayList<DataItem>()
                    val helper = Helper(context)
                    helper.UpdateData(
                        id1.toString(),
                        Uuid1.toString(),
                        Nama1.toString(),
                        TipeRuang1.toString(),
                        Relay1.toString(),
                        NamaProduk1.toString(),
                        TipeProduk1.toString(),
                        UuidProduk1.toString(),
                        stateluar)
                    lit.clear()
                    val datamaps=DataItem()
                    datamaps.namaItem =Nama1
                    datamaps.uuidItem =Uuid1
                    datamaps.relayItem =Relay1
                    datamaps.kondisiItem =stateluar
                    datamaps.uuiddeviceItem =UuidProduk1
                    lit.add(datamaps)
                    val post = Post("https://fastapiskripsi-be199a487d88.herokuapp.com/postperintah")
                    post.HTTPPost(context,  lit)
                    state2=false
                }

            }
        }

        val permission = android.Manifest.permission.ACCESS_FINE_LOCATION
        if (androidx.core.content.ContextCompat.checkSelfPermission(this, permission) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun sendData(data: String) {
        Intent("com.example.myforegroundservice.action.SEND_DATA").also { intent ->
            intent.putExtra("extra_data", data)
            intent.putExtra("extra_dataUUID", Uuid1)
            intent.putExtra("foreground", "1")
            intent.putExtra("dalam", statedalam)
            intent.putExtra("luar", stateluar)
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }
    }
}