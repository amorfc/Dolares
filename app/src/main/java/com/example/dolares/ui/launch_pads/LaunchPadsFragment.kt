package com.example.dolares.ui.launch_pads

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dolares.R

class LaunchPadsFragment : Fragment() {

    companion object {
        fun newInstance() = LaunchPadsFragment()
    }

    private lateinit var viewModel: LaunchPadsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.launch_pads_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LaunchPadsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}