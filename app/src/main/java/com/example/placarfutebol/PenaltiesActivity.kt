package com.example.placarfutebol

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PenaltiesActivity : AppCompatActivity() {

    companion object {
        const val PENALTIES_REQUEST_CODE = 1
    }

    private lateinit var textViewTeamOne: TextView
    private lateinit var textViewTeamOneScore: TextView
    private lateinit var buttonTeamOneGoal: Button
    private lateinit var buttonTeamOneMiss: Button

    private lateinit var textViewTeamTwo: TextView
    private lateinit var textViewTeamTwoScore: TextView
    private lateinit var buttonTeamTwoGoal: Button
    private lateinit var buttonTeamTwoMiss: Button

    private var teamOneScore: Int = 0
    private var teamTwoScore: Int = 0

    private var currentTeam: Int = 1
    private var penaltyTurn: Int = 1
    private var maxPenalties: Int = 10

    private lateinit var teamOneName: String
    private lateinit var teamTwoName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_penalties)

        textViewTeamOne = findViewById(R.id.textViewTeamOne)
        textViewTeamOneScore = findViewById(R.id.textViewTeamOneScore)
        buttonTeamOneGoal = findViewById(R.id.buttonTeamOneGoal)
        buttonTeamOneMiss = findViewById(R.id.buttonTeamOneMiss)

        textViewTeamTwo = findViewById(R.id.textViewTeamTwo)
        textViewTeamTwoScore = findViewById(R.id.textViewTeamTwoScore)
        buttonTeamTwoGoal = findViewById(R.id.buttonTeamTwoGoal)
        buttonTeamTwoMiss = findViewById(R.id.buttonTeamTwoMiss)

        val intent = intent
        teamOneName = intent.getStringExtra("teamOneName") ?: "Team One"
        teamTwoName = intent.getStringExtra("teamTwoName") ?: "Team Two"

        textViewTeamOne.text = teamOneName
        textViewTeamTwo.text = teamTwoName

        buttonTeamOneGoal.setOnClickListener {
            handleGoalScored(1)
        }

        buttonTeamOneMiss.setOnClickListener {
            handleMissedPenalty(1)
        }

        buttonTeamTwoGoal.setOnClickListener {
            handleGoalScored(2)
        }

        buttonTeamTwoMiss.setOnClickListener {
            handleMissedPenalty(2)
        }
    }

    private fun handleGoalScored(team: Int) {
        if (team == 1) {
            teamOneScore++
            textViewTeamOneScore.text = teamOneScore.toString()
        } else {
            teamTwoScore++
            textViewTeamTwoScore.text = teamTwoScore.toString()
        }

        nextPenaltyTurn()
    }

    private fun handleMissedPenalty(team: Int) {
        nextPenaltyTurn()
    }

    private fun nextPenaltyTurn() {
        penaltyTurn++

        if (penaltyTurn > maxPenalties) {
            endPenalties()
        } else {
            currentTeam = if (currentTeam == 1) 2 else 1
            updateButtonVisibility()
        }
    }

    private fun updateButtonVisibility() {
        if (currentTeam == 1) {
            buttonTeamOneGoal.visibility = View.VISIBLE
            buttonTeamOneMiss.visibility = View.VISIBLE
            buttonTeamTwoGoal.visibility = View.INVISIBLE
            buttonTeamTwoMiss.visibility = View.INVISIBLE
        } else {
            buttonTeamOneGoal.visibility = View.INVISIBLE
            buttonTeamOneMiss.visibility = View.INVISIBLE
            buttonTeamTwoGoal.visibility = View.VISIBLE
            buttonTeamTwoMiss.visibility = View.VISIBLE
        }
    }

    private fun endPenalties() {
        val intent = Intent()
        intent.putExtra("teamOneScore", teamOneScore)
        intent.putExtra("teamTwoScore", teamTwoScore)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onBackPressed() {
        // Disable back button during penalties
    }

    override fun finish() {
        val intent = Intent()
        intent.putExtra("teamOneScore", teamOneScore)
        intent.putExtra("teamTwoScore", teamTwoScore)
        intent.putExtra("teamOneName", teamOneName)
        intent.putExtra("teamTwoName", teamTwoName)
        setResult(RESULT_OK, intent)
        super.finish()
    }
}
