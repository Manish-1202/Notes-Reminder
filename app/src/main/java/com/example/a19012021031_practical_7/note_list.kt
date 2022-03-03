package com.example.a19012021031_practical_7

import AlarmService
import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.ClipData
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class note_list : AppCompatActivity() {
    companion object{
        lateinit var listView:ListView
        lateinit var arrayList : ArrayList<Note>
        lateinit var adapter : NoteAdapter
        fun editItem(title:String,subTitle:String,description:String,modifiedTime:String,isReminder:Boolean,reminderTime:String,id:Int){
            for(i in 0 until arrayList.size) {
                if (arrayList[i].getId() == id) {
                    arrayList[i].setIsReminder(isReminder)
                    arrayList[i].setReminderTime(reminderTime)
                    arrayList[i].setTitle(title)
                    arrayList[i].setSubTitle(subTitle)
                    arrayList[i].setDescription(description)
                    arrayList[i].setModifiedTime(modifiedTime)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        fun deleteItem(id: Int){
            for(i in 0 until arrayList.size){
                if(arrayList[i].getId()==id){
                    arrayList.removeAt(i)
                    adapter.notifyDataSetChanged()
                    return
                }
            }
        }
        fun getNoteItem(id: Int): Note? {
            for(i in 0 until arrayList.size){
                if(arrayList[i].getId() == id){
                    return arrayList[i]
                }
            }
            return null
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        setStatusBarTransparent()

        val bottomMenu = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        listView = findViewById(R.id.listView)
        val fButton = findViewById<FloatingActionButton>(R.id.floating_action_btn)

        bottomMenu.selectedItemId = R.id.notes

        arrayList = ArrayList()

        adapter = NoteAdapter(this,arrayList)

        listView.adapter = adapter

        fButton.setOnClickListener {
            val boxView = LayoutInflater.from(this).inflate(R.layout.form_view, null)
            val alertDialog = AlertDialog.Builder(this).setView(boxView)

            val titleText = boxView.findViewById<TextView>(R.id.form_title)
            val subTitleText = boxView.findViewById<TextView>(R.id.form_sub_title)
            val descriptionText = boxView.findViewById<TextView>(R.id.form_description)
            val timePicker = boxView.findViewById<TimePicker>(R.id.form_time_picker)
            val switch = boxView.findViewById<Switch>(R.id.form_switch)

            alertDialog.apply {
                setTitle("Add Note here..")
                var isReminder = false

                switch.setOnClickListener {
                    isReminder = !isReminder
                }

                setPositiveButton("Add") { dialogInterface: DialogInterface, i: Int ->
                    val alarmCalendar = Calendar.getInstance()
                    val year: Int = alarmCalendar.get(Calendar.YEAR)
                    val month: Int = alarmCalendar.get(Calendar.MONTH)
                    val day: Int = alarmCalendar.get(Calendar.DATE)

                    val time = SimpleDateFormat("MMM, dd yyyy hh:mm:ss a").format(alarmCalendar.time)
                    var reminderTime = ""
                    if(isReminder){
                        alarmCalendar.set(year, month, day, timePicker.hour, timePicker.minute, 0)

                        reminderTime = SimpleDateFormat("MMM, dd yyyy hh:mm:ss a").format(alarmCalendar.time)
                    }

                    val newItem = Note(Note.noteIdGeneration(),titleText.text.toString(),subTitleText.text.toString(),descriptionText.text.toString(),time,isReminder,reminderTime)

                    newItem.setHours(alarmCalendar.get(Calendar.HOUR).toString())
                    newItem.setMinutes(alarmCalendar.get(Calendar.MINUTE).toString())
                    newItem.setAMPM(alarmCalendar.get(Calendar.AM_PM).toString())

                    arrayList.add(newItem)


                    if(isReminder) {
                        setAlarm(alarmCalendar.timeInMillis, "Start",newItem.getId())
                    }
                    adapter.notifyDataSetChanged()
                }
                setNegativeButton("Cancel"){ dialogInterface: DialogInterface, i: Int ->
                    Toast.makeText(applicationContext,"Canceled",Toast.LENGTH_SHORT).show()
                }
            }.show()
        }



        bottomMenu.setOnItemSelectedListener {
            when(it.itemId){
                R.id.dashboard -> {
                    Intent(this,dash_board_main::class.java).apply {
                        startActivity(this)
                    }
                    return@setOnItemSelectedListener true
                }
                R.id.notes->{
                    Intent(this,note_list::class.java).apply {
                        startActivity(this)
                    }
                    return@setOnItemSelectedListener  true
                }
                else -> {
                    return@setOnItemSelectedListener true
                }
            }
        }
    }
    private fun setAlarm(millisTime: Long, str: String,id:Int)
    {
        val intent = Intent(this, MyReceiver::class.java)
//        intent.putExtra("Service1", str)
        val intentService = Intent(this,AlarmService::class.java)
        intentService.putExtra("id",id)

        val pendingIntent = PendingIntent.getBroadcast(this@note_list, 234324243, intent, 0)
        val pendingIntentService = PendingIntent.getService(this@note_list, 234324244, intentService, 0)

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        if(str == "Start") {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                millisTime,
                pendingIntent
            )
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                millisTime,
                pendingIntentService
            )
        }else if(str == "Stop")
        {
            alarmManager.cancel(pendingIntent)
        }
    }



    private fun setWindowFlag(flagTranslucentStatus: Int, b: Boolean) {
        val winParameters = window.attributes
        if(b){
            winParameters.flags =  winParameters.flags or flagTranslucentStatus
        }
        else{
            winParameters.flags =  winParameters.flags and flagTranslucentStatus.inv()
        }
        window.attributes = winParameters
    }

    private fun setStatusBarTransparent(){
        if (Build.VERSION.SDK_INT in 19..20) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
            }
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        if(Build.VERSION.SDK_INT >= 21){
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

}
