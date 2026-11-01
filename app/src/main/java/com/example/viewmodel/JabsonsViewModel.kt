package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ProductCatalog
import com.example.data.local.AppDatabase
import com.example.data.local.OrderEntity
import com.example.model.BhiwaniData
import com.example.model.CartItem
import com.example.model.Order
import com.example.model.OrderItemRecord
import com.example.model.Product
import com.example.model.ProductCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppTab {
  HOME,
  CART,
  ORDERS,
  STORE_INFO
}

class JabsonsViewModel(application: Application) : AndroidViewModel(application) {
  private val orderDao = AppDatabase.getDatabase(application).orderDao()

  val allProducts: List<Product> = ProductCatalog.PRODUCTS

  private val _selectedCategory = MutableStateFlow(ProductCategory.ALL)
  val selectedCategory: StateFlow<ProductCategory> = _selectedCategory.asStateFlow()

  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  private val _cart = MutableStateFlow<Map<Int, Int>>(emptyMap())
  val cart: StateFlow<Map<Int, Int>> = _cart.asStateFlow()

  private val _favoriteIds = MutableStateFlow<Set<Int>>(setOf(1, 4))
  val favoriteIds: StateFlow<Set<Int>> = _favoriteIds.asStateFlow()

  private val _selectedProduct = MutableStateFlow<Product?>(null)
  val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

  private val _currentTab = MutableStateFlow(AppTab.HOME)
  val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

  private val _isHindi = MutableStateFlow(false)
  val isHindi: StateFlow<Boolean> = _isHindi.asStateFlow()

  // Checkout Form State
  var customerName = MutableStateFlow("")
  var phoneNumber = MutableStateFlow("")
  var deliveryAddress = MutableStateFlow("")
  var selectedArea = MutableStateFlow(BhiwaniData.LOCALITIES.first())
  var selectedSlot = MutableStateFlow(BhiwaniData.DELIVERY_SLOTS.first())
  var paymentMethod = MutableStateFlow("UPI")

  private val _appliedCoupon = MutableStateFlow<String?>(null)
  val appliedCoupon: StateFlow<String?> = _appliedCoupon.asStateFlow()

  private val _couponMessage = MutableStateFlow<String?>(null)
  val couponMessage: StateFlow<String?> = _couponMessage.asStateFlow()

  private val _latestOrder = MutableStateFlow<Order?>(null)
  val latestOrder: StateFlow<Order?> = _latestOrder.asStateFlow()

  val orderHistory: StateFlow<List<OrderEntity>> = orderDao.getAllOrders()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  fun setTab(tab: AppTab) {
    _currentTab.value = tab
  }

  fun toggleLanguage() {
    _isHindi.value = !_isHindi.value
  }

  fun selectCategory(category: ProductCategory) {
    _selectedCategory.value = category
  }

  fun updateSearch(query: String) {
    _searchQuery.value = query
  }

  fun selectProduct(product: Product?) {
    _selectedProduct.value = product
  }

  fun toggleFavorite(productId: Int) {
    val current = _favoriteIds.value.toMutableSet()
    if (current.contains(productId)) {
      current.remove(productId)
    } else {
      current.add(productId)
    }
    _favoriteIds.value = current
  }

  fun addToCart(productId: Int) {
    val current = _cart.value.toMutableMap()
    current[productId] = (current[productId] ?: 0) + 1
    _cart.value = current
  }

  fun decreaseFromCart(productId: Int) {
    val current = _cart.value.toMutableMap()
    val count = current[productId] ?: 0
    if (count > 1) {
      current[productId] = count - 1
    } else {
      current.remove(productId)
    }
    _cart.value = current
  }

  fun removeFromCart(productId: Int) {
    val current = _cart.value.toMutableMap()
    current.remove(productId)
    _cart.value = current
  }

  fun clearCart() {
    _cart.value = emptyMap()
    _appliedCoupon.value = null
    _couponMessage.value = null
  }

  fun getCartItems(): List<CartItem> {
    val cartMap = _cart.value
    return allProducts.filter { cartMap.containsKey(it.id) }.map {
      CartItem(it, cartMap[it.id] ?: 0)
    }
  }

  fun getCartCount(): Int {
    return _cart.value.values.sum()
  }

