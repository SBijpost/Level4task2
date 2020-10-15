package com.sem.level4task2.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sem.level4task2.R
import com.sem.level4task2.model.Game
import com.sem.level4task2.repository.GameRepository
import kotlinx.android.synthetic.main.fragment_history_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameListFragment : Fragment() {

    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private val games = arrayListOf<Game>()
    private val gameListAdapter = GameListAdapter(games)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameRepository = GameRepository(requireContext())

        getGameListFromDatabase()

        initRv()

    }

    private fun initRv() {

        // Initialize the recycler view with a linear layout manager, adapter
        rvList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvList.adapter = gameListAdapter
        rvList.setHasFixedSize(true)
        rvList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        createItemTouchHelper().attachToRecyclerView(rvList)
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    @SuppressLint("InflateParams")
//    private fun showAddGamedialog() {
//        val builder = AlertDialog.Builder(requireContext())
//        builder.setTitle(getString(R.string.add_product_dialog_title))
//        val dialogLayout = layoutInflater.inflate(R.layout.add_product_dialog, null)
//        val productName = dialogLayout.findViewById<EditText>(R.id.txt_product_name)
//        val amount = dialogLayout.findViewById<EditText>(R.id.txt_amount)
//
//        builder.setView(dialogLayout)
//        builder.setPositiveButton(R.string.dialog_ok_btn) { _: DialogInterface, _: Int ->
//            addGame()
//        }
//        builder.show()
//    }


//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun addGame() {
//            mainScope.launch {
//                val game = Game(
//                    gameDate = LocalDateTime.now().toString(),
//                    winLose = "win",
//                    pcPick = 1,
//                    playerPick = 2
//                )
//
//                withContext(Dispatchers.IO) {
//                    gameRepository.insertGame(game)
//                }
//
//                getGameListFromDatabase()
//            }
//    }

//    private fun validateFields(txtProductName: EditText
//                               , txtAmount: EditText
//    ): Boolean {
//        return if (txtProductName.text.toString().isNotBlank()
//            && txtAmount.text.toString().isNotBlank()
//        ) {
//            true
//        } else {
//            Toast.makeText(activity, "Please fill in the fields", Toast.LENGTH_LONG).show()
//            false
//        }
//    }


    /**
     * Create a touch helper to recognize when a user swipes an item from a recycler view.
     * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
     * and uses callbacks to signal when a user is performing these actions.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val productToDelete = games[position]
                CoroutineScope(Dispatchers.Main).launch {
                    withContext(Dispatchers.IO) {
                        gameRepository.deleteGame(productToDelete)
                    }
                    getGameListFromDatabase()
                }


            }
        }
        return ItemTouchHelper(callback)
    }


    private fun getGameListFromDatabase() {
        mainScope.launch {
            val gameList = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            this@GameListFragment.games.clear()
            this@GameListFragment.games.addAll(gameList)
            this@GameListFragment.gameListAdapter.notifyDataSetChanged()
        }
    }

    private fun removeAllGames() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            getGameListFromDatabase()
        }
    }


}