package com.example.dolares.util

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dolares.data.local.model.BaseModel
import com.example.dolares.data.local.model.Capsule
import com.example.dolares.ui.capsules.CapsulesAdapter


@BindingAdapter("submitNewList")
fun setRecyclerViewItem(recyclerView: RecyclerView,items: List<Capsule>?){
        Log.i("BindingAdapters",items.toString())
    items?.let{
        val adapter =  recyclerView.adapter as CapsulesAdapter
        adapter.submitListItems(it)
    }
}