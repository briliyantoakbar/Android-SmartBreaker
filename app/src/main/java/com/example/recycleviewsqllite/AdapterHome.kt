package com.example.recycleviewsqllite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import kotlin.concurrent.thread

val datagambar= arrayOf(R.drawable.livingroom,R.drawable.bedroom,R.drawable.bathroom,R.drawable.kichen,R.drawable.frontgarden,R.drawable.terrace)
class AdapterHome(val data:ArrayList<String>,val datahorizontal:ArrayList<String>,val context: Context): RecyclerView.Adapter<AdapterHome.MyHolder>(){
    class MyHolder(itemview: View):  RecyclerView.ViewHolder(itemview){
        val ruang: TextView =itemview.findViewById(R.id.txthomenamaruang)
        val jumlah: TextView =itemview.findViewById(R.id.txthomejumlah)
        val gambar: ShapeableImageView =itemview.findViewById(R.id.images)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyHolder{
        val item= LayoutInflater.from(parent.context).inflate(R.layout.recycleview_home, parent,false)
        return AdapterHome.MyHolder(item)

    }

    override fun getItemCount(): Int {
        return datahorizontal.size
    }

    override fun onBindViewHolder(holder:MyHolder, position: Int) {
        val RUANG=data.get(position)
        val JUMLAH=datahorizontal.get(position)
        holder.ruang.setText(RUANG)
        holder.jumlah.setText(JUMLAH +" Devices")
        Glide.with(context)
            .load(datagambar[position])
            .into(holder.gambar)

        holder.gambar.setOnClickListener {
            //aksi untuk menuju kehalaman lihat_data_2
            val intent1 = Intent(context,Detail_Recycle::class.java)
            intent1.putExtra("RUANG",RUANG)
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent1)
        }

    }

}


