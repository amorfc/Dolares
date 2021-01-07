package com.example.dolares.ui.cores

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dolares.R

class CoreDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = CoreDetailsFragment()
    }

    private lateinit var viewModel: CoreDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.core_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CoreDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}