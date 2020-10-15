package com.sem.level4task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sem.level4task2.R
import com.sem.level4task2.model.Game
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.game.view.*


class GameListAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameListAdapter.ViewHolder>(){


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun databind(game: Game) {
            itemView.tvDate.text = game.gameDate
            itemView.tvWinLose.text = game.winLose
            when (game.pcPick) {
                1 -> itemView.pcIV.setImageResource(R.drawable.rock)
                2 -> itemView.pcIV.setImageResource(R.drawable.paper)
                3 -> itemView.pcIV.setImageResource(R.drawable.scissors)
            }
            when (game.playerPick) {
                1 -> itemView.playerIV.setImageResource(R.drawable.rock)
                2 -> itemView.playerIV.setImageResource(R.drawable.paper)
                3 -> itemView.playerIV.setImageResource(R.drawable.scissors)
            }
        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.game, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return games.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(games[position])
    }


}