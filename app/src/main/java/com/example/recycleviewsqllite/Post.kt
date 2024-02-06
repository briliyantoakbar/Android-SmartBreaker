package com.example.recycleviewsqllite

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class Post(var getURL:String) {
    fun HTTPPost(context: Context,lists: ArrayList<DataItem>){
        val jsonParams = JSONObject()
        val array=JSONArray()
        for (i in 0 until lists.size) {
            val UUIDDevice = lists.get(i).uuiddeviceItem
            val NAMAitem = lists.get(i).namaItem
            val UUIDItem = lists.get(i).uuidItem
            val RELAYItem = lists.get(i).relayItem
            val KONDISIItem = lists.get(i).kondisiItem
            Log.d("POSTUUIDDEVICE:","HAI")
            Log.d("POSTUUIDDEVICE:",UUIDDevice)
            Log.d("POSTUUIDITEM:",UUIDItem)
            array.put(0, UUIDDevice)
            array.put(1, NAMAitem)
            array.put(2, UUIDItem)
            array.put(3, RELAYItem)
            array.put(4, KONDISIItem)
        }
        jsonParams.put("name", array)
        val request = JsonObjectRequest(
            Request.Method.POST,  // Using a variable for the domain is great for testing
            getURL,
            jsonParams,
            { response ->
                try {
                    val jsonObj = JSONObject(response.toString())
                    val nama=jsonObj.getString("oke")
                    Toast.makeText(context, nama, Toast.LENGTH_SHORT).show()
                } catch (e: JSONException) {
                    throw RuntimeException(e)
                }
            }
        ) {
            // Handle the error
        }

        Volley.newRequestQueue(context).add<JSONObject>(request)

    }

    fun HTTPPostUpdate(context: Context,uuid:String, versi:String){
        val jsonParams = JSONObject()
        val array=JSONArray()
        array.put(0,uuid)
        array.put(1,versi)
        jsonParams.put("name", array)
        val request = JsonObjectRequest(
            Request.Method.POST,  // Using a variable for the domain is great for testing
            getURL,
            jsonParams,
            { response ->
                try {
                    val jsonObj = JSONObject(response.toString())
                    Log.d("RESPONSE:",jsonObj.getString("DATA"))
                } catch (e: JSONException) {
                    throw RuntimeException(e)
                }
            }
        ) {
            // Handle the error
        }
        Volley.newRequestQueue(context).add<JSONObject>(request)
    }

    interface ResponseCallback {
        fun onResponse(sis: ArrayList<String>)
    }
    fun HTTPPostVersi(context: Context, lists:ArrayList<String>,callback: ResponseCallback){

        val jsonParams = JSONObject()
        val array=JSONArray()
       val sis=ArrayList<String>()
        val op=OP()
        var count=0
//        for (datalist in lists){
//            count++
//            array.put(count,lists[0])
//            array.put(1,lists[1])
//            Log.d("COUNT:",datalist)
//        }
//        jsonParams.put("name", array)
        for ((index, datat) in lists.withIndex()) {
            // Using index + 1 as the key, and data as the value
            array.put((index + 1), datat)
            Log.d("DATA:", datat.toString())
        }
        jsonParams.put("name", array)

        val request = JsonObjectRequest(
            Request.Method.POST,  // Using a variable for the domain is great for testing
            getURL,
            jsonParams,
            { response ->
                try {
                    val jsonObj = JSONObject(response.toString())
                    val nama=jsonObj.getJSONArray("aku")
                    for (i in 0 until nama.length()) {
                        val ce=nama[i].toString()
                        sis.add(ce)
                        Log.d("SERVER",sis.size.toString())
                        Log.d("SERVER",nama[i].toString())

                    }
                    callback.onResponse(sis)
                } catch (e: JSONException) {
                    throw RuntimeException(e)
                }
            }
        ) {
            // Handle the error
        }

        Volley.newRequestQueue(context).add<JSONObject>(request)


    }

    interface Callback {
        fun onResponse(arrayversi: ArrayList<String>)
    }
    fun HTTPPostGetAll(context: Context,allback:Callback){

        val jsonParams = JSONObject()
        val arrayversi=ArrayList<String>()
        jsonParams.put("name", "cekdata")
        val request = JsonObjectRequest(
            Request.Method.POST,  // Using a variable for the domain is great for testing
            getURL,
            jsonParams,
            { response ->
                try {
                    val jsonObj = JSONObject(response.toString())
                    val nama=jsonObj.getJSONArray("data")
                    for (i in 0 until nama.length()) {
                        val ce=nama[i].toString()
                        arrayversi.add(ce)
                        Log.d("SERVERKU",nama[i].toString())

                    }
                    allback.onResponse(arrayversi)
                } catch (e: JSONException) {
                    throw RuntimeException(e)
                }
            }
        ) {
            // Handle the error
        }

        Volley.newRequestQueue(context).add<JSONObject>(request)


    }


    }
