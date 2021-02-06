package com.example.dolares.util

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dolares.R
import com.example.dolares.data.local.model.Capsule
import com.example.dolares.data.local.model.Core
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.data.repository.AuthStatus
import com.example.dolares.ui.capsules.CapsulesAdapter
import com.example.dolares.ui.cores.CoresAdapter
import com.example.dolares.ui.launches.LaunchAdapter
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size


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

@BindingAdapter("setLaunchImage")
fun ImageView.setLaunchImage(launch: Launch?) {

    launch?.links?.flickr?.original?.let { originalList ->

        if (!originalList.isNullOrEmpty()) {

            Glide.with(this.context)
                .load(originalList.first())
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(this)
            return
        }
    }

    this.setImageResource(R.drawable.ic_broken_image)

}

@BindingAdapter("setSuccess")
fun TextView.setSuccess(isSuccess:Boolean?){

    isSuccess?.let {
        if(it){
            this.text = this.context.getString(R.string.launch_success_desc)
        }else{
            this.text = this.context.getString(R.string.launch_failure_desc)
        }
        return
    }

    this.text = this.context.getString(R.string.launch_failure_or_success_desc_not_found)

}

@BindingAdapter("setListItemLaunchImage")
fun ImageView.setListItemLaunchImage(launch: Launch?) {

    launch?.links?.patch?.small?.let { smallUrl ->

            Glide.with(this.context)
                .load(smallUrl)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(this)
            return
    }

    this.setImageResource(R.drawable.ic_broken_image)

}

@BindingAdapter("triggerKonfetti")
fun KonfettiView.triggerKonfetti(time: Long) {

    when (time) {
        0L -> {
            this.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.Square, Shape.Circle)
                .addSizes(Size(12))
                .setPosition(-50f, this.width + 50f, -50f, -50f)
                .streamFor(300, 5000L)
        }
    }
}

@BindingAdapter("setTimerDateFormat")
fun TextView.setTimerDateFormat(timer: Long) {

    var inSeconds = timer
    val day = inSeconds / 86400
    inSeconds %= 86400

    val hour = inSeconds / 3600
    inSeconds %= 3600

    val minutes = inSeconds / 60
    inSeconds %= 60

    if (timer == 0L) {
        this.text = this.context.getString(R.string.launched_launch_timer_text)
        return
    }

    this.text =
        this.context.getString(R.string.upcoming_launch_date_format, day, hour, minutes, inSeconds)

}

@BindingAdapter("registerStatus")
fun setProgressBarStatus(view: View, status: AuthStatus?) {
    when (status) {
        AuthStatus.LOADING -> view.visibility = View.VISIBLE
        AuthStatus.ERROR -> view.visibility = View.GONE
        else -> view.visibility = View.GONE
    }
}

@BindingAdapter("setTimer")
fun setTimerText(tw: TextView, time: Long) {

    when (time) {

        0L -> {
            tw.visibility = View.GONE
        }
        else -> {
            tw.visibility = View.VISIBLE
            tw.text = time.toString()
        }
    }

}