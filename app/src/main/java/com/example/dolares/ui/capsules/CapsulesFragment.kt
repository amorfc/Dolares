package com.example.dolares.ui.capsules

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.dolares.R
import com.example.dolares.data.local.model.Capsule
import com.example.dolares.databinding.CapsulesFragmentBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class CapsulesFragment : Fragment() {

    private val TAG = "Capsules Fragment"

    private val viewModel by viewModel<CapsulesViewModel>()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
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

        init()

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loadingStatus.observe(viewLifecycleOwner, {

            swipeRefreshLayout.isRefreshing = it

            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        viewModel.selectedItem.observe(viewLifecycleOwner, {
            it?.let {
                navigateCapsuleDetails(it)
                viewModel.doneNavigateDetailsScreen()
            }

        })

        viewModel.snackBarMessage.observe(viewLifecycleOwner, {
            it?.let {
                Snackbar.make(swipeRefreshLayout, it, Snackbar.LENGTH_SHORT).show()
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            Log.i(TAG, "onRefresh called from SwipeRefreshLayout")
            viewModel.refreshData()
        }
    }

    private fun init() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        swipeRefreshLayout = binding.swipeRefreshLayout
        adapterInit()
    }

    private fun adapterInit() {
        val clickListener = CapsulesAdapter.CapsulesClickListener { capsule ->
            viewModel.navigateSelectedItemDetailsScreen(capsule)
        }
        val adapter = CapsulesAdapter(clickListener)
        binding.capsulesRV.adapter = adapter
    }

    private fun navigateCapsuleDetails(capsule: Capsule?) {
        capsule?.let {
            findNavController().navigate(
                CapsulesFragmentDirections.actionCapsulesFragmentToCapsuleDetailsFragment(
                    parcelableCapsule = it
                )
            )
            return
        }
        Snackbar.make(swipeRefreshLayout, "Something Went Wrong", Snackbar.LENGTH_SHORT).show()
    }

}