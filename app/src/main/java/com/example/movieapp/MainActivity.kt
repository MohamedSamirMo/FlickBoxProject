package com.example.movieapp

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.mvvm.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val  binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // تغيير لون شريط التنقل السفلي على الأجهزة التي تعمل بنظام Android 10 أو أحدث
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = Color.BLACK // تغيير لون شريط التنقل السفلي
            window.statusBarColor = Color.BLACK // تغيير لون شريط الحالة

        }
    }
}