  fun getCartSubtotal(): Int {
    return getCartItems().sumOf { it.totalPrice }
  }

  fun getDeliveryFee(subtotal: Int): Int {
    // Free delivery in Bhiwani for orders >= 199, else nominal 25
    return if (subtotal >= 199 || subtotal == 0) 0 else 25
  }

  fun getDiscount(subtotal: Int): Int {
    return when (_appliedCoupon.value) {
      "BHIWANI50" -> if (subtotal >= 199) 50 else 0
      "JABSONS10" -> (subtotal * 0.10f).toInt()
      else -> 0
    }
  }

  fun getTotalAmount(): Int {
    val subtotal = getCartSubtotal()
    val fee = getDeliveryFee(subtotal)
    val discount = getDiscount(subtotal)
    return (subtotal + fee - discount).coerceAtLeast(0)
  }

  fun applyCoupon(code: String): Boolean {
    val cleaned = code.trim().uppercase()
    val subtotal = getCartSubtotal()
    return when (cleaned) {
      "BHIWANI50" -> {
        if (subtotal >= 199) {
          _appliedCoupon.value = cleaned
          _couponMessage.value = "Coupon BHIWANI50 applied! ₹50 OFF"
          true
        } else {
          _couponMessage.value = "Min order ₹199 required for BHIWANI50"
          false
        }
      }
      "JABSONS10" -> {
        _appliedCoupon.value = cleaned
        _couponMessage.value = "Coupon JABSONS10 applied! 10% OFF"
        true
      }
      else -> {
        _couponMessage.value = "Invalid coupon code. Try BHIWANI50 or JABSONS10"
        false
      }
    }
  }

  fun removeCoupon() {
    _appliedCoupon.value = null
    _couponMessage.value = null
  }

  fun placeOrder(onSuccess: (Order) -> Unit, onError: (String) -> Unit) {
    val items = getCartItems()
    if (items.isEmpty()) {
      onError("Your cart is empty. Please add items to order.")
      return
    }

    val name = customerName.value.trim()
    val phone = phoneNumber.value.trim()
    val address = deliveryAddress.value.trim()
    val area = selectedArea.value

    if (name.isBlank()) {
      onError("Please enter your name.")
      return
    }
    if (phone.length != 10 || !phone.all { it.isDigit() }) {
      onError("Please enter a valid 10-digit mobile number.")
      return
    }
    if (address.isBlank()) {
      onError("Please enter your delivery street / house address in Bhiwani.")
      return
    }

    val subtotal = getCartSubtotal()
    val fee = getDeliveryFee(subtotal)
    val discount = getDiscount(subtotal)
    val total = getTotalAmount()
    val orderId = "JB" + (System.currentTimeMillis() % 10000000).toString().padStart(7, '0')

    val orderItemRecords = items.map {
      OrderItemRecord(
        productName = it.product.name,
        hindiName = it.product.hindiName,
        quantity = it.quantity,
        priceEach = it.product.price,
        total = it.totalPrice,
        weight = it.product.weight
      )
    }

    val itemsSummary = orderItemRecords.joinToString("; ") { "${it.productName} (${it.weight}) x${it.quantity}" }

    val order = Order(
      orderId = orderId,
      customerName = name,
      phone = phone,
      address = address,
      area = area,
      slot = selectedSlot.value,
      paymentMethod = paymentMethod.value,
      items = orderItemRecords,
      subtotal = subtotal,
      discount = discount,
      deliveryFee = fee,
      totalAmount = total,
      createdAt = System.currentTimeMillis(),
      status = "Order Confirmed - Preparing at Bhiwani Hub"
    )

    viewModelScope.launch {
      val entity = OrderEntity(
        orderId = orderId,
        customerName = name,
        phone = phone,
        address = address,
        area = area,
        slot = selectedSlot.value,
        paymentMethod = paymentMethod.value,
        itemsSummary = itemsSummary,
        subtotal = subtotal,
        discount = discount,
        deliveryFee = fee,
        totalAmount = total,
        createdAt = order.createdAt,
        status = order.status
      )
      orderDao.insertOrder(entity)
      _latestOrder.value = order
      clearCart()
      onSuccess(order)
    }
  }

  fun dismissLatestOrderDialog() {
    _latestOrder.value = null
  }
}
