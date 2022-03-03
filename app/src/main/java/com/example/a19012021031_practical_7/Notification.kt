package com.example.a19012021031_practical_7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class Notification : AppCompatActivity() {
    private lateinit var title : TextView
    private lateinit var subTitle : TextView
    private lateinit var description : TextView
    private lateinit var reminderTime : TextView
    private lateinit var modifiedTime : TextView
    private var isReminder : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val note = (intent.extras?.getSerializable("note") as? Note)!!


        this.title = findViewById(R.id.notificationtextViewTitle)
        this.subTitle = findViewById(R.id.notificationtextViewSubTitle)
        this.description = findViewById(R.id.notificationtextViewDescription)
        this.reminderTime = findViewById(R.id.notificationreminder_time)
        this.modifiedTime = findViewById(R.id.notificationtime)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if(note!=null) {
            supportActionBar?.title = "Reminder Of ${note.getTitle()}"

            this.title.text = note.getTitle()
            this.subTitle.text = note.getSubTitle()
            this.description.text = note.getDescription()
            this.reminderTime.text = note.getReminderTime()
            this.modifiedTime.text = note.getModifiedTime()
            this.isReminder = note.getIsReminder()

        }
        else{
            supportActionBar?.title = "Reminder Of TODO"
            title.text = "Note Has been Deleted!"
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.notification_activity -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}
