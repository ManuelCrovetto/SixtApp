package com.macrosystems.sixtapp.ui.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.macrosystems.sixtapp.R
import com.macrosystems.sixtapp.data.model.CarDetails
import com.macrosystems.sixtapp.data.model.CarLatLng
import com.macrosystems.sixtapp.databinding.MapFragmentBinding
import com.macrosystems.sixtapp.ui.view.dialogs.ErrorDialog
import com.macrosystems.sixtapp.ui.view.dialogs.dialogifcs.OnClickDialog
import com.macrosystems.sixtapp.ui.view.ifcs.AppListener
import com.macrosystems.sixtapp.ui.viewmodel.ListFragmentViewModel
import com.macrosystems.sixtapp.ui.viewmodel.factory.ListFragmentViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MapFragment : Fragment(), KodeinAware, OnMapReadyCallback, AppListener {
    override val kodein by kodein()
    private val factory: ListFragmentViewModelFactory by instance()
    private lateinit var binding: MapFragmentBinding
    private lateinit var viewModel: ListFragmentViewModel

    private lateinit var mMap: GoogleMap
    private val markerOptions = MarkerOptions()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MapFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val map = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        map.getMapAsync(this)

    }


    override fun onMapReady(gMap: GoogleMap) {
        mMap = gMap
        setUpViewModel()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(requireActivity(), factory).get(ListFragmentViewModel::class.java)
        viewModel.listener = this
        with(viewModel){
            binding.vm = viewModel
            getCarDetails()
            carDetails.observe(viewLifecycleOwner, locsObserver)
        }
    }

    private val locsObserver = Observer<List<CarDetails>>{list->
            setMap(list)
    }

    private fun setMap(it: List<CarDetails>?) {
        if (it != null){
            val builder = LatLngBounds.Builder()
            for (car in it){
                val loc = LatLng(car.latitude!!, car.longitude!!)
                with(markerOptions){
                    position(loc)
                    title(car.name)
                    snippet(getString(R.string.car_brand_model_concatenation, car.make, car.modelName))

                }
                mMap.addMarker(markerOptions)
                builder.include(loc)
            }
            val bounds = builder.build()
            val padding = 0
            val cameraAnimation = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            mMap.animateCamera(cameraAnimation)
        }
    }



    override fun onStarted() {
        binding.clProgressBarContainer.isVisible = true
    }

    override fun onSuccess() {
        binding.clProgressBarContainer.isGone = true
    }

    override fun onFailure() {
        binding.clProgressBarContainer.isGone = true
        val errorDialog = ErrorDialog(requireContext(), object : OnClickDialog {
            override fun onClicked() {
                viewModel.getCarDetails()
            }
        })
        errorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        errorDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        errorDialog.show()
    }



}