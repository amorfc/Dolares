package com.example.dolares.util

import android.content.res.Resources
import android.provider.Settings.Global.getString
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dolares.data.local.model.Capsule
import com.example.dolares.data.local.model.Core
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.data.repository.AuthStatus
import com.example.dolares.ui.capsules.CapsulesAdapter
import com.example.dolares.ui.cores.CoresAdapter
import com.example.dolares.ui.launches.LaunchAdapter
import com.example.dolares.R



@BindingAdapter("submitNewList")
fun setRecyclerViewItem(recyclerView: RecyclerView, items: List<Any>?) {

    if (items != null) {
        if (items.isNotEmpty()) {

            items.let {
                when (it.first()) {
                    is Capsule -> {
                        val adapter: CapsulesAdapter = recyclerView.adapter as CapsulesAdapter
                        adapter.submitListItems(items as List<Capsule>)
                    }
                    is Core -> {
                        val adapter = recyclerView.adapter as CoresAdapter
                        adapter.submitList(items as List<Core>)
                    }
                    is Launch -> {
                        val adapter = recyclerView.adapter as LaunchAdapter
                        adapter.submitList(items as List<Launch>)
                    }
                }
            }
        }
    }

}

@BindingAdapter("setTimerDateFormat")
fun TextView.setTimerDateFormat(timer:Long){

    var inSeconds = timer
    val day = inSeconds/86400
    inSeconds %= 86400

    val hour = inSeconds/3600
    inSeconds %= 3600

    val minutes = inSeconds/60
    inSeconds %= 60

    if(inSeconds == 0L ){
        this.text = this.context.getString(R.string.launched_launch_timer_text)
        return
    }

    this.text = this.context.getString(R.string.upcoming_launch_date_format,day,hour,minutes,inSeconds)

}

@BindingAdapter("registerStatus")
fun setProgressBarStatus(view: View, status: AuthStatus?) {
    when (status) {
        AuthStatus.LOADING -> view.visibility = View.VISIBLE
        AuthStatus.ERROR-> view.visibility = View.GONE
        else -> view.visibility = View.GONE
    }
}

@BindingAdapter("setTimer")
fun setTimerText(tw:TextView,time:Long){

    when(time){

        0L ->{
            tw.visibility = View.GONE
        }
        else->{
            tw.visibility = View.VISIBLE
            tw.text = time.toString()
        }
    }

}