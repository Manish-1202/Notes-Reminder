package com.example.a19012021031_practical_7

import java.io.Serializable

class Login_Info : Serializable {
    private var username : String = ""
    private var phoneno : Long = 0
    private var city : String = ""
    private var email : String = ""
    private var password : String = ""

    constructor(username:String, phoneno:Long, city:String, email:String, password:String) {
        this.username = username
        this.phoneno = phoneno
        this.city = city
        this.email = email
        this.password = password
    }

    fun getUsername():String {
        return this.username
    }

    fun getPassword():String {
        return this.password
    }

    fun getCity():String{
        return this.city
    }

    fun getEmail():String {
        return this.email
    }

    fun getPhone():Long{
        return this.phoneno
    }
}
