package com.example.recycleviewsqllite

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView


class AdapterDetailRecycle(val data:ArrayList<DataItem>,val context: Context):RecyclerView.Adapter<AdapterDetailRecycle.Myholder>() {
    class Myholder(itemview: View):  RecyclerView.ViewHolder(itemview){
        val textNAMARUANG: TextView =itemview.findViewById(R.id.txtNAMARUANG)
        val textKONDISI: ImageView=itemview.findViewById(R.id.txtKONDISI)
        val textNAMAITEM: TextView =itemview.findViewById(R.id.NAMAITEM)
        val textTIPEPRODUK: TextView =itemview.findViewById(R.id.TIPEPRODUK)
        val textRELAY: TextView =itemview.findViewById(R.id.txtRELAY)
        val image:ImageView=itemview.findViewById(R.id.titik)
        val gambar:ShapeableImageView=itemview.findViewById(R.id.images)
        val namadevice:TextView=itemview.findViewById(R.id.txtNAMADEVICE)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myholder {
        val item= LayoutInflater.from(parent.context).inflate(R.layout.recycleview_horizontal, parent,false)
        return Myholder(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Myholder, position: Int) {
        val ID=data.get(position).idItem
        val UUID=data.get(position).uuidItem
        val NAMARUANG=data.get(position).namaItem //lampu o1
        val TIPERUANG=data.get(position).tipeItem //livingroom
        val RELAY=data.get(position).relayItem
        val NAMAPRODUK=data.get(position).namadeviceItem //esp01
        val TIPEPRODUK=data.get(position).tipedeviceItem //tv
        val UUIDPRODUK=data.get(position).uuiddeviceItem
        val KONDISI=data.get(position).kondisiItem
        holder.textRELAY.setText(RELAY)
        holder.textNAMAITEM.setText(TIPERUANG)
        holder.textNAMARUANG.setText(NAMARUANG)
        holder.textTIPEPRODUK.setText(TIPEPRODUK)
        holder.namadevice.setText(NAMAPRODUK)
        if(TIPERUANG.equals("Livingroom")){
            holder.gambar.setImageResource(R.drawable.livingroom)
        }
        else if (TIPERUANG.equals("Bedroom")){
            holder.gambar.setImageResource(R.drawable.bedroom)
        }
        else if (TIPERUANG.equals("Badroom")){
            holder.gambar.setImageResource(R.drawable.bathroom)

        }
        else if (TIPERUANG.equals("Kichen")){
            holder.gambar.setImageResource(R.drawable.kichen)

        }
        else if (TIPERUANG.equals("front garden")){
            holder.gambar.setImageResource(R.drawable.frontgarden)

        }
        else if (TIPERUANG.equals("Terrace")){
            holder.gambar.setImageResource(R.drawable.terrace)

        }
        if(KONDISI.equals("0")) {
            holder.textKONDISI.setImageResource(R.drawable.baseline_circle_24)
        }
        else{
            holder.textKONDISI.setImageResource(R.drawable.baseline_circle_green_24)
        }
                holder.image.setOnClickListener {
            popupMenus(it,position)
        }

        holder.gambar.setOnClickListener {
            val intent1 = Intent(context,Detail::class.java)
            intent1.putExtra("Id", ID)
            intent1.putExtra("Uuid",UUID)
            intent1.putExtra("NamaRuang", NAMARUANG)
            intent1.putExtra("Tiperuang",TIPERUANG)
            intent1.putExtra("Relay",RELAY)
            intent1.putExtra("NamaProduk",NAMAPRODUK)
            intent1.putExtra("TipeProduk",TIPEPRODUK)
            intent1.putExtra("UuidProduk",UUIDPRODUK)
            intent1.putExtra("Kondisi", KONDISI)
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent1)
        }
//        holder.textKONDISI.setText(KONDISI)

    }


    private fun popupMenus(v:View, position:Int) {
        val popupMenus = PopupMenu(context,v)
        popupMenus.inflate(R.menu.menu)
        popupMenus.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_edit->{
                    Toast.makeText(context,position.toString(), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_delete->{
                    val helper=Helper(context)
                    val i=position
                    val id= listdetail.get(i).idItem
                    helper.DeleteData(id.toInt())
                    val get=helper.ItemTipeRuang(listdetail.get(i).tipeItem)
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
                        datadetail.namadeviceItem=namaproduk
                        datadetail.tipedeviceItem=tipeproduk
                        datadetail.uuiddeviceItem=uuidproduk
                        datadetail.kondisiItem=kondisi
                        listdetail.add(datadetail)
                        myadapter.notifyDataSetChanged()

                    }
                    val datatipe=helper.getAllTipeRuang()
                    dataTipeRuang.clear()
                    for (i in 0 until datatipe.size) {
                        val c=datatipe.get(i)
                        dataTipeRuang.add(c)
                        adapter_recycle.notifyDataSetChanged()
                    }
//                    val rows=helper.getAllData()
////            helper.insertData("464646","TAMAN","HIGH")
//                    list.clear()
//                    for( i in 0 until rows.size){
//                        val data=Data()
//                        val id=rows.get(i).id
//                        val uuid=rows.get(i).uuid
//                        val namaruang=rows.get(i).namaruang
//                        val tiperuang=rows.get(i).tiperuang
//                        val relay=rows.get(i).relay
//                        val kondisi=rows.get(i).kondisi
//                        Log.d("OUTPUT", id)
//                        data.id=id
//                        data.uuid=uuid
//                        data.namaruang=namaruang
//                        data.tiperuang=tiperuang
//                        data.relay=relay
//                        data.kondisi=kondisi
//                        list.add(data)
//                        adapter_recycle.notifyDataSetChanged()
//                    }
                    Toast.makeText(context,"User"+id, Toast.LENGTH_SHORT).show()
                    true
                }
                else-> true
            }

        }
        popupMenus.show()
        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val menu = popup.get(popupMenus)
        menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
            .invoke(menu,true)
    }

}