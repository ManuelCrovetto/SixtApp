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
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.macrosystems.sixtapp.data.model.CarDetails
import com.macrosystems.sixtapp.data.model.CarLatLng
import com.macrosystems.sixtapp.databinding.CarListBinding
import com.macrosystems.sixtapp.ui.view.dialogs.ErrorDialog
import com.macrosystems.sixtapp.ui.view.dialogs.dialogifcs.OnClickDialog
import com.macrosystems.sixtapp.ui.view.ifcs.AppListener
import com.macrosystems.sixtapp.ui.viewmodel.ListFragmentViewModel
import com.macrosystems.sixtapp.ui.viewmodel.factory.ListFragmentViewModelFactory
import com.macrosystems.sixtapp.utils.rvadapters.CarsRVAdapter

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*

class ListFragment : Fragment(), KodeinAware, CarsRVAdapter.onItemClickListener, AppListener {
    override val kodein by kodein()
    private val factory: ListFragmentViewModelFactory by instance()
    private lateinit var viewModel: ListFragmentViewModel
    private lateinit var binding: CarListBinding

    private lateinit var adapter: CarsRVAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CarListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, factory).get(ListFragmentViewModel::class.java)
        binding.vm = viewModel
        viewModel.listener = this

        viewModel.getCarDetails()



        viewModel.carDetails.observe(viewLifecycleOwner, {
            adapter = CarsRVAdapter(requireContext(), this)
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = adapter
            adapter.setListData(it)
        })
    }



    override fun onItemViewClick(position: Int) {

    }

    override fun onStarted() {
        binding.clProgressBarContainer.isVisible = true

    }

    override fun onSuccess() {
        binding.clProgressBarContainer.isGone = true
    }

    override fun onFailure() {
        binding.clProgressBarContainer.isGone = true
        val errorDialog = ErrorDialog(requireContext(), object : OnClickDialog{
            override fun onClicked() {
                viewModel.getCarDetails()
            }
        })
        errorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        errorDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        errorDialog.show()
    }




}