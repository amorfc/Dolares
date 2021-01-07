package com.example.dolares.ui.capsules

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.example.dolares.R
import com.example.dolares.data.local.model.Capsule
import com.example.dolares.databinding.CapsuleDetailsFragmentBinding

class CapsuleDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = CapsuleDetailsFragment()
    }

    private lateinit var viewModel: CapsuleDetailsViewModel
    private lateinit var capsule:Capsule
    private lateinit var binding:CapsuleDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  DataBindingUtil.inflate(inflater,R.layout.capsule_details_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val arguments = CapsuleDetailsFragmentArgs.fromBundle(arguments!!)
        capsule = arguments.parcelableCapsule

        val viewModelProvider = CapsuleDetailsViewModel.Factory(capsule)

        viewModel = ViewModelProvider(this,viewModelProvider).get(CapsuleDetailsViewModel::class.java)

        binding.selectedCapsule = viewModel.capsule

    }

}