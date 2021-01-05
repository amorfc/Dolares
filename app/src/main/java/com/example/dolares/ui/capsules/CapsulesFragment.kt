package com.example.dolares.ui.capsules

import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.dolares.R
import com.example.dolares.data.local.converters.LaunchSiteConverter
import com.example.dolares.data.local.model.Capsule
import com.example.dolares.databinding.CapsulesFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CapsulesFragment : Fragment() {

    private val TAG = "Capsules Fragment"

    private val viewModel by viewModel<CapsulesViewModel>()

    lateinit var binding: CapsulesFragmentBinding

    companion object {
        fun newInstance() = CapsulesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.capsules_fragment,
            container,
            false
        )

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel._capsulesDataLoadingStatus.observe(viewLifecycleOwner, {
            //Capsules Data Loading Status
        })

        viewModel._capsulesSnackBarMessage.observe(viewLifecycleOwner, {
            //Capsules SnackBar Message
        })
    }

}