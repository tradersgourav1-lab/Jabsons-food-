package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
  @PrimaryKey
  val orderId: String,
  val customerName: String,
  val phone: String,
  val address: String,
  val area: String,
  val slot: String,
  val paymentMethod: String,
  val itemsSummary: String,
  val subtotal: Int,
  val discount: Int,
  val deliveryFee: Int,
  val totalAmount: Int,
  val createdAt: Long,
  val status: String
)
