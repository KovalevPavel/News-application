package com.github.newsapp.ui.fragments.newsDetailsFragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.github.newsapp.R
import com.github.newsapp.data.entities.RecItemExtended
import com.github.newsapp.databinding.FragmentNewsDetailsBinding
import com.github.newsapp.domain.usecases.network.NewsTypes
import com.github.newsapp.ui.fragments.newsDetailsFragment.NewsDetailsFragment.Companion.REC_ID
import com.github.newsapp.ui.fragments.newsDetailsFragment.NewsDetailsFragment.Companion.newInstance
import com.github.newsapp.ui.fragments.newsDetailsFragment.adapters.ViewPagerAdapter
import com.github.newsapp.ui.fragments.newsDetailsFragment.fragmentStates.DetailsNewsState
import com.github.newsapp.ui.fragments.newsDetailsFragment.fragmentStates.StateManyImages
import com.github.newsapp.ui.fragments.newsDetailsFragment.fragmentStates.StateNoImages
import com.github.newsapp.ui.fragments.newsDetailsFragment.fragmentStates.StateOneImage
import com.github.newsapp.ui.presenters.NewsDetailsPresenter
import com.github.newsapp.ui.view.NewsDetailsView
import com.github.newsapp.util.FragmentViewBinding
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

/**
 * Фрагмент, отвечающий за отображение экрана с детальной информацией о новости
 * @property REC_ID идентификатор, по которому из аргументов достается id записи
 * @property newInstance создание фрагмента
 * @property detailsPresenter экземлпяр презентера
 * @property fragmentState переменная, в которой хранится состояние фрагмента
 * @property viewPagerAdapter экземпляр адаптера для ViewPager
 * @property onViewPagerItemChange слушатель события скролла адаптера [viewPagerAdapter]
 */
class NewsDetailsFragment :
    FragmentViewBinding<FragmentNewsDetailsBinding>(FragmentNewsDetailsBinding::inflate),
    NewsDetailsView {

    companion object {
        private const val REC_ID = "recId"

        /**
         * Создание экземпляра фрагмента с переданным значением id записи в аргументах
         * @param recId id передаваемой записи
         */
        fun newInstance(recId: Long): NewsDetailsFragment {
            val args = Bundle()
            args.putLong(REC_ID, recId)
            return NewsDetailsFragment().also {
                it.arguments = args
            }
        }
    }

    @InjectPresenter
    lateinit var detailsPresenter: NewsDetailsPresenter

    @ProvidePresenter
    fun provideDetailsPresenter(): NewsDetailsPresenter {
        return NewsDetailsPresenter(requireArguments().getLong(REC_ID))
    }

    private lateinit var fragmentState: DetailsNewsState
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private val onViewPagerItemChange = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            detailsPresenter.onScrollViewPager(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPagerAdapter = ViewPagerAdapter {
            detailsPresenter.openFullScreenView()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onStart() {
        super.onStart()
        detailsPresenter.updatePreviewImage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binder.viewPager.unregisterOnPageChangeCallback(onViewPagerItemChange)
    }

    /**
     * Инициализация элементов UI
     */
    private fun initUI() {
        binder.viewPager.adapter = viewPagerAdapter
        binder.toolbar.setNavigationOnClickListener {
            detailsPresenter.navigateBack()
        }
        binder.viewPager.registerOnPageChangeCallback(onViewPagerItemChange)
    }

    override fun bindDetails(details: RecItemExtended) {
        setupRecordText(details)
        setState(details)
        setupShareButton(details)
        setupViewPager(details)
    }

    private fun setupRecordText(details: RecItemExtended) {
        binder.apply {
            textNewsHeader.text = details.title
            textNewsDescription.text = details.description
            textPublishedAt.text = details.publishedAtString
        }
    }

    /**
     * Настройка состояния фрагмента
     * @param details объект с детальной информацией о записи
     */
    private fun setState(details: RecItemExtended) {
        fragmentState = when (details.images?.size) {
            null -> StateNoImages(binder, requireActivity() as AppCompatActivity)
            1 -> StateOneImage(binder, requireActivity() as AppCompatActivity)
            else -> StateManyImages(binder, requireActivity() as AppCompatActivity)
        }.also {
            it.prepareFragment()
        }
    }

    /**
     * Настройка кнопки "Поделиться"
     * @param details объект с детальной информацией о записи
     */
    private fun setupShareButton(details: RecItemExtended) {
        val gotShareText = details.shareText !== null
        val shareTextRes = when (details.type) {
            NewsTypes.NEWS -> R.string.btnShare_news
            NewsTypes.ALERT -> R.string.btnShare_alert
        }
        binder.btnShare.apply {
            visibility = if (gotShareText) View.VISIBLE else View.GONE
            setText(shareTextRes)
            setOnClickListener {
                detailsPresenter.shareNews()
            }
        }
    }

    /**
     * Настройка viewPager
     * @param details объект с детальной информацией о записи
     */
    private fun setupViewPager(details: RecItemExtended) {
        binder.apply {
            details.images?.let {
                viewPagerAdapter.updateImageList(it)
                viewPagerAdapter.notifyDataSetChanged()
            }
            dotsIndicator.setViewPager2(binder.viewPager)
        }
    }

    override fun setImageToViewPager(imageID: Int) {
        binder.viewPager.setCurrentItem(imageID, false)
    }

    override fun toggleDetailsLoading(toggle: Boolean) {
        binder.apply {
            loadingProgressBar.visibility = if (toggle) View.VISIBLE else View.GONE
            btnShare.visibility = if (toggle) View.GONE else View.VISIBLE

            /*
             Здесь только скрываем индикатор. Если в будущем он будет необходим, при установке
             состояния фрагмента он будет выведен
             */
            if (toggle) dotsIndicator.visibility = View.GONE
        }
    }


}