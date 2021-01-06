package com.example.dolares.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dolares.data.local.model.BaseModel
import com.example.dolares.data.local.model.Capsule
import com.example.dolares.data.local.model.Core
import com.example.dolares.data.local.model.LaunchPad
import com.example.dolares.data.local.model.launch.Launch
import com.example.dolares.databinding.CapsulesListItemBinding
import com.example.dolares.databinding.CoresListItemBinding
import com.example.dolares.databinding.LaunchPadsListItemBinding
import com.example.dolares.databinding.LaunchesListItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class MODEL_TYPE { Capsule, Launch, Core, LaunchPad }

class GenericAdapter(val modelType: MODEL_TYPE) :
    ListAdapter<GenericAdapter.DataItem, RecyclerView.ViewHolder>(ItemDiffCallback()) {
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (modelType) {
            MODEL_TYPE.Capsule -> CapsuleViewHolder.from(parent)
            MODEL_TYPE.LaunchPad -> LaunchPadsViewHolder.from(parent)
            MODEL_TYPE.Launch -> LaunchViewHolder.from(parent)
            MODEL_TYPE.Core -> CoreViewHolder.from(parent)
            else -> throw Exception("ViewHolder Error")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CapsuleViewHolder -> {
                val item = getItem(position) as DataItem.ModelItem<*>
                if (item.model is Capsule) holder.bind(capsule = item.model)
            }
            is LaunchPadsViewHolder -> {
                val item = getItem(position) as DataItem.ModelItem<*>
                if (item.model is LaunchPad) holder.bind(launchPad = item.model)
            }
            is LaunchViewHolder -> {
                val item = getItem(position) as DataItem.ModelItem<*>
                if (item.model is Launch) holder.bind(launch = item.model)
            }
            is CoreViewHolder -> {
                val item = getItem(position) as DataItem.ModelItem<*>
                if (item.model is Core) holder.bind(core = item.model)
            }
        }
    }


    fun <T : BaseModel> submitListItems(list: List<T>?) {
        adapterScope.launch {
            val items = list?.map { DataItem.ModelItem(it) }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }


    class CapsuleViewHolder private constructor(private val capsulesListItemBinding: CapsulesListItemBinding) :
        RecyclerView.ViewHolder(capsulesListItemBinding.root) {

        fun bind(capsule: Capsule) {
            capsulesListItemBinding.capsule = capsule
            capsulesListItemBinding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): CapsuleViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = CapsulesListItemBinding.inflate(layoutInflater, parent, false)

                return CapsuleViewHolder(binding)
            }
        }

    }

    class LaunchPadsViewHolder private constructor(private val launchPadsListItemBinding: LaunchPadsListItemBinding) :
        RecyclerView.ViewHolder(launchPadsListItemBinding.root) {

        fun bind(launchPad: LaunchPad) {
            launchPadsListItemBinding.launchPad = launchPad
            launchPadsListItemBinding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): LaunchPadsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = LaunchPadsListItemBinding.inflate(layoutInflater, parent, false)

                return LaunchPadsViewHolder(binding)
            }
        }

    }

    class LaunchViewHolder private constructor(private val launchesListItemBinding: LaunchesListItemBinding) :
        RecyclerView.ViewHolder(launchesListItemBinding.root) {

        fun bind(launch: Launch) {
            launchesListItemBinding.launch = launch
            launchesListItemBinding.executePendingBindings()

        }
        companion object {
            fun from(parent: ViewGroup): LaunchViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = LaunchesListItemBinding.inflate(layoutInflater, parent, false)

                return LaunchViewHolder(binding)
            }
        }
    }

    class CoreViewHolder private constructor(private val coresListItemBinding: CoresListItemBinding) :
        RecyclerView.ViewHolder(coresListItemBinding.root) {

        fun bind(core: Core) {
            coresListItemBinding.core = core
            coresListItemBinding.executePendingBindings()

        }
        companion object {
            fun from(parent: ViewGroup): CoreViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = CoresListItemBinding.inflate(layoutInflater, parent, false)

                return CoreViewHolder(binding)
            }
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }

    sealed class DataItem {
        data class ModelItem<T : BaseModel>(val model: T) : DataItem() {
            override val id: String?
                get() = model.id
        }

        abstract val id: String?

    }
}



