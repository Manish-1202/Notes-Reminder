package com.example.a19012021031_practical_7
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        setStatusBarTransparent()

        val loginBtn = findViewById<AppCompatButton>(R.id.login)
        val email = findViewById<TextInputEditText>(R.id.email)
        val password = findViewById<TextInputEditText>(R.id.password)
        val acc = this.findViewById<TextView>(R.id.account)

        acc.setOnClickListener {
            Intent(this,signup::class.java).apply {
                startActivity(this)
            }
        }


        loginBtn.setOnClickListener{
            val data : Login_Info? = intent.extras?.getSerializable("user_data") as? Login_Info
            if(data != null) {
//                Toast.makeText(this,data.getEmail(),Toast.LENGTH_SHORT).show()
                if (email.text!!.toString() == "") {
                    Toast.makeText(this, "Email Is Required!", Toast.LENGTH_SHORT).show()
                } else if (email.text!!.toString() != data.getEmail()) {
                    Toast.makeText(this, "Email Is Wrong!", Toast.LENGTH_SHORT).show()
                } else if (password.text!!.toString() == "") {
                    Toast.makeText(this, "Password is Required!", Toast.LENGTH_SHORT).show()
                }
                else if (password.text!!.toString() != data.getPassword()) {
                    Toast.makeText(this, "Password is Wrong!", Toast.LENGTH_SHORT).show()
                } else {
                    Intent(this, dash_board_main::class.java).apply {
                        putExtra("user_data", data)
                        startActivity(this)
                    }
                }
            }
            else{
                Toast.makeText(this,"All Fields Are Required!",Toast.LENGTH_SHORT).show()
            }

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
