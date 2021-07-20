package com.github.newsapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.newsapp.R
import com.github.newsapp.ui.cicerone.NewsNavigator
import com.github.newsapp.ui.presenters.ActivityPresenter
import com.github.newsapp.ui.view.ActivityView
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.launch
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import javax.inject.Inject

/**
 * Активити приложения
 */
class MainActivity : MvpAppCompatActivity(), ActivityView {
    //    private val navigator = AppNavigator(this, R.id.fragment_view)
    private val navigator by lazy {
        NewsNavigator(this, R.id.fragment_view)
    }

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var activityPresenter: ActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
//        NewsApplication.newsApplicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        if (savedInstanceState == null)
            lifecycleScope.launch {
                activityPresenter.replaceFragment()
            }
    }

    override fun onResume() {
        super.onResume()
        activityPresenter.installNavigator(navigator, true)
//        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        activityPresenter.installNavigator(navigator, false)
//        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun navigateToFragment(fragmentToNavigate: Fragment) {
        lifecycleScope.launch {
            activityPresenter.replaceFragment()
        }
//        router.navigateTo(CiceroneScreens.onboardingScreen())
//        router.newChain(CiceroneScreens.onboardingScreen())
//        navigator.
//        supportFragmentManager.beginTransaction()
//            .add(R.id.fragment_view, fragmentToNavigate)
//            .commit()
//        supportFragmentManager.popBackStack()
    }
}