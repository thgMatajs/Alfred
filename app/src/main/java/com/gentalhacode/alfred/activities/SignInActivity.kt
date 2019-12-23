package com.gentalhacode.alfred.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.gentalhacode.alfred.BuildConfig
import com.gentalhacode.alfred.MainActivity
import com.gentalhacode.alfred.R
import com.gentalhacode.alfred.extensions.setGone
import com.gentalhacode.alfred.extensions.setVisible
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignInActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions
    private var fbAuth = FirebaseAuth.getInstance()

    private val btnSignIn: ExtendedFloatingActionButton by lazy {
        findViewById<ExtendedFloatingActionButton>(R.id.btnSignIn)
    }
    private val loading: LottieAnimationView by lazy {
        findViewById<LottieAnimationView>(R.id.signInAnimationLoading)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initGoogleSignIn()
        btnSignIn.setOnClickListener {
            signIn()
            btnSignIn.setGone()
            loading.setVisible()
        }
    }

    private fun initGoogleSignIn() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_API_KEY)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                println("THG_LOG:: Google sign in failed ${e.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        fbAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    println("THG_LOG::signInWithCredential:failure ${task.exception}")
                }
            }
    }

    companion object {
        private const val RC_GOOGLE_SIGN_IN = 1
    }
}
