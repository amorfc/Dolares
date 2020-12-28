package com.example.dolares.ui.launch_pads

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.dolares.R
import com.example.dolares.databinding.LaunchPadsFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LaunchPadsFragment : Fragment() {

    private val viewModel by viewModel<LaunchPadsViewModel>()
    private lateinit var binding: LaunchPadsFragmentBinding

    companion object {
        fun newInstance() = LaunchPadsFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.launch_pads_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getAllLaunchPads().observe(viewLifecycleOwner,{
            binding.launchPads.text = it.toString()
        })

    }

}