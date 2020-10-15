package com.sem.level4task2.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.sem.level4task2.R
import com.sem.level4task2.model.Game
import com.sem.level4task2.repository.GameRepository
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class GameFragment : Fragment() {

    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    // pc rock paper scissors
    private var pcRPS: Int = 1

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun updateUI() {

        when (pcRPS) {
            1 -> pcIV.setImageResource(R.drawable.rock)
            2 -> pcIV.setImageResource(R.drawable.paper)
            3 -> pcIV.setImageResource(R.drawable.scissors)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addGame(win: String, pc: Int, player: Int) {
        mainScope.launch {
            val game = Game(
                gameDate = LocalDateTime.now().toString(),
                winLose = win,
                pcPick = pc,
                playerPick = player
            )

            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViews() {
        rockIB.setOnClickListener {
            onRockClick()
        }
        paperIB.setOnClickListener {
            onPaperClick()
        }
        scissorsIB.setOnClickListener {
            onScissorsClick()
        }
        updateUI()
    }

    private fun playGame() {
        pcRPS = (1..3).random()
        updateUI()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onAnswerIncorrect(pick: Int) {
        winloseTV.text = getString(R.string.you_lose)
        addGame("Computer wins!", pcRPS, pick)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onAnswerCorrect(pick: Int) {
        winloseTV.text = getString(R.string.you_win)
        addGame("You win!", pcRPS, pick)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onDraw(pick: Int) {
        winloseTV.text = getString(R.string.draw)
        addGame("Draw", pcRPS, pick)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onRockClick() {
        playerIV.setImageResource(R.drawable.rock)
        playGame()
        if(pcRPS == 1) {
            onDraw(1)
        } else if(pcRPS == 2) {
            onAnswerIncorrect(1)
        } else if(pcRPS == 3) {
            onAnswerCorrect(1)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onPaperClick() {
        playerIV.setImageResource(R.drawable.paper)
        playGame()
        if(pcRPS == 1) {
            onAnswerCorrect(2)
        } else if(pcRPS == 2) {
            onDraw(2)
        } else if(pcRPS == 3) {
            onAnswerIncorrect(2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onScissorsClick() {
        playerIV.setImageResource(R.drawable.scissors)
        playGame()
        if(pcRPS == 1) {
            onAnswerIncorrect(3)
        } else if(pcRPS == 2) {
            onAnswerCorrect(3)
        } else if(pcRPS == 3) {
            onDraw(3)
        }
    }

}