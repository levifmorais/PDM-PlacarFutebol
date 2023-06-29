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

            onView(withText("Pronto")).perform(click())

            Intents.intended(hasComponent(Placar::class.java.name))

    }

@Test
        fun semUmDosTimes() {

            //teste para verificar se existe tratamento para quando informações não forem preenchidas

            onView(withId(R.id.btnCriarPartida)).perform(click())
            onView(withId(R.id.tempoTextNumber)).check(matches(isDisplayed()))
            onView(withId(R.id.tempoTextNumber)).perform(clearText(), typeText("90"))
            onView(withId(R.id.editNomeTimeUm)).perform(clearText(), typeText("Fortaleza"))
            onView(withId(R.id.editNomeTimeDois)).perform(clearText())
            onView(withText("Pronto")).perform(click())
            onView(withText("Preencha todos os campos")).check(matches(isDisplayed()))

        }
        @Test
        fun prorrogacao() {

            //teste para verificar se a prorrogação funciona

            val tempoTotal = 1
            val metadeTempoEmMilissegundos = (tempoTotal * 60 * 1000) / 2 // Converter metade do tempo para milissegundos

            onView(withId(R.id.btnCriarPartida)).perform(click())
            onView(withId(R.id.tempoTextNumber)).check(matches(isDisplayed()))
            onView(withId(R.id.tempoTextNumber)).perform(clearText(), typeText(tempoTotal.toString()))
            onView(withId(R.id.editNomeTimeUm)).perform(clearText(), typeText("Fortaleza"))
            onView(withId(R.id.editNomeTimeDois)).perform(clearText(), typeText("Ceara"))
            onView(withId(R.id.switchProrrogacao)).check(matches(isChecked()))
            onView(withText("Pronto")).perform(click())
            onView(withId(R.id.btnStartTimer)).check(matches(isDisplayed()))
            Thread.sleep(3000)
            onView(withId(R.id.btnStartTimer)).perform(click())
            Thread.sleep(metadeTempoEmMilissegundos.toLong())
            onView(withId(R.id.btnIntervalo)).perform(click())
            onView(withId(R.id.btnStartTimer)).perform(click())
            Thread.sleep(metadeTempoEmMilissegundos.toLong())
            onView(withId(R.id.btnIntervalo)).perform(click())
            onView(withId(R.id.btnStartTimer)).perform(click())
            Thread.sleep(metadeTempoEmMilissegundos.toLong())
            onView(withId(R.id.btnCriarPartida)).check(matches(isDisplayed()))


        }

        @Test
        fun penalties() {

            //teste para verificar se os penaltis funcionam

            val tempoTotal = 1
            val metadeTempoEmMilissegundos = (tempoTotal * 60 * 1000) / 2 // Converter metade do tempo para milissegundos

            onView(withId(R.id.btnCriarPartida)).perform(click())
            onView(withId(R.id.tempoTextNumber)).check(matches(isDisplayed()))
            onView(withId(R.id.tempoTextNumber)).perform(clearText(), typeText(tempoTotal.toString()))
            onView(withId(R.id.editNomeTimeUm)).perform(clearText(), typeText("Fortaleza"))
            onView(withId(R.id.editNomeTimeDois)).perform(clearText(), typeText("Ceara"))
            onView(withId(R.id.switchProrrogacao)).check(matches(isChecked()))
            onView(withId(R.id.switchPenalties)).check(matches(isChecked()))
            onView(withText("Pronto")).perform(click())
            onView(withId(R.id.btnStartTimer)).check(matches(isDisplayed()))
            Thread.sleep(3000)
            onView(withId(R.id.btnStartTimer)).perform(click())
            Thread.sleep(metadeTempoEmMilissegundos.toLong())
            onView(withId(R.id.btnIntervalo)).perform(click())
            onView(withId(R.id.btnStartTimer)).perform(click())
            Thread.sleep(metadeTempoEmMilissegundos.toLong())
            onView(withId(R.id.btnIntervalo)).perform(click())
            onView(withId(R.id.btnStartTimer)).perform(click())
            Thread.sleep(metadeTempoEmMilissegundos.toLong())
            onView(withId(R.id.textViewTeamOne)).check(matches(isDisplayed()))
            onView(withId(R.id.buttonTeamOneGoal)).perform(click())
            onView(withId(R.id.buttonTeamTwoMiss)).perform(click())
            onView(withId(R.id.buttonTeamOneGoal)).perform(click())
            onView(withId(R.id.buttonTeamTwoMiss)).perform(click())
            onView(withId(R.id.buttonTeamOneGoal)).perform(click())
            onView(withId(R.id.buttonTeamTwoMiss)).perform(click())
            onView(withId(R.id.buttonTeamOneGoal)).perform(click())
            onView(withId(R.id.buttonTeamTwoMiss)).perform(click())
            onView(withId(R.id.buttonTeamOneGoal)).perform(click())
            onView(withId(R.id.buttonTeamTwoMiss)).perform(click())
            onView(withId(R.id.golUm)).check(matches(isDisplayed()))
        }
}
