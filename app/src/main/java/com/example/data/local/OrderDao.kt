package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
  @Query("SELECT * FROM orders ORDER BY createdAt DESC")
  fun getAllOrders(): Flow<List<OrderEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrder(order: OrderEntity)

  @Query("SELECT COUNT(*) FROM orders")
  suspend fun getOrderCount(): Int
}
