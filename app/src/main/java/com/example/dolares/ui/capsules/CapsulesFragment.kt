package com.example.dolares.ui.capsules

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.dolares.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class CapsulesFragment : Fragment() {

    private val viewModel by viewModel<CapsulesViewModel>()

    companion object {
        fun newInstance() = CapsulesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.capsules_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.allCapsules.observe(viewLifecycleOwner, {
            //Data is done
        })
    }

}