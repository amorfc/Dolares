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


@BindingAdapter("submitNewList")
fun setRecyclerViewItem(recyclerView: RecyclerView,items: List<BaseModel>?){

    Log.i("BindingAdapters",items.toString())

        items?.let{
            var item:BaseModel? = null
            if(items.size > 0) item = items[0]
            when (item) {
                is Capsule -> {
                    recyclerView.adapter = GenericAdapter(MODEL_TYPE.Capsule)
                    val adapter = recyclerView.adapter as GenericAdapter
                    adapter.submitListItems(it)
                }
                is LaunchPad -> {
                    recyclerView.adapter = GenericAdapter(MODEL_TYPE.LaunchPad)
                    val adapter = recyclerView.adapter as GenericAdapter
                    adapter.submitListItems(it)
                }
                is Launch -> {
                    recyclerView.adapter = GenericAdapter(MODEL_TYPE.Launch)
                    val adapter = recyclerView.adapter as GenericAdapter
                    adapter.submitListItems(it)
                }
                is Core -> {
                    recyclerView.adapter = GenericAdapter(MODEL_TYPE.Core)
                    val adapter = recyclerView.adapter as GenericAdapter
                    adapter.submitListItems(it)
            }
        }
    }
}