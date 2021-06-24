package ru.geekbrains.githubclient

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.geekbrains.githubclient.mvp.model.entity.GithubRepository
import ru.geekbrains.githubclient.ui.fragments.ShowRepositoryFragment

@RunWith(AndroidJUnit4::class)
class ShowRepositoryFragmentTest {

    private lateinit var scenario: FragmentScenario<ShowRepositoryFragment>

    @Before
    fun setup() {
        val data = GithubRepository("id", "name", "description", 100)
        val args = bundleOf(ShowRepositoryFragment.BUNDLE_KEY_USER_REPOSITORY to data)
        scenario = launchFragmentInContainer(args)
    }

    @Test
    fun fragmentNotNull() {
        scenario.onFragment {
            assertNotNull(it)
        }
    }

    @Test
    fun viewsShowData() {
        onView(withId(R.id.tv_repo_name)).check(matches(withText("name")))
        onView(withId(R.id.tv_repo_description)).check(matches(withText("description")))
        onView(withId(R.id.tv_repo_fork_count)).check(matches(withText("100 forks")))
    }
}