package com.example.recycleviewsqllite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


class AdapterActivityDevice(
    val context: Context,
    val data: ArrayList<OP>,
    val arrayversit: ArrayList<String>
) : RecyclerView.Adapter<AdapterActivityDevice.MyHolder>() {
    class MyHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val devicenama: TextView = itemview.findViewById(R.id.DeviceNama)
        val namaversi: TextView = itemview.findViewById(R.id.namaversi)
        val say: LinearLayout = itemview.findViewById(R.id.say)
        val card: CardView = itemview.findViewById(R.id.oke)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.listdevice, parent, false)
        return AdapterActivityDevice.MyHolder(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var c = false
        val nama = data.get(position).nama
        val versi = data.get(position).versi
        val uuid=data.get(position).uuid
        holder.devicenama.setText(nama)
        holder.namaversi.setText(versi)
        val extra = Bundle()
        holder.card.setOnClickListener {
            Toast.makeText(context, uuid, Toast.LENGTH_SHORT).show()
            extra.putSerializable("objects", arrayversit);
            extra.putSerializable("uuid", uuid);
            extra.putSerializable("nama", nama);
            val intent2 = Intent(context, UpdateVersion::class.java)
            intent2.putExtra("extra", extra);
            context.startActivity(intent2)

        }
    }
}