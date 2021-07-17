package com.github.newsapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.newsapp.R
import com.github.newsapp.app.NewsApplication
import com.github.newsapp.ui.presenters.ActivityPresenter
import com.github.newsapp.ui.view.ActivityView
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import javax.inject.Inject

/**
 * Активити приложения
 */
class MainActivity : MvpAppCompatActivity(), ActivityView {
    private val navigator = AppNavigator(this, R.id.fragment_view)

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @InjectPresenter
    lateinit var activityPresenter: ActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        NewsApplication.newsApplicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        if (savedInstanceState == null)
            activityPresenter.replaceFragment()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun navigateToFragment(fragmentToNavigate: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_view, fragmentToNavigate)
            .commit()
        supportFragmentManager.popBackStack()
    }
}