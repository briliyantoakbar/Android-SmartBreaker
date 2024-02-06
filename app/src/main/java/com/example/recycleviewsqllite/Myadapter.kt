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
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class Myadapter(val data:ArrayList<Data>,val context:Context): RecyclerView.Adapter<Myadapter.MyHolder>(){
    class MyHolder(itemview: View):  RecyclerView.ViewHolder(itemview){
//        val textID: TextView =itemview.findViewById(R.id.txtID)
//        val textNAMARUANG: TextView =itemview.findViewById(R.id.txtNAMARUANG)
//        val textUUID: TextView =itemview.findViewById(R.id.txtUUID)
//        val textKONDISI: TextView =itemview.findViewById(R.id.txtKONDISI)
//        val image:ImageView=itemview.findViewById(R.id.titik)
//        val gambar:ShapeableImageView=itemview.findViewById(R.id.images)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val item= LayoutInflater.from(parent.context).inflate(R.layout.recycleview_horizontal, parent,false)
        return MyHolder(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
//        val ID=data.get(position).id
//        val UUID=data.get(position).uuid
//        val NAMARUANG=data.get(position).namaruang
//        val TIPERUANG=data.get(position).tiperuang
//        val RELAY=data.get(position).relay
//        val KONDISI=data.get(position).kondisi
//        holder.textID.setText(ID)
//        holder.textUUID.setText(UUID)
//        holder.textNAMARUANG.setText(NAMARUANG)
//        holder.textKONDISI.setText(KONDISI)
//        holder.gambar.setOnClickListener {
//            //aksi untuk menuju kehalaman lihat_data_2
//            val intent1 = Intent(context,Detail::class.java)
//            intent1.putExtra("Nama", NAMARUANG)
//            intent1.putExtra("Id", ID)
//            intent1.putExtra("Uuid",UUID)
//            intent1.putExtra("Tiperuang",TIPERUANG)
//            intent1.putExtra("Relay",RELAY)
//            intent1.putExtra("Kondisi", KONDISI)
//            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            context.startActivity(intent1)
//        }
//        holder.image.setOnClickListener {
//            popupMenus(it,position)
//        }

    }


    private fun popupMenus(v:View, position:Int) {
        val popupMenus = PopupMenu(context,v)
        popupMenus.inflate(R.menu.menu)
        popupMenus.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_edit->{
                            Toast.makeText(context,position.toString(),Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_delete->{
                    val helper=Helper(context)
                    val i=position
                    val id= list.get(i).id
                    helper.DeleteData(id.toInt())
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
                    Toast.makeText(context,"User",Toast.LENGTH_SHORT).show()
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