package com.example.recycleviewsqllite

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.UUID

class Tambah : AppCompatActivity() {
    var datatipe: String = ""
    var datarelay: String = ""
    var datajenisproduk:String=""
    var dataitemproduk:String=""
    var datauuiditemproduk:String=""
    var jenisProduk =
        arrayOf("Ventilator","Air Conditioner","Lamp","Other")
    var tipe =
        arrayOf("Livingroom", "Bedroom", "Badroom", "Kichen", "front garden", "Terrace")
    var Rl =
        arrayOf("ID-1", "ID-2", "ID-3", "ID-4", "ID-5", "ID-6")
    var arraynamadevice=ArrayList<String>()
    var arrayuuiddevice=ArrayList<String>()

    lateinit var spinner: Spinner
    lateinit var spinnerJenisProduk: Spinner
    lateinit var spinnerItemProduk: Spinner
    lateinit var spinnerRelay: Spinner
    lateinit var BtnSave: Button
    lateinit var EditTxtNamaDevice: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        spinner = findViewById(R.id.spinnerRuang)
        spinnerRelay = findViewById(R.id.spinnerRelay)
        BtnSave = findViewById(R.id.btnsave)
        spinnerJenisProduk=findViewById(R.id.spinnerJenisProduk)
        spinnerItemProduk=findViewById(R.id.spinnerDevice)
        EditTxtNamaDevice = findViewById(R.id.EditTxtNamaDevice)


        val helperLogin = HelperLogin(this)
        val colom = helperLogin.getAllDatag()

        for (i in 0 until colom.size) {
            val namadevice = colom.get(i).namadevice
            arraynamadevice.add(namadevice)

            val uuiddevice=colom.get(i).uuiddevice
            arrayuuiddevice.add(uuiddevice)
        }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tipe)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
//        BtnSave.setOnClickListener {
//            val helper=Helper(this)
//            val ata=EditTxtNamaDevice.getText().toString()
//            val myUuid = UUID.randomUUID()
//            val myUuidAsString = myUuid.toString()
//            helper.insertData(myUuidAsString,ata, datatipe,datarelay,"0")
//            val rows=helper.getAllData()
////            helper.insertData("464646","TAMAN","HIGH")
//            list.clear()
//            for( i in 0 until rows.size) {
//                val data = Data()
//                val id = rows.get(i).id
//                val uuid = rows.get(i).uuid
//                val namaruang = rows.get(i).namaruang
//                val tiperuang = rows.get(i).tiperuang
//                val relay = rows.get(i).relay
//                val kondisi = rows.get(i).kondisi
//                Log.d("OUTPUT", id)
//                data.id = id
//                data.uuid = uuid
//                data.namaruang = namaruang
//                data.tiperuang = tiperuang
//                data.relay = relay
//                data.kondisi = kondisi
//                list.add(data)
//                adapter_recycle.notifyDataSetChanged()
//            }
//        }

            BtnSave.setOnClickListener {
                val helper = Helper(this)
                val ata = EditTxtNamaDevice.getText().toString()
                val myUuid = UUID.randomUUID()
                val myUuidAsString = myUuid.toString()
                helper.insertData(myUuidAsString, ata, datatipe, datarelay,dataitemproduk,datauuiditemproduk,datajenisproduk,"0")
                helper.getAllTipeRuang()
                if (myUuidAsString != null) {
                    val dat=HelperTimer(this)
                    dat.select(myUuidAsString,"0")
                }
//            helper.insertData("464646","TAMAN","HIGH")
//            list.clear()
//            for( i in 0 until rows.size) {
//                val data = Data()
//                val id = rows.get(i).id
//                val uuid = rows.get(i).uuid
//                val namaruang = rows.get(i).namaruang
//                val tiperuang = rows.get(i).tiperuang
//                val relay = rows.get(i).relay
//                val kondisi = rows.get(i).kondisi
//                Log.d("OUTPUT", id)
//                data.id = id
//                data.uuid = uuid
//                data.namaruang = namaruang
//                data.tiperuang = tiperuang
//                data.relay = relay
//                data.kondisi = kondisi
//                list.add(data)
                adapter_recycle.notifyDataSetChanged()
            }
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(this@Tambah, tipe[position], Toast.LENGTH_SHORT).show()
                    datatipe = tipe[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

            val adapterRelay = ArrayAdapter(this, android.R.layout.simple_spinner_item, Rl)
            adapterRelay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerRelay.adapter = adapterRelay
            spinnerRelay.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(this@Tambah, Rl[position], Toast.LENGTH_SHORT).show()
                    datarelay = Rl[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        val adapterJenisProduk = ArrayAdapter(this, android.R.layout.simple_spinner_item, jenisProduk)
        adapterJenisProduk .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerJenisProduk.adapter = adapterJenisProduk
        spinnerJenisProduk.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@Tambah, jenisProduk[position], Toast.LENGTH_SHORT).show()
                datajenisproduk = jenisProduk[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        val adapterItemProduk = ArrayAdapter(this, android.R.layout.simple_spinner_item, arraynamadevice)
        adapterItemProduk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerItemProduk.adapter = adapterItemProduk
        spinnerItemProduk.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@Tambah, arraynamadevice[position], Toast.LENGTH_SHORT).show()
                dataitemproduk= arraynamadevice[position]
                datauuiditemproduk=arrayuuiddevice[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

//        BtnSave.setOnLongClickListener {
////            val helper=Helper(this@Tambah)
//////            val ata=EditTxtNamaDevice.getText().toString()
//////            val myUuid = UUID.randomUUID()
//////            val myUuidAsString = myUuid.toString()
////            helper.insertData("SAA","7","K")
//
//        }
        }
    }