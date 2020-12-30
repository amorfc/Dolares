package com.example.dolares.ui.cores

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.dolares.R
import com.example.dolares.databinding.CoresFragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoresFragment : Fragment() {

    private val viewModel by viewModel<CoresViewModel>()
    private lateinit var binding: CoresFragmentBinding

    companion object {
        fun newInstance() = CoresFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.cores_fragment,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        viewModel.getAllCores().observe(viewLifecycleOwner,{
//            binding.coresText.text = it.toString()
//        })

    }

}