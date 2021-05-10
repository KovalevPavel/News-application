package com.github.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.newsapp.NewsApplication
import com.github.newsapp.R
import com.github.newsapp.presenters.ActivityPresenter
import com.github.newsapp.ui.view.ActivityView
import com.github.newsapp.util.loggingDebug
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class MainActivity : MvpAppCompatActivity(), ActivityView {
    private val navigator = AppNavigator(this, R.id.fragment_view)

    private val navigatorHolder = NewsApplication.instance.navigatorHolder

    @InjectPresenter
    lateinit var activityPresenter: ActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        activityPresenter.loadLaunchInfo()
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
        loggingDebug("adding")
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_view, fragmentToNavigate)
            .commit()
        supportFragmentManager.popBackStack()
    }
}