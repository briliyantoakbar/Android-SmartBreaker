package com.example.recycleviewsqllite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

val dataListDevice = ArrayList<DataDevice>()

class HelperLogin(context: Context): SQLiteOpenHelper(context,"LoginDeviceV1", null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE IF NOT EXISTS DeviceV1(id INTEGER PRIMARY KEY autoincrement, email TEXT, password TEXT,namadevice TEXT,uuiddevice TEXT)"
        if (db != null) {
            db.execSQL(createTableQuery)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun insertDatag(Email:String,Password:String,Namadevice:String, Uuiddevice:String){
        val table= "DeviceV1"
        val db = writableDatabase
        var c:Boolean=false
        var id:String
        var email:String
        var password:String
        var namadevice:String
        val cursor: Cursor =db.rawQuery("SELECT * FROM DeviceV1 WHERE namadevice=?", arrayOf(Namadevice))
        if (cursor.moveToNext()) {
            do {
                c=true
                id=cursor.getString(0)
                email = cursor.getString(1)
                password = cursor.getString(2)
                namadevice = cursor.getString(3)

            }while (cursor.moveToNext())
            val database = this.writableDatabase
            val values = ContentValues()
            values.put("id", id)
            values.put("email",email)
            values.put("password",password)
            values.put("namadevice",namadevice)
            values.put("uuiddevice",Uuiddevice)
            db.update("DeviceV1", values, "id=?", arrayOf(id.toString()))
            db.close()
            }
        if(c.equals(false)){
            val values = ContentValues().apply {
            val email = "email"
            put(email,Email)
            val password="password"
            put(password,Password)
            val namadevice="namadevice"
            put(namadevice,Namadevice)
            val uuiddevice="uuiddevice"
            put(uuiddevice,Uuiddevice)
        }
        db.insert(table,null, values)
        db.close()
        }
    }


    fun getAllDatag(): ArrayList<DataDevice> {
        dataListDevice.clear()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM DeviceV1", null)
        if (cursor.moveToNext()) {
            do {
                val data=DataDevice()
                val id = cursor.getString(0)
                val email = cursor.getString(1)
                val password = cursor.getString(2)
                val namadevice = cursor.getString(3)
                Log.d("NAMADEVICE:",namadevice)
                val uuiddevice = cursor.getString(4)
                data.email=email
                data.password=password
                data.namadevice=namadevice
                data.uuiddevice=uuiddevice
                dataListDevice.add(data)
            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return dataListDevice
    }
}