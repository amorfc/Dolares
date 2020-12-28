package com.example.dolares.ui.launches

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.dolares.R
import com.example.dolares.databinding.LaunchesFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LaunchesFragment : Fragment() {

    private val viewModel by viewModel<LaunchesViewModel>()
    private lateinit var binding: LaunchesFragmentBinding

    companion object {
        fun newInstance() = LaunchesFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.launches_fragment,
            container,
            false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        viewModel.getAllLaunches().observe(viewLifecycleOwner,{
            binding.launches.text = it.toString()
        })
    }

}