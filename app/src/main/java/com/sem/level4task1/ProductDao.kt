package com.sem.level4task1

import androidx.room.*

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