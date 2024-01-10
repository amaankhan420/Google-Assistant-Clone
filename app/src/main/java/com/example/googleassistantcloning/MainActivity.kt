package com.example.googleassistantcloning


import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.googleassistantcloning.utils.UiUtils.setCustomActionBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var  hiGoogle : ImageView
    private lateinit var  googleLens : ImageView
    private lateinit var  explore : ImageView
    private lateinit var scaleUp: Animation
    private lateinit var scaleDown: Animation
    private lateinit var moveUpAndFadeOut: Animation
    private val Record_Audio_Request_Code:Int=1

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setCustomActionBar( supportActionBar, this)
        imageView = findViewById(R.id.action_button)
        googleLens= findViewById(R.id.action_google_lens)
        explore = findViewById(R.id.action_explore)
        hiGoogle = findViewById(R.id.hiGoogle)
        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down)
        moveUpAndFadeOut = AnimationUtils.loadAnimation(this, R.anim.move_up_and_fade_out)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PERMISSION_GRANTED) {
            checkPermission()
        }

        hiGoogle.setOnClickListener {
            moveUpAndFadeOut.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    lifecycleScope.launch {
                        delay(220)
                        startActivity(Intent(this@MainActivity, AssistantActivity::class.java))
                    }
                }
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    hiGoogle.clearAnimation()
                }
            })
            hiGoogle.startAnimation(moveUpAndFadeOut)
        }

        imageView.setOnClickListener {
            scaleUp.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    imageView.startAnimation(scaleDown)
                }
            })
            imageView.startAnimation(scaleUp)
            scaleDown.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    startActivity(Intent(this@MainActivity, AssistantActivity::class.java))
                }
            })
        }

        googleLens.setOnClickListener{
            scaleUp.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    googleLens.startAnimation(scaleDown)
                }
            })

            googleLens.startAnimation(scaleUp)

            scaleDown.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    startActivity(Intent(this@MainActivity, GoogleLensActivity::class.java))
                }
            })
        }

        explore.setOnClickListener{
            scaleUp.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    explore.startAnimation(scaleDown)
                }
            })

            explore.startAnimation(scaleUp)

            scaleDown.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    startActivity(Intent(this@MainActivity, ExploreActivity::class.java))
                }
            })
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==Record_Audio_Request_Code && grantResults.isNotEmpty())
        {
            if (grantResults[0]== PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPermission(){
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.RECORD_AUDIO),
            Record_Audio_Request_Code )
    }
}