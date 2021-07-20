package com.macrosystems.sixtapp.ui.map.view

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
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.macrosystems.sixtapp.R
import com.macrosystems.sixtapp.data.model.CarDetails
import com.macrosystems.sixtapp.databinding.MapFragmentBinding
import com.macrosystems.sixtapp.ui.core.ConnectionStatusLiveData
import com.macrosystems.sixtapp.ui.core.dialogs.ErrorDialog
import com.macrosystems.sixtapp.ui.core.dialogs.dialogifcs.OnClickDialog
import com.macrosystems.sixtapp.ui.core.ifcs.AppListener
import com.macrosystems.sixtapp.ui.list.viewmodel.ListFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback, AppListener {

    private val viewModel: ListFragmentViewModel by viewModels()

    private var _binding: MapFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var connectionStatusLiveData: ConnectionStatusLiveData
    private var isConnected = true

    private lateinit var map: GoogleMap
    private val markerOptions = MarkerOptions()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MapFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val map = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        map.getMapAsync(this)

        connectionStatusLiveData.observe(requireActivity(), {
            if (!it) {
                isConnected = false
            } else {
                if (!isConnected) {
                    viewModel.getCarDetails()
                    isConnected = true
                }
            }
        })
    }

    override fun onMapReady(gMap: GoogleMap) {
        map = gMap
        setUpViewModel()
    }

    private fun setUpViewModel() {
        viewModel.listener = this
        viewModel.getCarDetails()
        viewModel.carDetails.observe(viewLifecycleOwner, { locations->
            setMap(locations)
        })
    }

    private fun setMap(carList: List<CarDetails>?) {
        if (carList != null){
            val builder = LatLngBounds.Builder()
            for (car in carList){
                val loc = LatLng(car.latitude!!, car.longitude!!)
                with(markerOptions){
                    position(loc)
                    title(car.name)
                    snippet(getString(R.string.car_brand_model_concatenation, car.make, car.modelName))
                }
                map.addMarker(markerOptions)
                builder.include(loc)
            }
            val bounds = builder.build()
            val cameraAnimation = CameraUpdateFactory.newLatLngBounds(bounds, 0)
            map.animateCamera(cameraAnimation)
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