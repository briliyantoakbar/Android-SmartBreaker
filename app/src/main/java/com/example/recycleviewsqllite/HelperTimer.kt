package com.example.recycleviewsqllite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class HelperTimer(context: Context): SQLiteOpenHelper(context,"timerV1", null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE IF NOT EXISTS waktuV1 (id INTEGER PRIMARY KEY autoincrement, uuid TEXT, kondisi TEXT)"
        if (db != null) {
            db.execSQL(createTableQuery)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun insertData(Uuid:String,Kondisi:String){
        val table= "waktuV1"
        val db = writableDatabase
        val values = ContentValues().apply {
            val uuid = "uuid"
            put(uuid,Uuid)
            val kondisi="kondisi"
            put(kondisi,Kondisi)
        }
        db.insert(table,null, values)
        db.close()

    }
fun getswitch(Uuid:String):Int{
    val db= readableDatabase
    var id :Int=0
    var c=false
    val cursor: Cursor = db.rawQuery("SELECT * FROM waktuV1 WHERE uuid=?", arrayOf(Uuid))
    if (cursor.moveToNext()) {
        do {
            c=true
            id=cursor.getString(2).toInt()
        } while (cursor.moveToNext())
    }
    if(c.equals(false)){
        insertData(Uuid,"0")
    }
    return id
    }

    fun select(Uuid: String,Kondisi: String) {
        var c=false
        val db = readableDatabase
        var id:String=""
        val cursor: Cursor = db.rawQuery("SELECT * FROM waktuV1 WHERE uuid=?", arrayOf(Uuid))
        if (cursor.moveToNext()) {
            do {
                c=true
               id=cursor.getString(0)
                Log.d("ID SWITCH",id)


            } while (cursor.moveToNext())
        }
        if(c.equals(true)){
            UpdateData(id, Kondisi)
        }
       else if(c.equals(false)){
            insertData(Uuid,Kondisi)
        }

    }

    fun UpdateData(idz:String, kondisi:String){
        val database = this.writableDatabase
        val values = ContentValues()
        var id=idz.toInt()
        values.put("kondisi", kondisi)
        database.update("waktuV1", values, "id=?", arrayOf(id.toString()))
        database.close()
    }
}