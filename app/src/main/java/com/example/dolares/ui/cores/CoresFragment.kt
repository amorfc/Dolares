package com.example.dolares.ui.cores

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.dolares.R
import com.example.dolares.databinding.CoresFragmentBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoresFragment : Fragment() {

    private val viewModel by viewModel<CoresViewModel>()
    private lateinit var binding: CoresFragmentBinding
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

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

        init()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel.loadingStatus.observe(viewLifecycleOwner,{
            swipeRefreshLayout.isRefreshing = it

            if(it){
                binding.progressBar2.visibility = View.VISIBLE
            }else{
                binding.progressBar2.visibility = View.GONE
            }

        })

        viewModel.snackBarMessage.observe(viewLifecycleOwner,{
            Snackbar.make(swipeRefreshLayout,it,Snackbar.LENGTH_LONG).show()
        })

       swipeRefreshLayout.setOnRefreshListener {
           viewModel.refreshCoreData()
       }

    }


    private fun init(){
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        swipeRefreshLayout = binding.swipeRefreshLayoutCore
    }

}