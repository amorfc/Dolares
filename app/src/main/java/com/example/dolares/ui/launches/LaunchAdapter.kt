package com.example.dolares.ui.launches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.databinding.LaunchesListItemBinding


class LaunchAdapter(val clickListener: LaunchItemClickListener): ListAdapter<Launch,LaunchAdapter.LaunchViewHolder>(LaunchesDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        return LaunchViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        holder.bind(getItem(position),clickListener)
    }

    class LaunchViewHolder(val binding: LaunchesListItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(launch: Launch,clickListener: LaunchItemClickListener){
            binding.launch = launch
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent:ViewGroup):LaunchViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LaunchesListItemBinding.inflate(layoutInflater,parent,false)
                return LaunchViewHolder(binding)
            }
        }
    }

    class LaunchesDiffCallBack:DiffUtil.ItemCallback<Launch>() {
        override fun areItemsTheSame(oldItem: Launch, newItem: Launch): Boolean {
            return oldItem.md == newItem.md
        }

        override fun areContentsTheSame(oldItem: Launch, newItem: Launch): Boolean {
            return oldItem == newItem
        }
    }
    class LaunchItemClickListener(val clickListener:(launch:Launch)->Unit){
        fun onClick(launch: Launch) = clickListener(launch)
    }
}