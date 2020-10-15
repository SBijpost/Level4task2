package com.sem.Level4task2.dao

import androidx.room.*
import com.sem.Level4task2.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM productTable")
    suspend fun getAllProducts(): List<Product>

    @Insert
    suspend fun insertProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("DELETE FROM productTable")
    suspend fun deleteAllProducts()

}