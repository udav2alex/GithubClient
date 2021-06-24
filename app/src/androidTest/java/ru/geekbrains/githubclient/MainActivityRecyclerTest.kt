package ru.geekbrains.githubclient

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.geekbrains.githubclient.ui.adapter.RepositoryRVAdapter
import ru.geekbrains.githubclient.ui.adapter.UserRVAdapter

@RunWith(AndroidJUnit4::class)
class MainActivityRecyclerTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun usersListIsDisplayed() {
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
    }

    @Test
    fun usersListScroll() {
        onView(withId(R.id.rv_users)).perform(
            RecyclerViewActions.scrollTo<UserRVAdapter.ViewHolder>(
                hasDescendant(withText("login15"))
            )
        )
    }

    @Test
    fun usersRepoScroll() {
        onView(withId(R.id.rv_users)).perform(
            RecyclerViewActions.scrollTo<UserRVAdapter.ViewHolder>(
                hasDescendant(withText("login15"))
            )
        )
        onView(withId(R.id.rv_users)).perform(
            RecyclerViewActions.actionOnItem<UserRVAdapter.ViewHolder>(
                hasDescendant(withText("login14")), click()
            )
        )
        onView(withId(R.id.rv_repositories)).perform(
            RecyclerViewActions.scrollTo<RepositoryRVAdapter.ViewHolder>(
                hasDescendant(withText("description 1"))
            )
        )
    }

    @After
    fun close() {
        scenario.close()
    }
}