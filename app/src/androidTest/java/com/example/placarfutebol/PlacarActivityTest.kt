package com.example.placarfutebol

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.placarfutebol.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


    @RunWith(AndroidJUnit4::class)
    class PlacarActivityTest {

        @get:Rule
        val activityScenarioRule = ActivityScenarioRule(Placar::class.java)

        @Test
        fun testPlacarActivity() {

            onView(withId(R.id.tempoDePartida)).check(matches(withText("1°")))
            onView(withId(R.id.duracaoDePartida)).check(matches(withText("00:00")))
            onView(withId(R.id.btnStartTimer)).check(matches(withText("OFF")))
            onView(withId(R.id.btnStartTimer)).perform(click())
            onView(withId(R.id.btnStartTimer)).check(matches(withText("PAUSE")))
            onView(withId(R.id.btnStartTimer)).perform(click())
            onView(withId(R.id.btnStartTimer)).check(matches(withText("RESUME")))
            onView(withId(R.id.btnStartTimer)).perform(click())

            // Add more actions and assertions to test different functionality
        }
    }
