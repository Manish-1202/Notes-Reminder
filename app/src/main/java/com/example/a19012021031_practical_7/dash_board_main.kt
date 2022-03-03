package com.example.a19012021031_practical_7

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*

class dash_board_main : AppCompatActivity() {
    private lateinit var setAlarmText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board_main)
        supportActionBar?.hide()
        setStatusBarTransparent()

        val name = findViewById<TextView>(R.id.name)
        val email = findViewById<TextView>(R.id.email)
        val email1 = findViewById<TextView>(R.id.email1)
        val username = findViewById<TextView>(R.id.username)
        val number = findViewById<TextView>(R.id.number)
        val city = findViewById<TextView>(R.id.city)
        val logout = findViewById<TextView>(R.id.logout)
        setAlarmText = findViewById(R.id.set_alarm_text)
        val bottomMenu = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val slider = findViewById<ImageView>(R.id.slider_image)
        val heart_slider = findViewById<ImageView>(R.id.heart)

        slider.setBackgroundResource(R.drawable.slider_list_animation)
        heart_slider.setBackgroundResource(R.drawable.heart_animation)

        val animation : AnimationDrawable = slider.background as AnimationDrawable
        animation.start()

        val heartAnim : AnimationDrawable = heart_slider.background as AnimationDrawable
        heartAnim.start()


        bottomMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.dashboard -> {
                    Intent(this, dash_board_main::class.java).apply {
                        startActivity(this)
                    }
                    return@setOnItemSelectedListener true
                }
                R.id.notes -> {
                    Intent(this, note_list::class.java).apply {
                        startActivity(this)
                    }
                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener true
                }
            }
        }


        logout.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        }

        val data: Login_Info? = intent.extras?.getSerializable("user_data") as? Login_Info

        if (data != null) {
            name.text = data.getUsername()
            username.text = data.getUsername()
            email.text = data.getEmail()
            number.text = data.getPhone().toString()
            city.text = data.getCity()
            email1.text = data.getEmail()
        }
        setAlarmText.setOnClickListener {
        //   timePicker.visibility = View.VISIBLE
            showTimerDialog()
        }


    }

    private fun setAlarm(millisTime: Long, str: String) {
        val intent = Intent(this, MyReceiver::class.java)
        intent.putExtra("Service1", str)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 234324243, intent, 0)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        if (str == "Start") {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                millisTime,
                pendingIntent
            )
        } else if (str == "Stop") {
            alarmManager.cancel(pendingIntent)
        }
    }

    private fun showTimerDialog() {
        val cldr: Calendar = Calendar.getInstance()
        val hour: Int = cldr.get(Calendar.HOUR_OF_DAY)
        val minutes: Int = cldr.get(Calendar.MINUTE)
        // time picker dialog
        val picker = TimePickerDialog(
            this,
            { _, sHour, sMinute -> sendDialogDataToActivity(sHour, sMinute) },
            hour,
            minutes,
            false
        )
        picker.show()
    }


    private fun sendDialogDataToActivity(hour: Int, minute: Int) {
        val alarmCalendar = Calendar.getInstance()
        val year: Int = alarmCalendar.get(Calendar.YEAR)
        val month: Int = alarmCalendar.get(Calendar.MONTH)
        val day: Int = alarmCalendar.get(Calendar.DATE)
        alarmCalendar.set(year, month, day, hour, minute, 0)
        setAlarmText.text = SimpleDateFormat("hh:mm:ss a").format(alarmCalendar.time)
        setAlarm(alarmCalendar.timeInMillis, "Start")
        Toast.makeText(
            this,
            "Time: hours:${hour}, minutes:${minute},millis:${alarmCalendar.timeInMillis}",
            Toast.LENGTH_SHORT
        ).show()
    }


    private fun setWindowFlag(flagTranslucentStatus: Int, b: Boolean) {
        val winParameters = window.attributes
        if (b) {
            winParameters.flags = winParameters.flags or flagTranslucentStatus
        } else {
            winParameters.flags = winParameters.flags and flagTranslucentStatus.inv()
        }
        window.attributes = winParameters
    }

    private fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT in 19..20) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
            }
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }
}

