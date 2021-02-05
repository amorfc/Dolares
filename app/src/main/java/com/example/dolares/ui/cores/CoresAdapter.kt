package com.example.dolares.ui.cores

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dolares.data.local.model.Core
import com.example.dolares.databinding.CoresListItemBinding

class CoresAdapter(val clickListener: CoreItemClickListener) : ListAdapter<Core, CoresAdapter.CoreViewHolder>(CoresDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoreViewHolder {
        return CoreViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CoreViewHolder, position: Int) {
        holder.bind(getItem(position),clickListener)
    }



    class CoreViewHolder(val binding: CoresListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(core: Core,clickListener: CoreItemClickListener) {

            binding.core = core
            binding.clickListener = clickListener
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): CoreViewHolder {

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CoresListItemBinding.inflate(layoutInflater, parent, false)
                return CoreViewHolder(binding)

            }
        }
    }

    class CoresDiffCallBack : DiffUtil.ItemCallback<Core>() {
        override fun areItemsTheSame(oldItem: Core, newItem: Core): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Core, newItem: Core): Boolean {
            return oldItem == newItem
        }
    }
    class CoreItemClickListener(val clickListener:(core:Core)->Unit){
        fun onClick(core: Core) = clickListener(core)
    }
}