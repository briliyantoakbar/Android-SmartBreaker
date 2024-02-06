package com.example.recycleviewsqllite

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class Detail : AppCompatActivity() {
    lateinit var TipeRuang: TextView
    lateinit var TipeProduk: TextView
    lateinit var NamaRuang: TextView
    lateinit var gambar5: ShapeableImageView
    lateinit var power: ShapeableImageView
    lateinit var indikator: ShapeableImageView
    lateinit var timerON:CardView
    lateinit var timerOFF:CardView
    lateinit var btnOFF: Button
    lateinit var piker:MaterialTimePicker
    lateinit var calendar: Calendar
    lateinit var switchON:Switch
    lateinit var switchOFF:Switch
    lateinit var txttimeON:TextView
    lateinit var txttimeOFF:TextView
    private var state = false
    var a=false
    var b=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        TipeRuang = findViewById(R.id.TipeRuang)
        TipeProduk = findViewById(R.id.TipeProduk)
        NamaRuang = findViewById(R.id.NamaRuang)
        gambar5 = findViewById(R.id.backgrounddetail)
        power = findViewById(R.id.power)
        indikator = findViewById(R.id.indikator)
        timerON=findViewById(R.id.TimerON)
        timerOFF=findViewById(R.id.TimerOFF)
        switchON=findViewById(R.id.switchON)
        switchOFF=findViewById(R.id.switchOFF)
        txttimeON=findViewById(R.id.txttimeON)
        txttimeOFF=findViewById(R.id.txttimeOFF)
        val listpost=ArrayList<DataItem>()
        val listtimer=ArrayList<DataItem>()
        val dataItem=DataItem()
        var id1 = intent.getStringExtra("Id")
        var Uuid1 = intent.getStringExtra("Uuid")
        var Nama1 = intent.getStringExtra("NamaRuang")
        var TipeRuang1 = intent.getStringExtra("Tiperuang")
        var Relay1 = intent.getStringExtra("Relay")
        var NamaProduk1 = intent.getStringExtra("NamaProduk")
        var TipeProduk1 = intent.getStringExtra("TipeProduk")
        var UuidProduk1 = intent.getStringExtra("UuidProduk")
        var Kondisi1 = intent.getStringExtra("Kondisi")
        Log.d("OKON:",Kondisi1.toString())
        listtimer.clear()
        timerON.setOnClickListener {
            showtimepicker()
            a=true
            b=false
        }
        timerOFF.setOnClickListener {
            showtimepicker()
            a=false
            b=true
        }
        val getswitch= HelperTimer(this)
        if (Uuid1 != null) {
            var d=getswitch.getswitch(Uuid1)
            Log.d("GETSWITCH:",d.toString() )
            Toast.makeText(this,d.toString(), Toast.LENGTH_SHORT).show()
            if (d != null) {
                if (d.equals(1)) {
                    Toast.makeText(this, "True", Toast.LENGTH_SHORT).show()
                    switchON.isChecked = true
                } else if (d.equals(0)) {
                    switchON.isChecked = false
                }
            }
        }

        val getswitchOFF= HelpertimerOFF(this)
        if (Uuid1 != null) {
            var d=getswitchOFF.getswitch(Uuid1)
            Log.d("GETSWITCH:",d.toString() )
            Toast.makeText(this,d.toString(), Toast.LENGTH_SHORT).show()
            if (d != null) {
                if (d.equals(1)) {
                    Toast.makeText(this, "True", Toast.LENGTH_SHORT).show()
                    switchOFF.isChecked = true
                } else if (d.equals(0)) {
                    switchOFF.isChecked = false
                }
            }
        }
        switchON.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val helper = Helper(this)
                helper.UpdateData(
                    id1.toString(),
                    Uuid1.toString(),
                    Nama1.toString(),
                    TipeRuang1.toString(),
                    Relay1.toString(),
                    NamaProduk1.toString(),
                    TipeProduk1.toString(),
                    UuidProduk1.toString(),
                    "0")
                dataItem.idItem=id1.toString()
                dataItem.uuidItem=Uuid1.toString()
                dataItem.namaItem=Nama1.toString()
                dataItem.tipeItem=TipeRuang1.toString()
                dataItem.relayItem=Relay1.toString()
                dataItem.namadeviceItem=NamaProduk1.toString()
                dataItem.tipedeviceItem=TipeProduk1.toString()
                dataItem.uuiddeviceItem=UuidProduk1.toString()
                dataItem.kondisiItem="1"
                listtimer.add(dataItem)
                if (id1 != null) {
                    scheduleAlarm(id1.toInt()*2,listtimer,"1","a","0")
                }
                val helpertimer=HelperTimer(this)
                if (Uuid1 != null) {
//                    helpertimer.insertData(Uuid1,"1")
                    helpertimer.select(Uuid1,"1")
                }
                Toast.makeText(this, "Feature Enabled", Toast.LENGTH_SHORT).show()
            } else {
                val helpertimer=HelperTimer(this)
                if (Uuid1 != null) {
//                    helpertimer.insertData(Uuid1,"1")
                    helpertimer.select(Uuid1,"0")
                }
                Toast.makeText(this, "Feature Disabled", Toast.LENGTH_SHORT).show()
            }
        }

        switchOFF.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val helper = Helper(this)
                helper.UpdateData(
                    id1.toString(),
                    Uuid1.toString(),
                    Nama1.toString(),
                    TipeRuang1.toString(),
                    Relay1.toString(),
                    NamaProduk1.toString(),
                    TipeProduk1.toString(),
                    UuidProduk1.toString(),
                    "0")

                dataItem.idItem=id1.toString()
                dataItem.uuidItem=Uuid1.toString()
                dataItem.namaItem=Nama1.toString()
                dataItem.tipeItem=TipeRuang1.toString()
                dataItem.relayItem=Relay1.toString()
                dataItem.namadeviceItem=NamaProduk1.toString()
                dataItem.tipedeviceItem=TipeProduk1.toString()
                dataItem.uuiddeviceItem=UuidProduk1.toString()
                dataItem.kondisiItem="1"
                listtimer.add(dataItem)
                if (id1 != null) {
                    scheduleAlarm(id1.toInt()*3,listtimer,"0","b","0")
                }
                val helpertimerOFF=HelpertimerOFF(this)
                if (Uuid1 != null) {
//                    helpertimer.insertData(Uuid1,"1")
                    helpertimerOFF.select(Uuid1,"1")
                }
                Toast.makeText(this, "Feature Enabled", Toast.LENGTH_SHORT).show()
            } else {
                val helpertimerOFF=HelpertimerOFF(this)
                if (Uuid1 != null) {
//                    helpertimer.insertData(Uuid1,"1")
                    helpertimerOFF.select(Uuid1,"0")
                }
                Toast.makeText(this, "Feature Disabled", Toast.LENGTH_SHORT).show()
            }
        }

        NamaRuang.setText(Nama1.toString())
        TipeRuang.setText(TipeRuang1.toString())
        TipeProduk.setText(TipeProduk1.toString())
        if (TipeProduk1.toString().equals("Ventilator")) {
            gambar5.setImageResource(R.drawable.kipas)
        } else if (TipeProduk1.toString().equals("Air Conditioner")) {
            gambar5.setImageResource(R.drawable.aircondioner)
        } else if (TipeProduk1.toString().equals("Lamp")) {
            gambar5.setImageResource(R.drawable.lampu)

        } else if (TipeProduk1.toString().equals("Other")) {
            gambar5.setImageResource(R.drawable.other)

        }
        if(Kondisi1.toString().equals("0")){
            indikator.setBackgroundColor(Color.RED)
        }
        else{
            indikator.setBackgroundColor(Color.GREEN)
        }
        power.setOnClickListener {
            Toast.makeText(this, "POWER", Toast.LENGTH_SHORT).show()
            listpost.clear()
            val datapost=DataItem()
            if (Kondisi1.toString().equals("1")) {
                Kondisi1="0"
                indikator.setBackgroundColor(Color.RED)
                Toast.makeText(this, "false", Toast.LENGTH_SHORT).show()
                val helper = Helper(this)
                helper.UpdateData(
                    id1.toString(),
                    Uuid1.toString(),
                    Nama1.toString(),
                    TipeRuang1.toString(),
                    Relay1.toString(),
                    NamaProduk1.toString(),
                    TipeProduk1.toString(),
                    UuidProduk1.toString(),
                    "0"

                )
                val get = Helper(this).ItemTipeRuang(TipeRuang1.toString())
                listdetail.clear()
                for (i in 0 until get.size) {
                    var datadetail = DataItem()
                    val id = get.get(i).idItem
                    val uuid = get.get(i).uuidItem
                    val namaruang = get.get(i).namaItem
                    val tiperuang = get.get(i).tipeItem
                    val relay = get.get(i).relayItem
                    val namaproduk = get.get(i).namadeviceItem
                    val tipeproduk = get.get(i).tipedeviceItem
                    val uuidproduk = get.get(i).uuiddeviceItem
                    val kondisi = get.get(i).kondisiItem
                    Log.d("ARRAY:", kondisi)
                    datadetail.idItem = id
                    datadetail.namaItem = namaruang
                    datadetail.uuidItem = uuid
                    datadetail.tipeItem = tiperuang
                    datadetail.relayItem = relay
                    datadetail.kondisiItem = kondisi
                    datadetail.namadeviceItem = namaproduk
                    datadetail.tipedeviceItem = tipeproduk
                    datadetail.uuiddeviceItem = uuidproduk
                    listdetail.add(datadetail)
                    myadapter.notifyDataSetChanged()
//                    datapost.uuiddeviceItem=uuidproduk
//                    datapost.namaItem=namaruang
//                    datapost.uuidItem=uuid
//                    datapost.
                    Kondisi1="0"

                }
                val post = Post("https://fastapiskripsi-be199a487d88.herokuapp.com/postperintah")
                post.HTTPPost(this,  listdetail)

            } else if(Kondisi1.toString().equals("0")){
                indikator.setBackgroundColor(Color.GREEN)
                Toast.makeText(this, "true", Toast.LENGTH_SHORT).show()
                val helper = Helper(this)
                helper.UpdateData(
                    id1.toString(),
                    Uuid1.toString(),
                    Nama1.toString(),
                    TipeRuang1.toString(),
                    Relay1.toString(),
                    NamaProduk1.toString(),
                    TipeProduk1.toString(),
                    UuidProduk1.toString(),
                    "1"
                )
                val get = Helper(this).ItemTipeRuang(TipeRuang1.toString())
                listdetail.clear()
                for (i in 0 until get.size) {
                    var datadetail = DataItem()
                    val id = get.get(i).idItem
                    val uuid = get.get(i).uuidItem
                    val namaruang = get.get(i).namaItem
                    val tiperuang = get.get(i).tipeItem
                    val relay = get.get(i).relayItem
                    val namaproduk = get.get(i).namadeviceItem
                    val tipeproduk = get.get(i).tipedeviceItem
                    val uuidproduk = get.get(i).uuiddeviceItem
                    val kondisi = get.get(i).kondisiItem
                    Log.d("ARRAY:", kondisi)
                    datadetail.idItem = id
                    datadetail.namaItem = namaruang
                    datadetail.uuidItem = uuid
                    datadetail.tipeItem = tiperuang
                    datadetail.relayItem = relay
                    datadetail.kondisiItem = kondisi
                    datadetail.namadeviceItem = namaproduk
                    datadetail.tipedeviceItem = tipeproduk
                    datadetail.uuiddeviceItem = uuidproduk
                    listdetail.add(datadetail)
                    myadapter.notifyDataSetChanged()

                }
                Kondisi1="1"
                val post = Post("https://fastapiskripsi-be199a487d88.herokuapp.com/postperintah")
                post.HTTPPost(this,  listdetail)
            }
        }

    }

    private  fun showtimepicker(){
        val piker= MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Alarm Time")
            .build()

        piker.show(supportFragmentManager, "foxandroid")
        piker.addOnPositiveButtonClickListener {
            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = piker.hour
            calendar[Calendar.MINUTE] = piker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            if(piker.hour>12){
                if(a.equals(true)) {
                    txttimeON.setText(
                        String.format(
                            "%02d",
                            piker.hour - 12
                        ) + ":" + String.format("%02d", piker.minute) + "PM"
                    )
                    Toast.makeText(this, String.format(
                        "%02d",
                        piker.hour - 12
                    ) + ":" + String.format("%02d", piker.minute) + "PM", Toast.LENGTH_SHORT).show()

                }
                 if(b.equals(true)){
                    txttimeOFF.setText(
                        String.format(
                            "%02d",
                            piker.hour - 12
                        ) + ":" + String.format("%02d", piker.minute) + "PM"
                    )
                }
            }else{
                if(a.equals(true)){
                    txttimeON.setText( String.format("%02d",piker.hour)+":"+String.format("%02d",piker.minute)+"AM")
                }
                if(b.equals(true)){
                    txttimeOFF.setText( String.format("%02d",piker.hour)+":"+String.format("%02d",piker.minute)+"AM")
                }

            }
        }

    }

    private fun scheduleAlarm(alarmId: Int,data:ArrayList<DataItem>,kondisi:String, switch:String, value:String) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("alarm_id", alarmId)
            putExtra("idItem",data.get(0).idItem)
            putExtra("namaItem",data.get(0).namaItem)
            putExtra("uuidItem",data.get(0).uuidItem)
            putExtra("tipeItem",data.get(0).tipeItem)
            putExtra("relayItem",data.get(0).relayItem)
            putExtra("namadeviceItem",data.get(0).namadeviceItem)
            putExtra("tipedeviceItem",data.get(0).tipedeviceItem)
            putExtra("uuiddeviceItem",data.get(0).uuiddeviceItem)
            putExtra("kondisiItem",kondisi)
            putExtra("switch",switch)
            putExtra("value",value)
        }

        // Determine the appropriate flag based on the Android version
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        val pendingIntent = PendingIntent.getBroadcast(this, alarmId, intent, flags)

        // Use setExactAndAllowWhileIdle for better reliability on newer Android versions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        } else {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,AlarmManager.INTERVAL_DAY, pendingIntent)
        }
    }



}