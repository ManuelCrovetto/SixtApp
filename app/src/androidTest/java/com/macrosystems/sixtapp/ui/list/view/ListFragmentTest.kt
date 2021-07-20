package com.macrosystems.sixtapp.ui.list.view


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.macrosystems.sixtapp.R
import com.macrosystems.sixtapp.ui.core.MainActivity
import com.macrosystems.sixtapp.ui.list.rvadapters.CarViewHolder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ListFragmentTest {

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    /*
     * Recyclerview comes into view ->
     * */
    @Test
    fun test_isListFragmentVisible_onLaunch() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

    /*
    * Item in the recyclerView is clicked ->
    * */
    @Test
    fun test_selectItemFromList_isExpandableInfoVisible(){
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<CarViewHolder>(0, click()))
    }



}