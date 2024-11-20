package com.example.movieapp

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val  binding by lazy {
            ActivitySplashBinding.inflate(layoutInflater)
        }
        setContentView(binding.root)


        // تغيير لون شريط التنقل السفلي على الأجهزة التي تعمل بنظام Android 10 أو أحدث
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = Color.BLACK // تغيير لون شريط التنقل السفلي
            window.statusBarColor = Color.BLACK // تغيير لون شريط الحالة

        }

        // Access the LottieAnimationView using view binding
        binding.lottieAnimation.playAnimation() // Play the animation

        // Set the duration of the splash screen (8 seconds)
        Handler(Looper.getMainLooper()).postDelayed({
            // Start main activity
            startActivity(Intent(this, MainActivity::class.java))
            // Close splash activity
            finish()
        }, 8000) // 8 secon
    }
}