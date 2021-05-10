package com.github.newsapp.ui.fragments.onboardingFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.newsapp.databinding.ItemOnboardingBinding

class OnboardingViewPagerAdapter :
    RecyclerView.Adapter<OnboardingViewPagerAdapter.OnboardingViewHolder>() {
    var onboardingScreensList = listOf(
        OnboardingItem(0),
        OnboardingItem(1),
        OnboardingItem(2)
    )
    private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binder = ItemOnboardingBinding.inflate(inflater, parent, false)
        return OnboardingViewHolder(binder)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(onboardingScreensList[position])
    }

    override fun getItemCount() = onboardingScreensList.size

    class OnboardingViewHolder(private val binder: ItemOnboardingBinding) :
        RecyclerView.ViewHolder(binder.root) {

        fun bind(item: OnboardingItem) {
            val headerString = "${binder.onboardingTextHeader.text} ${item.id}"
            binder.onboardingTextHeader.text = headerString
        }
    }
}