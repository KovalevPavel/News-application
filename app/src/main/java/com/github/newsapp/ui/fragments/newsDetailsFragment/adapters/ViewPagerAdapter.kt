package com.github.newsapp.ui.fragments.newsDetailsFragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.newsapp.R
import com.github.newsapp.databinding.ItemDetailsImageBinding
import com.github.newsapp.data.entities.ImageToLoad
import com.squareup.picasso.Picasso

class ViewPagerAdapter(private val onClick: () -> Unit) :
    RecyclerView.Adapter<ViewPagerAdapter.ImageDetailsViewHolder>() {

    private var imageList = emptyList<ImageToLoad>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageDetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binder = ItemDetailsImageBinding.inflate(inflater, parent, false)
        return ImageDetailsViewHolder(binder, onClick)
    }

    override fun onBindViewHolder(holder: ImageDetailsViewHolder, position: Int) {
        holder.bind(imageList[position].url)
    }

    override fun getItemCount() = imageList.size

    fun updateImageList(newList: List<ImageToLoad>) {
        imageList = newList
    }

    class ImageDetailsViewHolder(private val binder: ItemDetailsImageBinding, onClick: () -> Unit) :
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
                .into(binder.imageDetails)
        }
    }
}