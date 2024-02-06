package com.example.recycleviewsqllite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

val dataList = ArrayList<Data>()
val dataItem = ArrayList<DataItem>()
val arraytiperuang=ArrayList<String>()
class Helper(context: Context) : SQLiteOpenHelper(context,"SmartBreakerV1", null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE IF NOT EXISTS BreakerV1(id INTEGER PRIMARY KEY autoincrement, uuid TEXT, namaruang TEXT,tiperuang TEXT,relay TEXT,namadevice TEXT,uuiddevice TEXT,tipedevice TEXT, kondisi TEXT)"
        if (db != null) {
            db.execSQL(createTableQuery)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    fun insertData(Uuid:String,Namaruang:String,Tiperuang:String,Relay:String,Namadevice:String,Uuiddevice:String,Tipedevice:String, Kondisi:String ){
        val table= "BreakerV1"
        val db = writableDatabase
        val values = ContentValues().apply {
            val uuid = "uuid"
            put(uuid,Uuid)
            val namaruang="namaruang"
            put(namaruang,Namaruang)
            val tiperuang="tiperuang"
            put(tiperuang,Tiperuang)
            val relay="relay"
            put(relay,Relay)
            val namadevice="namadevice"
            put(namadevice,Namadevice)
            val uuiddevice="uuiddevice"
            put(uuiddevice,Uuiddevice)
            val tipedevice="tipedevice"
            put(tipedevice,Tipedevice)
            val kondisi="kondisi"
            put(kondisi,Kondisi)
        }
        db.insert(table,null, values)
        db.close()

    }
    fun getAllTipeRuang(): ArrayList<String>{
        arraytiperuang.clear()
        val db = readableDatabase

        val livingroom:String="Livingroom"
        val cursor1: Cursor = db.rawQuery("SELECT * FROM BreakerV1 WHERE tiperuang=?",
            arrayOf(livingroom)
        )
        Log.d("JUMLAH:",cursor1.count.toString())
        val Livingroom=cursor1.count.toString()
        arraytiperuang.add(cursor1.count.toString())
        var Bedroom:String="Bedroom"
        val cursor2: Cursor = db.rawQuery("SELECT * FROM BreakerV1 WHERE tiperuang=?",
            arrayOf(Bedroom)
        )
        arraytiperuang.add(cursor2.count.toString())
        val Badroom:String="Badroom"
        val cursor3: Cursor = db.rawQuery("SELECT * FROM BreakerV1 WHERE tiperuang=?",
            arrayOf(Badroom)
        )
        arraytiperuang.add(cursor3.count.toString())
        val Kichen:String="Kichen"
        val cursor4: Cursor = db.rawQuery("SELECT * FROM BreakerV1 WHERE tiperuang=?",
            arrayOf(Kichen)
        )
        arraytiperuang.add(cursor4.count.toString())
        val front_garden:String="front garden"
        val cursor5: Cursor = db.rawQuery("SELECT * FROM BreakerV1 WHERE tiperuang=?",
            arrayOf(front_garden)
        )
        arraytiperuang.add(cursor5.count.toString())
        val Terrace:String="Terrace"
        val cursor6: Cursor = db.rawQuery("SELECT * FROM BreakerV1 WHERE tiperuang=?",
            arrayOf(Terrace)
        )
        arraytiperuang.add(cursor6.count.toString())
        return arraytiperuang
    }

    fun ItemTipeRuang(namaruang: String): ArrayList<DataItem>{
        val db = readableDatabase
        dataItem.clear()
        val cursor1: Cursor = db.rawQuery("SELECT * FROM BreakerV1 WHERE tiperuang=?",
            arrayOf(namaruang)
        )
        if (cursor1.moveToNext()) {
            do {
                val dataitem=DataItem()
                val id = cursor1.getString(0)
                val uuid = cursor1.getString(1)
                val namaruang = cursor1.getString(2)
                val tiperuang = cursor1.getString(3)
                val relay = cursor1.getString(4)
                val namadevice=cursor1.getString(5)
                val uuiddevice=cursor1.getString(6)
                val tipedevice=cursor1.getString(7)
                val kondisi = cursor1.getString(8)
                dataitem.idItem= id
                dataitem.uuidItem= uuid
                dataitem.namaItem= namaruang
                dataitem.tipeItem=tiperuang
                dataitem.relayItem=relay
                dataitem.namadeviceItem=namadevice
                dataitem.uuiddeviceItem=uuiddevice
                dataitem.tipedeviceItem=tipedevice
                dataitem.kondisiItem= kondisi
                dataItem.add(dataitem)

            }while (cursor1.moveToNext())
        }

        cursor1.close()
        db.close()

        return dataItem
    }

//    fun getAllData(): ArrayList<Data> {
//        dataList.clear()
//        val db = readableDatabase
//        val cursor: Cursor = db.rawQuery("SELECT * FROM BreakerV1", null)
//        if (cursor.moveToNext()) {
//            do {
//                val data=Data()
//                val id = cursor.getString(0)
//                val uuid = cursor.getString(1)
//                val namaruang = cursor.getString(2)
//                val tiperuang = cursor.getString(3)
//                val relay = cursor.getString(4)
//                val kondisi = cursor.getString(5)
//                data.id = id
//                data.uuid = uuid
//                data.namaruang = namaruang
//                data.tiperuang=tiperuang
//                data.relay=relay
//                data.kondisi = kondisi
//                dataList.add(data)
//                data.tampil()
//
//            }while (cursor.moveToNext())
//        }
//
//        cursor.close()
//        db.close()
//
//        return dataList
//    }

    fun DeleteData(id:Int){
        val db = this.writableDatabase
       val COLUMN_ID = "id"
        val QUERY = "DELETE FROM BreakerV1 WHERE id = ?"
        db.execSQL(QUERY, arrayOf(id.toString()))
        db.close()
    }
    fun UpdateData(idz:String, uuid:String, namaruang:String,tiperuang:String,relay:String,namadevice:String,tipedevice:String, uuiddevice:String,kondisi:String){
        val database = this.writableDatabase
        val values = ContentValues()
        values.put("uuid", uuid)
        var id=idz.toInt()
        values.put("namaruang", namaruang)
        values.put("tiperuang",tiperuang)
        values.put("relay",relay)
        values.put("namadevice",namadevice)
        values.put("tipedevice",tipedevice)
        values.put("uuiddevice",uuiddevice)
        values.put("kondisi", kondisi)
        database.update("BreakerV1", values, "id=?", arrayOf(id.toString()))
        database.close()
    }

}