package com.sem.level4task2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "gameTable")
data class Game(

    @ColumnInfo(name = "gameDate")
    var gameDate: String,

    @ColumnInfo(name = "winLose")
    var winLose: String,

    @ColumnInfo(name = "pcPick")
    var pcPick: Int,

    @ColumnInfo(name = "playerPick")
    var playerPick: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

)
