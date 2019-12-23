package com.gentalhacode.alfred.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.gentalhacode.alfred.MainActivity
import com.gentalhacode.alfred.R
import com.gentalhacode.alfred.extensions.animateContentIn
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SplashScreenActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main + Job()
    private val ivLogo: ImageView by lazy { findViewById<ImageView>(R.id.splashIcLogo) }
    private val txtLogo: TextView by lazy { findViewById<TextView>(R.id.splashTxtLogo) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        launch {
            delay(1000)
            ivLogo.animateContentIn()
            delay(1000)
            txtLogo.animateContentIn()
            delay(1000)
            withContext(Dispatchers.Main) {
                if (FirebaseAuth.getInstance().currentUser == null) {
                    startActivity(Intent(this@SplashScreenActivity, SignInActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}
