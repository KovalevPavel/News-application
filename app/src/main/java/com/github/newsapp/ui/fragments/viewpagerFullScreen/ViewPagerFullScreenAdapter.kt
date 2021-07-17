package com.github.newsapp.ui.fragments.viewpagerFullScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.newsapp.R
import com.github.newsapp.databinding.ItemViewpagerFullscreenBinding
import com.github.newsapp.data.entities.ImageToLoad
import com.squareup.picasso.Picasso

class  ViewPagerFullScreenAdapter (private val onClick: () -> Unit) :
    RecyclerView.Adapter<ViewPagerFullScreenAdapter.ViewPagerFullScreenViewHolder>() {
    private var imageList = emptyList<ImageToLoad>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewPagerFullScreenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binder = ItemViewpagerFullscreenBinding.inflate(inflater, parent, false)
        return ViewPagerFullScreenViewHolder(binder, onClick)
    }

    override fun onBindViewHolder(holder: ViewPagerFullScreenViewHolder, position: Int) {
        holder.bind(imageList[position].url)
    }

    override fun getItemCount() = imageList.size

    fun updateImageList(newList: List<ImageToLoad>) {
        imageList = newList.toMutableList()
    }

    class ViewPagerFullScreenViewHolder(
        private val binder: ItemViewpagerFullscreenBinding,
        onClick: () -> Unit
    ) :
        RecyclerView.ViewHolder(binder.root) {
        init {
            itemView.setOnClickListener {
                onClick()
            }
        }

        fun bind(imageUrl: String) {
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_image_not_supported)
                .into(binder.imgFullScreen)
        }
    }
}