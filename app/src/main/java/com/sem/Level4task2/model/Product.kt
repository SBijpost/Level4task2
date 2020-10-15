package com.sem.Level4task2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productTable")
data class Product(

    @ColumnInfo(name = "product")
    var productName: String,

    @ColumnInfo(name = "quantity")
    var quantity: Short?,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

)
