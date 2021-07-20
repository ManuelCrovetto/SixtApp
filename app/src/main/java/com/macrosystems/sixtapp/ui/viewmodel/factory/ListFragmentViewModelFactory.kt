package com.macrosystems.sixtapp.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.macrosystems.sixtapp.data.network.CarDetailsSource
import com.macrosystems.sixtapp.ui.list.viewmodel.ListFragmentViewModel

@Suppress("UNCHECKED_CAST")
class ListFragmentViewModelFactory (private val repo: CarDetailsSource): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListFragmentViewModel(repo) as T
    }
}