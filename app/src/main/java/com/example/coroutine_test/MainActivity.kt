package com.example.coroutine_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.coroutine_test.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.buttonLoad.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() {
        Looper.prepare()
        Handler() // only after Looper.prepare() we can use Handler constructor on no main thread

        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false

        loadCity {
            binding.tvLocation.text = it

            loadTemperature(it) {

                binding.tvTemperature.text = it.toString()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
            }
        }
    }

    private fun loadTemperature(city: String, callback: (Int) -> Unit) {
        thread {
            handler.post {
                Toast.makeText(
                    this,
                    "Loading temperature for city: $city",
                    Toast.LENGTH_SHORT
                ).show()
            }
            Thread.sleep(5000)
            handler.post {
                callback.invoke(17)
            }
        }
    }

    private fun loadCity(callback: (String) -> Unit) {
        thread {
            Thread.sleep(5000)
            handler.post {
                callback.invoke("Moscow")
            }
        }
    }

}