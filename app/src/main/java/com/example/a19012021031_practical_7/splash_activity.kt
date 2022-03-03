package com.example.a19012021031_practical_7

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class splash_activity : AppCompatActivity(){
    lateinit var imageView : ImageView
    lateinit var mainLayout : ConstraintLayout
    lateinit var animation : AnimationDrawable
    lateinit var animatedLogo: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setStatusBarTransparent()

        imageView = findViewById(R.id.logo)
        mainLayout = findViewById(R.id.splash_layout)


        imageView.setBackgroundResource(R.drawable.uvpce_logo)
        animation  = imageView.background as AnimationDrawable
        animation.start()


        animatedLogo = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        imageView.startAnimation(animatedLogo)

        animatedLogo.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                imageView.animate().scaleX(3.0f).scaleY(3.0f).alpha(0.1f).duration = 1500
                Handler().postDelayed({
                    val intent = Intent(this@splash_activity, dash_board_main::class.java)
                    startActivity(intent)
                    finish()
                }, 1500)
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })
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
