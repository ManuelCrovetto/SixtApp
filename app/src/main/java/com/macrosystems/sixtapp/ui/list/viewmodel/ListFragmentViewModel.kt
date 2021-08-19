package com.macrosystems.sixtapp.ui.list.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.macrosystems.sixtapp.data.model.CarDetails
import com.macrosystems.sixtapp.data.network.CarRepository
import com.macrosystems.sixtapp.ui.core.ifcs.AppListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.macrosystems.sixtapp.data.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListFragmentViewModel @Inject constructor(private val repo: CarRepository) : ViewModel() {

    var listener: AppListener? = null
    val carDetails: MutableLiveData<List<CarDetails>> = MutableLiveData()

    fun getCarDetails(){
        
        listener?.onStarted()
        viewModelScope.launch {
            val result: Result<CarDetails> = withContext(Dispatchers.IO){
                repo.getCarsDetails()
            }
            when (result){
                is Result.OnSuccess ->{
                    if (result.data.isNullOrEmpty()) {
                        listener?.onFailure()
                    } else {
                        listener?.onSuccess()
                        carDetails.value = result.data
                    }
                }
                is Result.OnError ->{
                    listener?.onFailure()
                }
            }
        }
    }
}