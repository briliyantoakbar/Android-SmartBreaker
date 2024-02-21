package com.example.recycleviewsqllite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast



lateinit var listversinow: Spinner
lateinit var btnpost:Button
lateinit var nama:TextView
var pilihversifirmware:String = ""
var uuid:String=""
var namadevice:String=""
class UpdateVersion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_version)
        listversinow = findViewById(R.id.sp)
        btnpost=findViewById(R.id.btnupdate)
        nama=findViewById(R.id.namev)
        var extra = getIntent().getBundleExtra("extra");
        val arrayversit= extra?.getSerializable("objects") as ArrayList<String>
        uuid= extra?.getSerializable("uuid") as String
        namadevice= extra?.getSerializable("nama") as String
        nama.setText(namadevice)
        val adapterJenisPr= ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayversit)
        adapterJenisPr .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        listversinow.adapter = adapterJenisPr
        listversinow.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                pilihversifirmware = arrayversit[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        btnpost.setOnClickListener {
            val kirim=Post("https://fastapiskripsi-be199a487d88.herokuapp.com/edit").
            HTTPPostUpdate(this,uuid,pilihversifirmware)

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
}