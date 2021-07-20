package com.macrosystems.sixtapp.ui.core

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.macrosystems.sixtapp.R
import com.macrosystems.sixtapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var connectionStatusLiveData: ConnectionStatusLiveData
    private var isConnected = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        connectionStatusLiveData = ConnectionStatusLiveData(this)
        connectionStatusLiveData.observe(this, { status->
            if (!status){
                isConnected = false
                val sb = Snackbar.make(binding.root, getString(R.string.lost_connection_default_text), Snackbar.LENGTH_INDEFINITE)
                val sbView=sb.view
                val params: FrameLayout.LayoutParams = sbView.layoutParams as FrameLayout.LayoutParams
                params.gravity = Gravity.TOP
                sbView.layoutParams = params
                sbView.setBackgroundColor(Color.RED)
                sb.show()
            } else {
                if (!isConnected) {
                    val sb = Snackbar.make(binding.root, getString(R.string.connected_successfully_default_text), Snackbar.LENGTH_LONG)
                    val sbView = sb.view
                    val params: FrameLayout.LayoutParams = sbView.layoutParams as FrameLayout.LayoutParams
                    params.gravity = Gravity.TOP
                    sbView.layoutParams = params
                    sbView.setBackgroundColor(Color.GREEN)
                    val tv: TextView = sbView.findViewById(com.google.android.material.R.id.snackbar_text)
                    tv.setTextColor(Color.BLACK)
                    sb.show()
                    isConnected = true
                }
            }
        })

        val navController = findNavController(R.id.navHost)

        binding.bottomNavigationView.setupWithNavController(navController)
    }

}