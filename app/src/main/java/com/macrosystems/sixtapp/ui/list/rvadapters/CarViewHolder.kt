package com.macrosystems.sixtapp.ui.list.rvadapters

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.macrosystems.sixtapp.R
import com.macrosystems.sixtapp.data.model.CarDetails
import com.macrosystems.sixtapp.databinding.CardetailsItemBinding
import com.macrosystems.sixtapp.ui.list.rvadapters.ifcs.NotifyItemHasChangedListener

const val SIXT_URL = "https://www.sixt.com/"

class CarViewHolder(itemView: View, private val notifyItemHasChangedListener: NotifyItemHasChangedListener) : RecyclerView.ViewHolder(itemView) {
    private val binding = CardetailsItemBinding.bind(itemView)
    private val context = binding.viewParent.context

    fun bindView(details: CarDetails){
        setUpFuelLvl(details.fuelLevel)
        setUpCarImage(details.carImageUrl)
        setUpCopies(details)
        itemView.setOnClickListener { onItemViewSelected(details) }
        binding.btnReserve.setOnClickListener{ onReserveSelected() }
    }

    private fun setUpFuelLvl(fuelLevel: String?) {
        fuelLevel?.let {
            when (fuelLevel){
                "1"->{
                    binding.pbFuelLevel.progress = 100
                }
                "0"->{
                    binding.pbFuelLevel.progress = 0
                }
                else->{
                    val percentage = fuelLevel.substring(2).toInt()
                    binding.pbFuelLevel.progress = percentage
                }
            }
        }
    }

    private fun setUpCarImage(carImageUrl: String?) {
        carImageUrl?.let {
            Glide.with(context).load(carImageUrl).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    binding.ivCarPicture.setImageResource(R.drawable.ic_baseline_broken_image_24)
                    return true
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    return false
                }

            }).into(binding.ivCarPicture)

        } ?: run {
            binding.ivCarPicture.setImageResource(R.drawable.ic_baseline_broken_image_24)
        }
    }

    private fun setUpCopies(details: CarDetails) {
        binding.tvBrandAndModel.text = context.getString(R.string.car_brand_model_concatenation, details.make, details.modelName)
        binding.tvName.text = context.getString(R.string.car_owner_name, details.name)
        binding.tvCarColor.text = context.getString(R.string.car_color, details.color)
        binding.tvCleanliness.text = context.getString(R.string.car_cleanliness, details.innerCleanliness)
        binding.tvLicensePlate.text = context.getString(R.string.car_license_plate, details.licensePlate)

        val isExpandable: Boolean = details.isExpanded
        binding.clExpandableLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE
        binding.btnReserve.visibility = if (isExpandable) View.VISIBLE else View.GONE
    }

    private fun onItemViewSelected(details: CarDetails) {
        details.isExpanded = !details.isExpanded

        if (adapterPosition != RecyclerView.NO_POSITION)
            notifyItemHasChangedListener.itemHasChanged(adapterPosition)
    }

    private fun onReserveSelected() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(SIXT_URL))
        context.startActivity(intent)
    }

}