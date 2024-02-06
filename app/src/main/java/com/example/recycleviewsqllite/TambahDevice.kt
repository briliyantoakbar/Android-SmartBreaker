package com.example.recycleviewsqllite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.UUID

lateinit var edtTxtEmail:EditText
lateinit var edtTxtPassword:EditText
lateinit var edtTxtPasswordSSID:EditText
lateinit var edtTxtSSID:EditText
lateinit var edtTxtIP:EditText
lateinit var edtTxtNamaDevice:EditText
lateinit var btnsimpan:Button
private lateinit var clientSocket: DatagramSocket
class TambahDevice : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_device)
        edtTxtEmail=findViewById(R.id.EditTxtEmail)
        edtTxtPassword=findViewById(R.id.EditTxtPassword)
        edtTxtPasswordSSID=findViewById(R.id.EditTxtPasswordSSID)
        edtTxtSSID=findViewById(R.id.EditTxtSSID)
        edtTxtIP=findViewById(R.id.EditTxtIP)
        edtTxtNamaDevice=findViewById(R.id.EditTxtNamaDevice)
        btnsimpan=findViewById(R.id.btnsimpan)

        val helperLogin=HelperLogin(this)
        val myUuid = UUID.randomUUID()
        val myUuidAsString = myUuid.toString()
        btnsimpan.setOnClickListener {
            Log.d("UUID DEVICE:",myUuidAsString)
            helperLogin.insertDatag(
                edtTxtEmail.getText().toString(), edtTxtPassword.getText().toString(),
                edtTxtNamaDevice.getText().toString(), myUuidAsString)

            clientSocket = DatagramSocket()
            val SSID= edtTxtSSID.getText().toString()
            val paswordSSID= edtTxtPasswordSSID.getText().toString()
            val IP= edtTxtIP.getText().toString()
            var count=0
            Log.d("UDP Client", "Pesan terkirim ke server")
            Thread {
                while(count<20) {
                    count++
                    // Kirim data UDP
                    val message = "$SSID,$paswordSSID,$myUuidAsString"
                    val sendData = message.toByteArray()
                    val sendPacket =
                        DatagramPacket(sendData, sendData.size, InetAddress.getByName(IP), 8000)
                    clientSocket.send(sendPacket)
                }
//
            }.start()
            Toast.makeText(this, "Kembali", Toast.LENGTH_SHORT).show()
        }

    }
}