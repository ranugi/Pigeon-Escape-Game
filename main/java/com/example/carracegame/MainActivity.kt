package com.example.carracegame

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.carracegame.R.id
import com.google.android.material.color.utilities.Score

class MainActivity : AppCompatActivity(), GameTask {
    lateinit var rootLayout: LinearLayout
    lateinit var startBtn : Button
    lateinit var mGameView: GameView
    lateinit var score : TextView
    private lateinit var highScoreTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize layout elements
        startBtn = findViewById(id.startBtn)
        rootLayout = findViewById(id.rootLayout)
        score = findViewById(id.score)
        mGameView = GameView(this,  this)
        highScoreTextView = findViewById(R.id.Highscore)

        val  prefs = getSharedPreferences("game", Context.MODE_PRIVATE)
        val highScore = prefs.getInt("High Score", 0)
        highScoreTextView.text = "High Score : $highScore"

        //set onClickListener to the start button
        startBtn.setOnClickListener{

            //set background resource for the game view
            mGameView.setBackgroundResource(R.drawable.sky)

        //add the game view to the root layout
            rootLayout.addView(mGameView)

        //hide the start button and score views
            startBtn.visibility = View.GONE
            score.visibility = View.GONE
            highScoreTextView.visibility = View.GONE
        }
    }

    override fun closeGame (mScore: Int){
        score.text = "Score : $mScore"  //update the ui to display the final score

        //retrieve highScore from sharedPref
        val  prefs = getSharedPreferences("game", Context.MODE_PRIVATE)
        val highScore = prefs.getInt("High Score", 0)

        if (mScore > highScore) {
            //update the highScore in sharedPref
            val editor = prefs.edit()
            editor.putInt("High Score", mScore)
            editor.apply()

            //update ui to display new highScore
            val highScoreText = "High Score: $mScore"
            highScoreTextView.text = highScoreText
        }

        rootLayout.removeView(mGameView)  //remove the game view from the root layout
        startBtn.visibility = View.VISIBLE // make the start button and score value visible again
        score.visibility = View.VISIBLE
        highScoreTextView.visibility = View.VISIBLE
    }

}