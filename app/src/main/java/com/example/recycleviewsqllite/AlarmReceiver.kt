package com.example.recycleviewsqllite

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // You can also retrieve extra data from the Intent
        val alarmId = intent.getIntExtra("alarm_id", 0)
        val idItem = intent.getStringExtra("idItem")
        val namaItem = intent.getStringExtra("namaItem")
        val uuidItem = intent.getStringExtra("uuidItem")
        val tipeItem = intent.getStringExtra("tipeItem")
        val relayItem = intent.getStringExtra("relayItem")
        val namadeviceItem = intent.getStringExtra("namadeviceItem")
        val uuiddeviceItem = intent.getStringExtra("uuiddeviceItem")
        val tipedeviceItem = intent.getStringExtra("tipedeviceItem")
        val kondisi = intent.getStringExtra("kondisiItem")
        val switch = intent.getStringExtra("switch")
        val value = intent.getStringExtra("value")
        Log.d("OKON alarm:",kondisi.toString())
        val post = Post("https://fastapiskripsi-be199a487d88.herokuapp.com/postperintah")
        val list=ArrayList<DataItem>()
        val datadetail=DataItem()
        datadetail.namaItem = namaItem.toString()
        datadetail.uuidItem = uuidItem.toString()
        datadetail.uuidItem = uuidItem.toString()
        datadetail.relayItem = relayItem.toString()
        datadetail.kondisiItem = kondisi.toString()
        datadetail.namadeviceItem = namadeviceItem.toString()
        datadetail.tipedeviceItem = tipeItem.toString()
        datadetail.uuiddeviceItem = uuiddeviceItem.toString()
        list.add(datadetail)
        val helper = Helper(context)
        helper.UpdateData(
            idItem.toString(),
            uuidItem.toString(),
            namaItem.toString(),
            tipeItem.toString(),
            relayItem.toString(),
            namadeviceItem.toString(),
            tipedeviceItem.toString(),
            uuiddeviceItem.toString(),
            kondisi.toString()

        )

        if(switch.equals("a")){
            val helperTimer=HelperTimer(context)
            helperTimer.select(uuidItem.toString(),"0")
        }
        if(switch.equals("b")){
            val helperTimerOFF=HelpertimerOFF(context)
            helperTimerOFF.select(uuidItem.toString(),"0")
        }
        post.HTTPPost(context, list)
        Toast.makeText(context, "Alarm $alarmId Ringing!", Toast.LENGTH_SHORT).show()
        // Implement further actions like notification
    }
}