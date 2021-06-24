package ru.geekbrains.githubclient

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.geekbrains.githubclient.ui.fragments.UsersFragment

@RunWith(AndroidJUnit4::class)
class UsersFragmentEspressoTest {

    private lateinit var scenario: FragmentScenario<UsersFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer()
    }

    @Test
    fun fragmentNotNull() {
        scenario.onFragment {
            assertNotNull(it)
        }
    }
}