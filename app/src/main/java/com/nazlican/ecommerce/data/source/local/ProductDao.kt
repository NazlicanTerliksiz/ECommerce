package com.nazlican.ecommerce.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nazlican.ecommerce.data.model.response.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM fav_products WHERE userId= :userId")
    suspend fun getProducts(userId : String) : List<ProductEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(productEntity: ProductEntity)
    @Delete
    suspend fun deleteProduct(productEntity: ProductEntity)
    @Query("SELECT productId FROM fav_products WHERE userId= :userId")
    suspend fun getProductIds(userId : String): List<Int>
    @Query("DELETE FROM fav_products WHERE userId = :userId")
    suspend fun clearAllFavorites(userId:String)
}