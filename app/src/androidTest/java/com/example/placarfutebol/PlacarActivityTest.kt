package com.example.placarfutebol

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.placarfutebol.R
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


    @RunWith(AndroidJUnit4::class)
    class PlacarActivityTest {

        @get:Rule
        val activityScenarioRule = ActivityScenarioRule(Placar::class.java)

        @Before
        fun setup() {
            Intents.init()
        }

        @After
        fun teardown() {
            Intents.release()
        }

        @Test
        fun testPlacarActivityInitialText() {

            onView(withId(R.id.tempoDePartida)).check(matches(withText("1°")))
            onView(withId(R.id.duracaoDePartida)).check(matches(withText("00:00")))

        }
        @Test
        fun testStartButtonClicked_TextChanges() {
            onView(withId(R.id.btnStartTimer)).check(matches(withText("OFF")))
            onView(withId(R.id.btnStartTimer)).perform(click())
            onView(withId(R.id.btnStartTimer)).check(matches(withText("PAUSE")))
            onView(withId(R.id.btnStartTimer)).perform(click())
            onView(withId(R.id.btnStartTimer)).check(matches(withText("RESUME")))
            onView(withId(R.id.btnStartTimer)).perform(click())
        }

        @Test
        fun testGameEndButtonNotDisplayed_onGameEndNotCalled() {
            onView(withId(R.id.btnGameEnd)).check(matches(not(isDisplayed())))
            onView(withId(R.id.btnGameEnd)).check(matches(not(isDisplayed())))
        }

        @Test
        fun testPenaltiesActivityLaunched_onGameEndWithPenaltiesEnabled() {
            //Intents.intended(hasComponent(PenaltiesActivity::class.java.name))
        }

        @Test
        fun testTextViewUpdates() {
            onView(withId(R.id.tempoDePartida)).check(matches(withText("1°")))
            onView(withId(R.id.duracaoDePartida)).check(matches(withText("00:00")))
            onView(withId(R.id.acrescimosDePartida)).check(matches(withText("")))
        }

        @Test
        fun testCustomNumberPickerInteraction() {
            //onView(withId(R.id.golUm)).perform(swipeUp()) // Increment goal for team one
            //onView(withId(R.id.golUm)).perform(replaceText("5"), closeSoftKeyboard())
            //onView(withId(R.id.golDois)).perform(swipeDown()) // Decrement goal for team two

        }


    }
