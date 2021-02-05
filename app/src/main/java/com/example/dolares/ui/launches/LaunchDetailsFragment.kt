package com.example.dolares.ui.launches

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.dolares.R
import com.example.dolares.data.repository.LaunchDetailsRepository
import com.example.dolares.databinding.LaunchDetailsFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LaunchDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = LaunchDetailsFragment()
    }

    private val viewModel by viewModel<LaunchDetailsViewModel>()
    private lateinit var binding: LaunchDetailsFragmentBinding
    private lateinit var launchId:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.launch_details_fragment, container, false)

        launchId = LaunchDetailsFragmentArgs.fromBundle(requireArguments()).launchId
        binding.lifecycleOwner = this

        return  binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
        viewModel.fetchSelectedLaunch(launchId)
        //For testing
    }
}