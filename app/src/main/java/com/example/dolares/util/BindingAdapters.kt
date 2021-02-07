package com.example.dolares.util

import android.graphics.Color
import android.media.Image
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
import org.w3c.dom.Text


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
@BindingAdapter("setNotificationIcon")
fun ImageView.setNotificationIcon(isNotificationActive:Boolean){
    if(isNotificationActive) {
        this.setImageResource(R.drawable.ic_notification_on)
    }
    else this.setImageResource(R.drawable.ic_notification_off)
}

@BindingAdapter("notificationVisibility")
fun ImageView.notificationVisibility(isUpcoming:Boolean?){
    isUpcoming?.let {
        if(isUpcoming){
            this.visibility = View.VISIBLE
        }else{
            this.visibility = View.GONE
        }
        return
    }
    this.visibility = View.GONE
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
@BindingAdapter("setLinks")
fun TextView.setLinks(link: String?){

    link?.let {

        this.text = link

        return
    }

    this.text = this.context.getText(R.string.no_links)
}

@BindingAdapter("registerStatus")
fun setProgressBarStatus(view: View, status: AuthStatus?) {
    when (status) {
        AuthStatus.LOADING -> view.visibility = View.VISIBLE
        AuthStatus.ERROR -> view.visibility = View.GONE
        else -> view.visibility = View.GONE
    }
}

@BindingAdapter("setRandomImage")
fun ImageView.setRandomImage(itemId:String){

        val linksOfAllRandomPhotos = listOf<String>("https://live.staticflickr.com/65535/49927519643_b43c6d4c44_o.jpg",
            "https://live.staticflickr.com/65535/49927519588_8a39a3994f_o.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQjrJ6Cm3bN5owwGlkdzwZjVCaeYl-0kB4xDw&usqp=CAU",
            "https://www.seekpng.com/png/small/246-2460944_free-png-space-exploration-rocket-png-images-transparent.png",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSPV4Z-V4fvoAMuiQVK7qmyOXudN4VYBH8oYw&usqp=CAU",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmlMElJ0DY0EpxBGW4goaSBgHYLzGhUNm3EQ&usqp=CAU",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ77PQsYw4Fs_TqeQJ9eFZ07bnmf4KlqNEYkQ&usqp=CAU",
            "https://live.staticflickr.com/65535/49928343022_6fb33cbd9c_o.jpg",
            "https://live.staticflickr.com/65535/49934168858_cacb00d790_o.jpg",
            "https://live.staticflickr.com/65535/49934682271_fd6a31becc_o.jpg",
            "https://live.staticflickr.com/65535/49956109906_f88d815772_o.jpg",
            "https://live.staticflickr.com/65535/49956109706_cffa847208_o.jpg",
            "https://live.staticflickr.com/65535/49956109671_859b323ede_o.jpg",
            "https://live.staticflickr.com/65535/49955609618_4cca01d581_o.jpg",
            "https://live.staticflickr.com/65535/49956396622_975c116b71_o.jpg",
            "https://live.staticflickr.com/65535/49955609378_9b77e5c771_o.jpg",
            "https://live.staticflickr.com/65535/49956396262_ef41c1d9b0_o.jpg",
            "https://assets.thehansindia.com/h-upload/feeds/2019/05/03/170678-space.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQFtxSOq-cnN8OZaETVx6aCUfKLIruNq0eUnA&usqp=CAU")
        val randomLink = linksOfAllRandomPhotos.random()

        Glide.with(this.context)
            .load(randomLink)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(this)
        return
}