package com.example.a19012021031_practical_7

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter(private val context: Context, private val arrayList: ArrayList<Note>) :
    BaseAdapter() {

    private lateinit var title : TextView
    private lateinit var subTitle : TextView
    private lateinit var description : TextView
    private lateinit var reminderTime : TextView
    private lateinit var modifiedTime : TextView
    private var isReminder : Boolean = false
    private lateinit var editBtn : ImageButton
    private lateinit var deleteBtn : ImageButton


    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val convertView: View? = LayoutInflater.from(context).inflate(R.layout.item_box,parent,false)
        this.title = convertView!!.findViewById(R.id.textViewTitle)
        this.subTitle = convertView.findViewById(R.id.textViewSubTitle)
        this.description = convertView.findViewById(R.id.textViewDescription)
        this.reminderTime = convertView.findViewById(R.id.reminder_time)
        this.modifiedTime = convertView.findViewById(R.id.time)
        this.editBtn = convertView.findViewById(R.id.edit_btn)
        this.deleteBtn = convertView.findViewById(R.id.delete_btn)

        this.title.text = arrayList[position].getTitle()
        this.subTitle.text = arrayList[position].getSubTitle()
        this.description.text = arrayList[position].getDescription()
        this.reminderTime.text = arrayList[position].getReminderTime()
        this.modifiedTime.text = arrayList[position].getModifiedTime()
        this.isReminder = arrayList[position].getIsReminder()

        this.editBtn.setOnClickListener {

            val boxView = LayoutInflater.from(context).inflate(R.layout.form_view, null)
            val alertDialog = AlertDialog.Builder(context).setView(boxView)

            val titleText = boxView.findViewById<TextView>(R.id.form_title)
            val subTitleText = boxView.findViewById<TextView>(R.id.form_sub_title)
            val descriptionText = boxView.findViewById<TextView>(R.id.form_description)
            val timePicker = boxView.findViewById<TimePicker>(R.id.form_time_picker)
            var switch = boxView.findViewById<Switch>(R.id.form_switch)

            alertDialog.apply {
                setTitle("Edit Note here..")

                titleText.text = arrayList[position].getTitle()
                subTitleText.text = arrayList[position].getSubTitle()
                descriptionText.text = arrayList[position].getDescription()

                timePicker.minute = arrayList[position].getMinutes().toInt()

                if(arrayList[position].getAMPM()=="1"){
                    timePicker.hour = 12 + arrayList[position].getHours().toInt()
                }
                else{
                    timePicker.hour = arrayList[position].getHours().toInt()
                }

                switch.isChecked = isReminder

                switch.setOnClickListener {
                    isReminder = !isReminder
                }


                setPositiveButton("Update") { dialogInterface: DialogInterface, i: Int ->
                    val alarmCalendar = Calendar.getInstance()

                    val year: Int = alarmCalendar.get(Calendar.YEAR)
                    val month: Int = alarmCalendar.get(Calendar.MONTH)
                    val day: Int = alarmCalendar.get(Calendar.DATE)
                    val time = SimpleDateFormat("MMM, dd yyyy hh:mm:ss a").format(alarmCalendar.time)

                    alarmCalendar.set(year,month,day,timePicker.hour,timePicker.minute)


                    note_list.editItem(
                        titleText.text.toString(),
                        subTitleText.text.toString(),
                        descriptionText.text.toString(),
                        time.toString(),
                        isReminder,
                        SimpleDateFormat("MMM, dd yyyy hh:mm:ss a").format(alarmCalendar.time),
                        arrayList[position].getId()
                    )
                    Toast.makeText(context,"Note Updated!",Toast.LENGTH_SHORT).show()

                }
                setNegativeButton("Cancel"){ dialogInterface: DialogInterface, i: Int ->
                    Toast.makeText(context,"Canceled",Toast.LENGTH_SHORT).show()
                }
            }.show()
        }

        this.deleteBtn.setOnClickListener {
            note_list.deleteItem(arrayList[position].getId())
        }

        return convertView
    }


}
