package com.orgustine.moviesapp.ui;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.orgustine.moviesapp.data.TvShow
import com.orgustine.moviesapp.databinding.MovieItemBinding

class MoviesAdapter(private val listener: OnItemClickListener) :
    ListAdapter<TvShow, MoviesAdapter.ViewHolder>(DiffCallback()) {
    inner class ViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tvShow: TvShow) {
            binding.apply {
                nameTv.text = tvShow.name
                Glide.with(binding.root.context).load(tvShow.image_thumbnail_path).into(imageIv)
                root.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        listener.onItemClick(tvShow.permalink)
                    }
                }
            }
        }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val currentItem = getItem(position)
            holder.bind(currentItem)
        }

    }

    interface OnItemClickListener {
        fun onItemClick(id: String)
    }

    class DiffCallback : DiffUtil.ItemCallback<TvShow>() {
        override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow) =
            oldItem == newItem
    }


