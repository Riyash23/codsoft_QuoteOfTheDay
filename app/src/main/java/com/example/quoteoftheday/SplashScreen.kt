package com.example.quoteoftheday

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quoteoftheday.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashTheScreen()
    }

    private fun splashTheScreen() {
        binding.ivSplash.alpha = 0f

        binding.ivSplash.animate().apply {
            alpha(1f)
            duration = 800
        }.withEndAction {
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }.start()
    }
}


