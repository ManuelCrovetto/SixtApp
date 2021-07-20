package com.macrosystems.sixtapp.ui.list.rvadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.macrosystems.sixtapp.R
import com.macrosystems.sixtapp.data.model.CarDetails
import com.macrosystems.sixtapp.ui.list.rvadapters.ifcs.NotifyItemHasChangedListener

class CarsRVAdapter : RecyclerView.Adapter<CarViewHolder>(), NotifyItemHasChangedListener {

    private var dataList = ArrayList<CarDetails>()

    fun setListData(data: List<CarDetails>){
        dataList = data as ArrayList<CarDetails>
        notifyItemRangeInserted(0, data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.cardetails_item, parent, false)
        return CarViewHolder(view, this)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val details: CarDetails = dataList[position]
        holder.bindView(details)
    }

    override fun getItemCount(): Int = dataList.size

    override fun itemHasChanged(position: Int) {
       notifyItemChanged(position)
    }

}

