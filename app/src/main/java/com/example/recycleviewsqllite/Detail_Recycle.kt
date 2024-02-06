package com.example.recycleviewsqllite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
lateinit var myadapter: AdapterDetailRecycle
lateinit var recyclerView_detal: RecyclerView
var datadetail = DataItem()
var listdetail = ArrayList<DataItem>()
class Detail_Recycle : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_recycle)
        val layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView_detal= findViewById(R.id.recycleviewDetail)
        recyclerView_detal.layoutManager = layoutManager
        myadapter = AdapterDetailRecycle(listdetail,this)
        recyclerView_detal.adapter = myadapter
        var  ruangIntent = intent.getStringExtra("RUANG").toString()
        val get=Helper(this).ItemTipeRuang(ruangIntent)
        listdetail.clear()
        for (i in 0 until get.size) {
            var datadetail = DataItem()
            val id = get.get(i).idItem
            val uuid = get.get(i).uuidItem
            val namaruang = get.get(i).namaItem
            val tiperuang = get.get(i).tipeItem
            val relay = get.get(i).relayItem
            val namaproduk=get.get(i).namadeviceItem
            val tipeproduk=get.get(i).tipedeviceItem
            val uuidproduk=get.get(i).uuiddeviceItem
            val kondisi = get.get(i).kondisiItem
            Log.d("ARRAY:", kondisi)
            datadetail.idItem=id
            datadetail.namaItem=namaruang
            datadetail.uuidItem=uuid
            datadetail.tipeItem=tiperuang
            datadetail.relayItem=relay
            datadetail.kondisiItem=kondisi
            datadetail.namadeviceItem=namaproduk
            datadetail.tipedeviceItem=tipeproduk
            datadetail.uuiddeviceItem=uuidproduk
            listdetail.add(datadetail)
            myadapter.notifyDataSetChanged()
        }


    }
}