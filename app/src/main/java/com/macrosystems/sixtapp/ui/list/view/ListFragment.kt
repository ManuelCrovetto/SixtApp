package com.macrosystems.sixtapp.ui.list.view

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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.macrosystems.sixtapp.databinding.CarListBinding
import com.macrosystems.sixtapp.ui.core.ConnectionStatusLiveData
import com.macrosystems.sixtapp.ui.core.dialogs.ErrorDialog
import com.macrosystems.sixtapp.ui.core.dialogs.dialogifcs.OnClickDialog
import com.macrosystems.sixtapp.ui.core.ifcs.AppListener
import com.macrosystems.sixtapp.ui.list.viewmodel.ListFragmentViewModel
import com.macrosystems.sixtapp.ui.viewmodel.factory.ListFragmentViewModelFactory
import com.macrosystems.sixtapp.ui.list.rvadapters.CarsRVAdapter


import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ListFragment : Fragment(), KodeinAware, AppListener {
    override val kodein by kodein()
    private val factory: ListFragmentViewModelFactory by instance()

    private lateinit var viewModel: ListFragmentViewModel

    private var _binding : CarListBinding? = null
    private val binding get () = _binding!!

    private lateinit var connectionStatusLiveData: ConnectionStatusLiveData
    private var isConnected = true


    private lateinit var adapter: CarsRVAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = CarListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, factory).get(ListFragmentViewModel::class.java)

        viewModel.listener = this

        viewModel.getCarDetails()

        viewModel.carDetails.observe(viewLifecycleOwner, {
            adapter = CarsRVAdapter()
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = adapter
            adapter.setListData(it)
        })

        connectionStatusLiveData = ConnectionStatusLiveData(requireContext())
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