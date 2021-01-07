package com.example.dolares.util

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dolares.data.local.model.Capsule
import com.example.dolares.data.local.model.Core
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.ui.capsules.CapsulesAdapter
import com.example.dolares.ui.cores.CoresAdapter
import com.example.dolares.ui.launches.LaunchAdapter


@BindingAdapter("submitNewList")
fun setRecyclerViewItem(recyclerView: RecyclerView,items: List<Any>?){

    items?.let {
        when(it[0]){
            is Capsule->{
                val adapter:CapsulesAdapter = recyclerView.adapter as CapsulesAdapter
                adapter.submitListItems(items as List<Capsule>)
            }
            is Core->{
                val adapter = recyclerView.adapter as CoresAdapter
                adapter.submitList(items as List<Core>)
            }
            is Launch->{
                val adapter = recyclerView.adapter as LaunchAdapter
                adapter.submitList(items as List<Launch>)
            }
        }
    }

}