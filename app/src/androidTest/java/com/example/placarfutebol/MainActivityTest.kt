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
class MainActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testMainActivityInitialText() {

        onView(withId(R.id.btnCriarPartida)).check(matches(withText("Criar Partida")))
        onView(withId(R.id.historico)).check(matches(withText("Histórico")))

    }

    @Test
    fun testCriacaoPartidaDialog() {

            onView(withId(R.id.btnCriarPartida)).perform(click())

            Thread.sleep(2000)


            onView(withId(R.id.editNomeTimeUm)).perform(typeText("Time Um"))
            onView(withId(R.id.editNomeTimeDois)).perform(typeText("Time Dois"))

            onView(withId(R.id.switchProrrogacao)).perform(click())
            onView(withId(R.id.switchPenalties)).perform(click())

            onView(withText("OK")).perform(click())

            Intents.intended(hasComponent(Placar::class.java.name))

    }


}
