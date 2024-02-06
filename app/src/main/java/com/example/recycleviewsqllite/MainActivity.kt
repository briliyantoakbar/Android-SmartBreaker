package com.example.recycleviewsqllite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

lateinit var adapter_recycle: AdapterHome
lateinit var recyclerView: RecyclerView
lateinit var btnkirim: FloatingActionButton
lateinit var btnaddDevice:ImageView
lateinit var menuIcon:ImageView
lateinit var drawerLayout: DrawerLayout
var data = Data()
var list = ArrayList<Data>()
val dataTipeRuang = ArrayList<String>()
val user = ArrayList<String>()

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnkirim = findViewById(R.id.btnKirim)
        btnaddDevice=findViewById(R.id.addDevice)
        drawerLayout = findViewById(R.id.drawerLayout)
        menuIcon = findViewById(R.id.menu_icon)

        menuIcon.setOnClickListener {
            // Open the drawer
            drawerLayout.openDrawer(GravityCompat.START)
        }
        btnaddDevice.setOnClickListener {
            val intent1 = Intent(this@MainActivity, TambahDevice::class.java)
            this.startActivity(intent1)
        }
        val navigationView: NavigationView = findViewById(R.id.navView)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selection
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            true
        }
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Home -> {
                    Toast.makeText(this@MainActivity, "First Item Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.About -> {
                    Toast.makeText(this@MainActivity, "Second Item Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.Firmware -> {
                    val intent2 = Intent(this@MainActivity, ActivityDevice::class.java)
                    this.startActivity(intent2)
                }
            }
            true
        }
        val dialogitem = arrayOf("a", "b")
        var helper = Helper(this)
//        data.ID="2"
//        data.NAMA="Akbar"
//        list.add(data)
//        data.ID="2"
//        data.NAMA="Akbar"
//        list.add(data)
//        data.ID="2"
//        data.NAMA="Akbar"
//        list.add(data)
//        helper.insertData("464646","TAMAN","HIGH")
        user.add("Livingroom")
        user.add("Bedroom")
        user.add("Badroom")
        user.add("Kichen")
        user.add("front garden")
        user.add("Terrace")
        val datatipe = helper.getAllTipeRuang()

        val layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)

        recyclerView = findViewById(R.id.recycleview)
        recyclerView.layoutManager = layoutManager
        adapter_recycle = AdapterHome(user, datatipe, this)
        recyclerView.adapter = adapter_recycle
        for (i in 0 until datatipe.size) {
            val c=datatipe.get(i)
            dataTipeRuang.add(c)
            Log.d("c1", dataTipeRuang.get(i))
            adapter_recycle.notifyDataSetChanged()
        }

        //        val layoutManager =
//            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
//        val layoutManager =
//            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
//        recyclerView = findViewById(R.id.recycleview)
//        recyclerView.layoutManager = layoutManager
//        adapter_recycle = Myadapter(list, applicationContext)
//        recyclerView.adapter = adapter_recycle
//        val rows = helper.getAllData()
//        val get=helper.getAllTipeRuang()
////            helper.insertData("464646","TAMAN","HIGH")
//        list.clear()
//        for (i in 0 until rows.size) {
//            val data = Data()
//            val id = rows.get(i).id
//            val uuid = rows.get(i).uuid
//            val namaruang = rows.get(i).namaruang
//            val tiperuang=rows.get(i).tiperuang
//            val relay=rows.get(i).relay
//            val kondisi = rows.get(i).kondisi
//            Log.d("OUTPUT", id)
//            data.id = id
//            data.uuid = uuid
//            data.namaruang = namaruang
//            data.tiperuang=tiperuang
//            data.relay=relay
//            data.kondisi = kondisi
//            list.add(data)
//            adapter_recycle.notifyDataSetChanged()
//        }


        btnkirim.setOnClickListener {
            val intent1 = Intent(this@MainActivity, Tambah::class.java)
            this.startActivity(intent1)
//            val rows = helper.getAllData()
////            helper.insertData("464646","TAMAN","HIGH")
//            list.clear()
//            for (i in 0 until rows.size) {
//                val data = Data()
//                val id = rows.get(i).id
//                val uuid = rows.get(i).uuid
//                val namaruang = rows.get(i).namaruang
//                val kondisi = rows.get(i).kondisi
//                Log.d("OUTPUT", id)
//                data.id = id
//                data.uuid = uuid
//                data.namaruang = namaruang
//                data.kondisi = kondisi
//                list.add(data)
//                adapter_recycle.notifyDataSetChanged()
        }
//            data.id="2"
//            data.uuid="62622626"
//            data.namaruang="tamu"
//            data.kondisi="ON"

    }

}