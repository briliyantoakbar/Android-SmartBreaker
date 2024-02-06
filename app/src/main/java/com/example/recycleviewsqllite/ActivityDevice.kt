package com.example.recycleviewsqllite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

lateinit var adapterdevice: AdapterActivityDevice
lateinit var recyclerView_list_device: RecyclerView
val output=ArrayList<String>()
val namedevice=ArrayList<String>()
val arrayversit=ArrayList<String>()
val dataKU=ArrayList<OP>()
class ActivityDevice : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)
        output.clear()
        namedevice.clear()
        arrayversit.clear()
        val layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView_list_device= findViewById(R.id.recycleviewlistdevice)
        recyclerView_list_device.layoutManager = layoutManager
        adapterdevice = AdapterActivityDevice(this,dataKU, arrayversit)
        recyclerView_list_device.adapter = adapterdevice
        val datadevice=HelperLogin(this).getAllDatag()
        for (i in 0 until datadevice.size) {
            val email=datadevice.get(i).email
            val namadevice=datadevice.get(i).namadevice
            val uuiddevice=datadevice.get(i).uuiddevice
            Log.d("NAMADEVICEx:",namadevice)
            Log.d("UUIDDEVICEx:",uuiddevice)
            val emaildevice=datadevice.get(i).password
            namedevice.add(namadevice)
            output.add(uuiddevice)

        }
        val postall=Post("https://fastapiskripsi-be199a487d88.herokuapp.com/getallversi")
        var tall = postall.HTTPPostGetAll(this, object : Post.Callback {
            override fun onResponse(arrayversi: ArrayList<String>) {
                // Handle the response here
                for (i in 0 until arrayversi.size) {
                    arrayversit.add(arrayversi.get(i))
                    Log.d("ALLDATAVERSI:", arrayversi.size.toString())
                }

                Log.d("ALLDATAVERSI:", arrayversit.size.toString())
            }
        })

        val post=Post("https://fastapiskripsi-be199a487d88.herokuapp.com/getversi")
        dataKU.clear()
            var t = post.HTTPPostVersi(this, output, object : Post.ResponseCallback {
                override fun onResponse(sis: ArrayList<String>) {
                    // Handle the response here
                    for (i in 0 until sis.size) {
                        val op = OP()
                        op.nama = namedevice.get(i)
                        op.uuid= output.get(i)
                        op.versi = sis.get(i)
                        dataKU.add(op)
                        adapterdevice.notifyDataSetChanged()
                    }
                    Log.d("ISI:", sis.size.toString())
                }
            })






    }
}