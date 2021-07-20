package com.github.newsapp.ui.cicerone

import androidx.fragment.app.Fragment
import com.github.newsapp.ui.main.MainActivity
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Имплементация [AppNavigator], позволяющая осуществить circular revel анимацию для фрагмента
 */
class NewsNavigator(
    activity: MainActivity,
    containerId: Int,
) : AppNavigator(activity, containerId) {

    override fun applyCommand(command: Command) {
        if (command is StartUsing) startUsing(command)
        else super.applyCommand(command)
    }

    private fun startUsing(command: StartUsing) {
        val screen = command.screen
        if (screen !is FragmentScreen) return
        commitNewsFragmentScreen(screen)
    }

    private fun commitNewsFragmentScreen(screen: FragmentScreen) {
        val newFragment = screen.createFragment(fragmentFactory)
        val oldFragment = fragmentManager.fragments.last()
        val transaction = fragmentManager.beginTransaction()
        transaction.setReorderingAllowed(true)
        setupFragmentTransaction(
            screen,
            transaction,
            fragmentManager.findFragmentById(containerId),
            newFragment
        )
        transaction.add(containerId, newFragment, screen.screenKey)
            .runOnCommit {
                removeFragment(oldFragment)
            }
            .commit()
    }

    /**
     * Функция удаления onboarding-фрагмента из [fragmentManager]. Задержка введена для того, чтобы успела отыграться анимация
     */
    private fun removeFragment(fragment: Fragment) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000L)
            fragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
            fragmentManager.popBackStack()
        }
    }
}