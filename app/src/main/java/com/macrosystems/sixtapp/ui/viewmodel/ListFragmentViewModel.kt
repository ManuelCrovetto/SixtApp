package com.macrosystems.sixtapp.ui.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.macrosystems.sixtapp.data.Repo
import com.macrosystems.sixtapp.data.model.CarDetails
import com.macrosystems.sixtapp.data.network.CarDetailsSource
import com.macrosystems.sixtapp.data.network.Result
import com.macrosystems.sixtapp.ui.view.ifcs.AppListener
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListFragmentViewModel (private val repo: CarDetailsSource) : ViewModel() {

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

    /*fun query(): MutableLiveData<MutableList<CarDetails>> {
        listener?.onStarted()
        val list = MutableLiveData<MutableList<CarDetails>>()
        repo.getCarDetails().observeForever {
            list.value = it
        }
        return list
    }*/

    override fun onCleared() {
        super.onCleared()
    }


}