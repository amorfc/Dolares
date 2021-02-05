package com.example.dolares.ui.capsules

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dolares.data.local.model.Capsule
import com.example.dolares.databinding.CapsulesListItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CapsulesAdapter(val clickListener: CapsulesClickListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(CapsulesDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder.from(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val item = getItem(position) as DataItem.CapsuleItem
                holder.bind(capsule = item.capsule,clickListener)
            }
        }
    }


    fun submitListItems(list: List<Capsule>?) {
        adapterScope.launch {
            val items = list?.map { DataItem.CapsuleItem(it) }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    class ViewHolder private constructor(private val capsulesListItemBinding: CapsulesListItemBinding) :
        RecyclerView.ViewHolder(capsulesListItemBinding.root) {

        fun bind(capsule: Capsule,clickListener: CapsulesClickListener) {
            capsulesListItemBinding.capsule = capsule
            capsulesListItemBinding.clickListener = clickListener
            capsulesListItemBinding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = CapsulesListItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                return ViewHolder(binding)
            }
        }

    }

    class CapsulesClickListener(val clickListener: (capsuleData: Capsule) -> Unit) {
        fun onClick(capsule: Capsule) = clickListener(capsule)
    }
}


class CapsulesDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}


sealed class DataItem {
    data class CapsuleItem(val capsule: Capsule) : DataItem() {
        override val id: String
            get() = capsule.id
    }

    abstract val id: String
}

