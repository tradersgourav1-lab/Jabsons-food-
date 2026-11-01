package com.example.model

data class OrderItemRecord(
  val productName: String,
  val hindiName: String,
  val quantity: Int,
  val priceEach: Int,
  val total: Int,
  val weight: String
)

data class Order(
  val orderId: String,
  val customerName: String,
  val phone: String,
  val address: String,
  val area: String,
  val slot: String,
  val paymentMethod: String,
  val items: List<OrderItemRecord>,
  val subtotal: Int,
  val discount: Int,
  val deliveryFee: Int,
  val totalAmount: Int,
  val createdAt: Long = System.currentTimeMillis(),
  val status: String = "Order Confirmed",
  val estimatedDeliveryMinutes: Int = 45
)
