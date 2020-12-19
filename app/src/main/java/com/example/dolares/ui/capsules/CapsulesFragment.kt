package com.example.dolares.ui.capsules

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dolares.R

class CapsulesFragment : Fragment() {

    companion object {
        fun newInstance() = CapsulesFragment()
    }

    private lateinit var viewModel: CapsulesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.capsules_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CapsulesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}