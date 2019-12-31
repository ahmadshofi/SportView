package com.ahmad.sportview.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmad.sportview.databinding.GridItemViewBinding
import com.ahmad.sportview.network.SportsItem

class PhotoGridAdapter(val onClickListener: OnClickListener) :
        ListAdapter<SportsItem, PhotoGridAdapter.SportPropertyViewHolder>(DiffCalback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportPropertyViewHolder {
        return SportPropertyViewHolder(GridItemViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SportPropertyViewHolder, position: Int) {
        val sportProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(sportProperty)
        }
        holder.bind(sportProperty)
    }


    class SportPropertyViewHolder(private var binding: GridItemViewBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(marsProperty: SportsItem) {
            binding.property = marsProperty
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    companion object DiffCalback : DiffUtil.ItemCallback<SportsItem>() {
        override fun areItemsTheSame(oldItem: SportsItem, newItem: SportsItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SportsItem, newItem: SportsItem): Boolean {
            return oldItem.idSport == newItem.idSport
        }
    }

    class OnClickListener(val clickListener: (sportPropery: SportsItem) -> Unit) {
        fun onClick(sportPropery: SportsItem) = clickListener(sportPropery)
    }
}