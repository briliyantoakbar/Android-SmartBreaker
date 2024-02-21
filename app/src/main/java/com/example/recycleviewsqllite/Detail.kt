package com.example.recycleviewsqllite

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar
import java.util.UUID

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
    lateinit var addtimeron:ImageView
    lateinit var bgon:LinearLayout
    lateinit var bgoff:LinearLayout
    lateinit var spinnerDalamRumah: Spinner
    lateinit var spinnerLuarRumah: Spinner
    lateinit var switchMaps:Switch
    lateinit var btnstop:Button
    lateinit var txetjarak:TextView

    private lateinit var receiver: BroadcastReceiver

    private var state = false
    var a=false
    var b=false
    var Uuid1:String=""
    var mapsStateDALAM =
        arrayOf("OFF","ON")
    var mapsStateLUAR =
        arrayOf("OFF","ON")
    var datastatedalam:String=""
    var datastateluar:String=""
    var c:Boolean=true
    var jamA:Int=0
    var jamB:Int=0
    var menitA:Int=0
    var menitB:Int=0
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
        addtimeron=findViewById(R.id.addtimeron)
        bgon=findViewById(R.id.bgon)
        bgoff=findViewById(R.id.bgoff)
        spinnerDalamRumah=findViewById(R.id.dalamrumah)
        spinnerLuarRumah=findViewById(R.id.luarrumah)
        switchMaps=findViewById(R.id.switchMaps)
        txetjarak=findViewById(R.id.txtjarak)


        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val data = intent?.getStringExtra("extra_data")
                val uuiddata = intent?.getStringExtra("extra_dataUUID")
                val stateforeground = intent?.getStringExtra("foreground")
                var posisispinnerdalam = intent?.getStringExtra("dalam")
                var posisispinnerluar = intent?.getStringExtra("luar")
                // Gunakan data yang diterima
                Log.d("MyActivity", "Data received: $data")
                if(Uuid1.equals(uuiddata)){
                        txetjarak.setText(data.toString()+" m")

                }
                if((stateforeground.equals("1")) and(Uuid1.equals(uuiddata))){
                    c=false
                    switchMaps.isChecked=true
                    if (posisispinnerdalam != null) {
                        spinnerDalamRumah.setSelection(posisispinnerdalam.toInt())
                    }
                    if (posisispinnerluar != null) {
                        spinnerLuarRumah.setSelection(posisispinnerluar.toInt())
                    }
                }
                Log.d("MyActivity", "Data received: $uuiddata")
                Log.d("MyActivity2", "Data received: $Uuid1")
                Log.d("KONDIS C:",posisispinnerluar.toString())

            }
        }

        val listpost=ArrayList<DataItem>()
        val listtimer=ArrayList<DataItem>()
        val dataItem=DataItem()
        var id1 = intent.getStringExtra("Id")
        Uuid1 = intent.getStringExtra("Uuid").toString()
        var Nama1 = intent.getStringExtra("NamaRuang")
        var TipeRuang1 = intent.getStringExtra("Tiperuang")
        var Relay1 = intent.getStringExtra("Relay")
        var NamaProduk1 = intent.getStringExtra("NamaProduk")
        var TipeProduk1 = intent.getStringExtra("TipeProduk")
        var UuidProduk1 = intent.getStringExtra("UuidProduk")
        var Kondisi1 = intent.getStringExtra("Kondisi")
        var latitde=intent.getDoubleExtra("latitude",0.00)!!
        var longitude=intent.getDoubleExtra("longitude",0.00)!!
        Log.d("OKON:",latitde.toString())
        listtimer.clear()

        val data=HelperTimer(this).getjam(Uuid1)

        if(data.get(0)>12){
            txttimeON.setText(
                String.format("%02d", data.get(0) - 12) + ":" + String.format("%02d",data.get(1)) + "PM"
            )
        }
        else if(data.get(0)<12){
            txttimeON.setText(
                String.format("%02d", data.get(0)) + ":" + String.format("%02d",data.get(1)) + "PM"
            )
        }else{
            txttimeON.setText("00"+":"+"00"+"AM")
        }
        val dataOFF=HelpertimerOFF(this).getjam(Uuid1)
        if(dataOFF.get(0)>12){
            txttimeOFF.setText(
                String.format("%02d", dataOFF.get(0) - 12) + ":" + String.format("%02d",dataOFF.get(1)) + "PM"
            )
        }
        else if(dataOFF.get(0)<12){
            txttimeOFF.setText(
                String.format("%02d", dataOFF.get(0)) + ":" + String.format("%02d",dataOFF.get(1)) + "PM"
            )
        }else{
            txttimeOFF.setText("00"+":"+"00"+"AM")
        }


        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mapsStateDALAM)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDalamRumah.adapter = adapter

        spinnerDalamRumah.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if(mapsStateDALAM[position].equals("ON")){
                    datastatedalam = "1"
                }else{
                    datastatedalam = "0"
                }

            }



            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        val adapterluar = ArrayAdapter(this, android.R.layout.simple_spinner_item, mapsStateLUAR)
        adapterluar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLuarRumah.adapter = adapterluar

        spinnerLuarRumah.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if(mapsStateLUAR[position].equals("ON")){
                    datastateluar = "1"
                }else{
                    datastateluar = "0"
                }
            }



            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        switchMaps.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked.equals(true)) {
                if(c.equals(true)) {
                    val startIntent = Intent(this, MyForegroundService::class.java).apply {
                      putExtra("Id", id1)
                     putExtra("Uuid",Uuid1)
                       putExtra("NamaRuang", Nama1)
                        putExtra("Tiperuang",TipeRuang1)
                        putExtra("Relay",Relay1)
                       putExtra("NamaProduk",NamaProduk1)
                        putExtra("TipeProduk",TipeProduk1)
                        putExtra("UuidProduk",UuidProduk1)
                        putExtra("Kondisi", Kondisi1)
                        putExtra("latitude", latitude)
                        putExtra("longitude", longitude)
                        putExtra("statedalam", datastatedalam)
                        putExtra("stateluar", datastateluar)
                    }
                    startService(startIntent)
                    c=false
                }

            }
            else if(isChecked.equals(false)){
                val stopIntent = Intent(this, MyForegroundService::class.java)
                stopService(stopIntent)
                c=true
            }


            }




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


        addtimeron.setOnClickListener {
            c=true
            val intent1 = Intent(this,ActivityMaps::class.java)
            intent1.putExtra("Id", id1)
            intent1.putExtra("Uuid",Uuid1)
            intent1.putExtra("NamaRuang", Nama1)
            intent1.putExtra("Tiperuang",TipeRuang1)
            intent1.putExtra("Relay",Relay1)
            intent1.putExtra("NamaProduk",NamaProduk1)
            intent1.putExtra("TipeProduk",TipeProduk1)
            intent1.putExtra("UuidProduk",UuidProduk1)
            intent1.putExtra("Kondisi", Kondisi1)
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(intent1)
            finish()

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
                    bgon.setBackgroundColor(Color.GREEN)
                } else if (d.equals(0)) {
                    switchON.isChecked = false
                    bgon.setBackgroundColor(Color.WHITE)
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
                    bgoff.setBackgroundColor(Color.RED)
                } else if (d.equals(0)) {
                    switchOFF.isChecked = false
                    bgoff.setBackgroundColor(Color.WHITE)
                }
            }
        }
        switchON.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                bgon.setBackgroundColor(Color.GREEN)
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
                bgon.setBackgroundColor(Color.WHITE)
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
                bgoff.setBackgroundColor(Color.RED)
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
                bgoff.setBackgroundColor(Color.WHITE)
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

    private  fun showtimepickernew(){
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

        }
        myadapter.notifyDataSetChanged()
        adapter_recycle.notifyDataSetChanged()

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
                    HelperTimer(this).selectjam(Uuid1,piker.hour,piker.minute)
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
                     HelpertimerOFF(this).selectjam(Uuid1,piker.hour,piker.minute)
                    txttimeOFF.setText(
                        String.format(
                            "%02d",
                            piker.hour - 12
                        ) + ":" + String.format("%02d", piker.minute) + "PM"
                    )
                }
            }else{
                if(a.equals(true)){
                    HelperTimer(this).selectjam(Uuid1,piker.hour,piker.minute)
                    txttimeON.setText( String.format("%02d",piker.hour)+":"+String.format("%02d",piker.minute)+"AM")
                }
                if(b.equals(true)){
                    HelpertimerOFF(this).selectjam(Uuid1,piker.hour,piker.minute)
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


    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, IntentFilter("com.example.myforegroundservice.action.SEND_DATA"))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
    }


}