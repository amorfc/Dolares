package com.example.dolares.util

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dolares.data.local.model.BaseModel
import com.example.dolares.data.local.model.Capsule
import com.example.dolares.data.local.model.Core
import com.example.dolares.data.local.model.LaunchPad
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.ui.GenericAdapter
import com.example.dolares.ui.MODEL_TYPE
import com.example.dolares.ui.capsules.CapsulesAdapter


@BindingAdapter("submitNewList")
fun setRecyclerViewItem(recyclerView: RecyclerView,items: List<Any>?){

    items?.let {
        when(it[0]){
            is Capsule->{
                val adapter:CapsulesAdapter = recyclerView.adapter as CapsulesAdapter
                adapter.submitListItems(items as List<Capsule>)
            }
        }
    }

}