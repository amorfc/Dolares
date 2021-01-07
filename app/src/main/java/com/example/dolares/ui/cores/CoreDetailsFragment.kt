package com.example.dolares.ui.cores

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.dolares.R
import com.example.dolares.data.local.model.Core
import com.example.dolares.databinding.CoreDetailsFragmentBinding

class CoreDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = CoreDetailsFragment()
    }

    private lateinit var viewModel: CoreDetailsViewModel
    private lateinit var binding: CoreDetailsFragmentBinding
    private lateinit var argumentCore: Core

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.core_details_fragment, container, false)

        val arguments = CoreDetailsFragmentArgs.fromBundle(arguments!!)
        binding.lifecycleOwner = this

        argumentCore = arguments.parcelableCore

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModelProvider = CoreDetailsViewModel.Factory(argumentCore)
        viewModel = ViewModelProvider(this,viewModelProvider).get(CoreDetailsViewModel::class.java)
        binding.viewModel = viewModel


    }

}