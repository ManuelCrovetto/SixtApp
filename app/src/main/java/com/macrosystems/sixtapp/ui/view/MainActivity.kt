package com.macrosystems.sixtapp.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.macrosystems.sixtapp.R
import com.macrosystems.sixtapp.databinding.ActivityMainBinding
import com.macrosystems.sixtapp.ui.viewmodel.ListFragmentViewModel
import com.macrosystems.sixtapp.ui.viewmodel.factory.ListFragmentViewModelFactory
import com.macrosystems.sixtapp.utils.rvadapters.CarsRVAdapter
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        binding.sixtLogo?.bringToFront()
        val navController = findNavController(R.id.navHost)

        binding.bottomNavigationView?.setupWithNavController(navController)
    }

}