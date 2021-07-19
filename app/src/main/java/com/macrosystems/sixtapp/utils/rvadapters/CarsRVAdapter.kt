package com.macrosystems.sixtapp.utils.rvadapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.macrosystems.sixtapp.R
import com.macrosystems.sixtapp.data.model.CarDetails
import com.macrosystems.sixtapp.data.model.CarLatLng

class CarsRVAdapter (private val context: Context, private val listener: CarsRVAdapter.onItemClickListener) : RecyclerView.Adapter<CarsRVAdapter.MainViewHolder>(){
    private var dataList = ArrayList<CarDetails>()
    private lateinit var mListener: AdapterView.OnItemClickListener


    fun setListData(data: List<CarDetails>){
        dataList = data as ArrayList<CarDetails>
        notifyItemRangeInserted(0, data.size)
    }


    inner class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        @SuppressLint("CheckResult")
        fun bindView(details: CarDetails){
            if (details.carImageUrl != null){
                Glide.with(context).load(details.carImageUrl).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        itemView.findViewById<ImageView>(R.id.ivCarPicture).setImageResource(R.drawable.empty)
                        return true
                    }
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false
                    }
                }).into(itemView.findViewById(R.id.ivCarPicture))
            } else {
                itemView.findViewById<ImageView>(R.id.ivCarPicture).setImageResource(R.drawable.empty)
            }
            itemView.findViewById<AppCompatTextView>(R.id.tvBrandAndModel).text = context.getString(R.string.car_brand_model_concatenation, details.make, details.modelName)
            itemView.findViewById<AppCompatTextView>(R.id.tvName).text = context.getString(R.string.car_owner_name, details.name)
            itemView.findViewById<AppCompatTextView>(R.id.tvCarColor).text = context.getString(R.string.car_color, details.color)
            itemView.findViewById<AppCompatTextView>(R.id.tvCleanliness).text = context.getString(R.string.car_cleanliness, details.innerCleanliness)
            itemView.findViewById<AppCompatTextView>(R.id.tvLicensePlate).text = context.getString(R.string.car_license_plate, details.licensePlate)
            when (details.fuelLevel) {
                "1" -> {
                    itemView.findViewById<ProgressBar>(R.id.pbFuelLevel).progress = 100
                }
                "0" -> {
                    itemView.findViewById<ProgressBar>(R.id.pbFuelLevel).progress = 0
                }
                else -> {
                    val percentage = details.fuelLevel?.substring(2)?.toInt()
                    itemView.findViewById<ProgressBar>(R.id.pbFuelLevel).progress = percentage!!
                }
            }

            val isExpandable: Boolean = dataList[adapterPosition].isExpanded
            itemView.findViewById<ConstraintLayout>(R.id.clExpandableLayout).visibility = if (isExpandable) View.VISIBLE else View.GONE
            itemView.findViewById<AppCompatButton>(R.id.btnReserve).visibility = if (isExpandable) View.VISIBLE else View.GONE
            itemView.setOnClickListener(this)
            itemView.findViewById<AppCompatButton>(R.id.btnReserve).setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            when (v?.id){
                itemView.id->{
                    val expandable = dataList[adapterPosition]
                    expandable.isExpanded = !expandable.isExpanded
                    notifyItemChanged(adapterPosition)
                    val position: Int = adapterPosition
                    if (position!=RecyclerView.NO_POSITION) {
                        listener.onItemViewClick(position)
                    }
                }
                R.id.btnReserve->{
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.sixt.com/"))
                    context.startActivity(intent)
                }
            }
        }
    }

    interface onItemClickListener{
        fun onItemViewClick(position: Int)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsRVAdapter.MainViewHolder {
        val view: View? = LayoutInflater.from(context).inflate(R.layout.cardetails_item, parent, false)
        return MainViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: CarsRVAdapter.MainViewHolder, position: Int) {
        val details: CarDetails = dataList[position]
        holder.bindView(details)
    }

    override fun getItemCount(): Int {
        if (dataList.size > 0){
            return dataList.size
        }
        return 0
    }

}