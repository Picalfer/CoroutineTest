package com.example.coroutine_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.coroutine_test.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.buttonLoad.setOnClickListener {
            lifecycleScope.launch {
                loadData()
            }
        }
    }

    private suspend fun loadData() {
        println("load started $this")

        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false

        val city = loadCity()
        binding.tvLocation.text = city

        val temp = loadTemperature(city)

        binding.tvTemperature.text = temp.toString()
        binding.progress.isVisible = false
        binding.buttonLoad.isEnabled = true

        println("load started $this")
    }

    private suspend fun loadCity(): String {
        for (i in 1..5) {
            delay(1000)
            binding.progress.progress = i * 10
        }
        return "Moscow"
    }

    private suspend fun loadTemperature(city: String): Int {
        runOnUiThread {
            Toast.makeText(
                this,
                "Loading temperature for city: $city",
                Toast.LENGTH_SHORT
            ).show()
        }
        for (i in 1..5) {
            delay(1000)
            binding.progress.progress = (i + 5) * 10
        }
        return 17
    }
}