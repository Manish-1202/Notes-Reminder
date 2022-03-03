package com.example.a19012021031_practical_7

import java.io.Serializable
class Note(
    private var id: Int,
    private var title: String,
    private var subTitle: String,
    private var description: String,
    private var modifiedTime: String,
    private var isReminder: Boolean,
    private var reminderTime: String
) : Serializable {

    private lateinit var hours : String
    private lateinit var minutes : String
    private lateinit var ampm : String

    companion object{
        var idNote = 0
        fun noteIdGeneration():Int
        {
            idNote++
            return idNote
        }
    }

    fun setId(id:Int){
        this.id = id
    }
    fun getId():Int{
        return this.id
    }

    @JvmName("getTitle1")
    fun getTitle():String{
        return this.title
    }
    fun setTitle(title: String){
        this.title = title
    }
    @JvmName("getSubTitle1")
    fun getSubTitle():String{
        return this.subTitle
    }
    fun setSubTitle(subTitle: String){
        this.subTitle = subTitle
    }
    @JvmName("getDescription1")
    fun getDescription():String{
        return this.description
    }
    fun setDescription(description: String){
        this.description = description
    }
    @JvmName("getModifiedTime1")
    fun getModifiedTime():String{
        return this.modifiedTime
    }
    fun setModifiedTime(modifiedTime: String){
        this.modifiedTime = modifiedTime
    }
    fun getIsReminder():Boolean{
        return this.isReminder
    }
    fun setIsReminder(isReminder: Boolean){
        this.isReminder = isReminder
    }
    @JvmName("getReminderTime1")
    fun getReminderTime():String{
        return this.reminderTime
    }
    fun setReminderTime(reminderTime: String){
        this.reminderTime = reminderTime
    }
    fun getHours():String{
        return this.hours
    }
    fun setHours(hours: String){
        this.hours = hours
    }
    fun getMinutes():String{
        return this.minutes
    }
    fun setMinutes(minutes: String){
        this.minutes = minutes
    }
    fun getAMPM():String{
        return this.ampm
    }
    fun setAMPM(ampm:String){
        this.ampm = ampm
    }
}

