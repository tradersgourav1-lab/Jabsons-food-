package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AppTopBar
import com.example.ui.components.CartFloatingBar
import com.example.ui.screens.CartCheckoutScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OrderHistoryScreen
import com.example.ui.screens.OrderSuccessDialog
import com.example.ui.screens.ProductDetailSheet
import com.example.ui.screens.StoreInfoScreen
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.SaffronDark
import com.example.ui.theme.SaffronPrimary
import com.example.viewmodel.AppTab
import com.example.viewmodel.JabsonsViewModel

@Composable
fun JabsonsApp(
  viewModel: JabsonsViewModel = viewModel()
) {
  val currentTab by viewModel.currentTab.collectAsState()
  val isHindi by viewModel.isHindi.collectAsState()
  val selectedCategory by viewModel.selectedCategory.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()
  val cart by viewModel.cart.collectAsState()
  val favoriteIds by viewModel.favoriteIds.collectAsState()
  val selectedProduct by viewModel.selectedProduct.collectAsState()
  val latestOrder by viewModel.latestOrder.collectAsState()
  val orderHistory by viewModel.orderHistory.collectAsState()

  val cartCount = viewModel.getCartCount()
  val cartSubtotal = viewModel.getCartSubtotal()

  // Handle back button: return to Home if on sub-screens
  BackHandler(enabled = currentTab != AppTab.HOME) {
    viewModel.setTab(AppTab.HOME)
  }

  Scaffold(
    topBar = {
      AppTopBar(
        isHindi = isHindi,
        onToggleLanguage = { viewModel.toggleLanguage() }
      )
    },
    bottomBar = {
      NavigationBar(
        containerColor = Color.White,
        tonalElevation = 6.dp,
        modifier = Modifier.testTag("main_navigation_bar")
      ) {
        // Home Tab
        NavigationBarItem(
          selected = currentTab == AppTab.HOME,
          onClick = { viewModel.setTab(AppTab.HOME) },
          icon = {
            Icon(
              imageVector = if (currentTab == AppTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
              contentDescription = "Home"
            )
          },
          label = {
            Text(
              text = if (isHindi) "होम" else "Snacks",
              fontSize = 11.sp,
              fontWeight = if (currentTab == AppTab.HOME) FontWeight.Bold else FontWeight.Normal
            )
          },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = SaffronPrimary,
            selectedTextColor = SaffronPrimary,
            indicatorColor = Color(0xFFFFF0DB)
          ),
          modifier = Modifier.testTag("nav_tab_home")
        )

        // Cart Tab
        NavigationBarItem(
          selected = currentTab == AppTab.CART,
          onClick = { viewModel.setTab(AppTab.CART) },
          icon = {
            BadgedBox(
              badge = {
                if (cartCount > 0) {
                  Badge(
                    containerColor = SaffronDark,
                    contentColor = Color.White
                  ) {
                    Text(text = "$cartCount", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                  }
                }
              }
            ) {
              Icon(
                imageVector = if (currentTab == AppTab.CART) Icons.Filled.ShoppingCart else Icons.Outlined.ShoppingCart,
                contentDescription = "Cart"
              )
            }
          },
          label = {
            Text(
              text = if (isHindi) "कार्ट" else "Cart",
              fontSize = 11.sp,
              fontWeight = if (currentTab == AppTab.CART) FontWeight.Bold else FontWeight.Normal
            )
          },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = SaffronPrimary,
            selectedTextColor = SaffronPrimary,
            indicatorColor = Color(0xFFFFF0DB)
          ),
          modifier = Modifier.testTag("nav_tab_cart")
        )

        // Orders Tab
        NavigationBarItem(
          selected = currentTab == AppTab.ORDERS,
          onClick = { viewModel.setTab(AppTab.ORDERS) },
          icon = {
            BadgedBox(
              badge = {
                if (orderHistory.isNotEmpty()) {
                  Badge(
                    containerColor = Color(0xFF2E7D32),
                    contentColor = Color.White
                  ) {
                    Text(text = "${orderHistory.size}", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                  }
                }
              }
            ) {
              Icon(
                imageVector = if (currentTab == AppTab.ORDERS) Icons.Filled.ReceiptLong else Icons.Outlined.ReceiptLong,
                contentDescription = "Orders"
              )
            }
          },
          label = {
            Text(
              text = if (isHindi) "ऑर्डर" else "Orders",
              fontSize = 11.sp,
              fontWeight = if (currentTab == AppTab.ORDERS) FontWeight.Bold else FontWeight.Normal
            )
          },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = SaffronPrimary,
            selectedTextColor = SaffronPrimary,
            indicatorColor = Color(0xFFFFF0DB)
          ),
          modifier = Modifier.testTag("nav_tab_orders")
        )

        // Store Info Tab
        NavigationBarItem(
          selected = currentTab == AppTab.STORE_INFO,
          onClick = { viewModel.setTab(AppTab.STORE_INFO) },
          icon = {
            Icon(
              imageVector = if (currentTab == AppTab.STORE_INFO) Icons.Filled.Info else Icons.Outlined.Info,
              contentDescription = "Store Info"
            )
          },
          label = {
            Text(
              text = if (isHindi) "स्टोर" else "Bhiwani Hub",
              fontSize = 11.sp,
              fontWeight = if (currentTab == AppTab.STORE_INFO) FontWeight.Bold else FontWeight.Normal
            )
          },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = SaffronPrimary,
            selectedTextColor = SaffronPrimary,
            indicatorColor = Color(0xFFFFF0DB)
          ),
          modifier = Modifier.testTag("nav_tab_store")
        )
      }
    },
    containerColor = CreamBackground
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (currentTab) {
        AppTab.HOME -> {
          HomeScreen(
            products = viewModel.allProducts,
            selectedCategory = selectedCategory,
            searchQuery = searchQuery,
            cart = cart,
            favoriteIds = favoriteIds,
            isHindi = isHindi,
            onCategorySelected = { viewModel.selectCategory(it) },
            onSearchQueryChanged = { viewModel.updateSearch(it) },
            onAddToCart = { viewModel.addToCart(it) },
            onDecreaseFromCart = { viewModel.decreaseFromCart(it) },
            onToggleFavorite = { viewModel.toggleFavorite(it) },
            onProductClick = { viewModel.selectProduct(it) }
          )

          // Floating cart button on Home
          CartFloatingBar(
            itemCount = cartCount,
            subtotal = cartSubtotal,
            isHindi = isHindi,
            onCheckout = { viewModel.setTab(AppTab.CART) },
            modifier = Modifier.align(Alignment.BottomCenter)
          )
        }

        AppTab.CART -> {
          CartCheckoutScreen(
            viewModel = viewModel,
            onGoBackToShopping = { viewModel.setTab(AppTab.HOME) },
            onOrderSuccess = { /* Dialog is triggered by state */ }
          )
        }

        AppTab.ORDERS -> {
          OrderHistoryScreen(
            orders = orderHistory,
            isHindi = isHindi,
            onStartShopping = { viewModel.setTab(AppTab.HOME) }
          )
        }

        AppTab.STORE_INFO -> {
          StoreInfoScreen(isHindi = isHindi)
        }
      }
    }
  }

  // Product Detail Bottom Sheet
  selectedProduct?.let { product ->
    ProductDetailSheet(
      product = product,
      cartQuantity = cart[product.id] ?: 0,
      isFavorite = favoriteIds.contains(product.id),
      isHindi = isHindi,
      onDismiss = { viewModel.selectProduct(null) },
      onAddToCart = { viewModel.addToCart(product.id) },
      onDecreaseFromCart = { viewModel.decreaseFromCart(product.id) },
      onToggleFavorite = { viewModel.toggleFavorite(product.id) }
    )
  }

  // Order Confirmed Success Modal
  latestOrder?.let { order ->
    OrderSuccessDialog(
      order = order,
      isHindi = isHindi,
      onDismiss = { viewModel.dismissLatestOrderDialog() },
      onViewOrders = {
        viewModel.dismissLatestOrderDialog()
        viewModel.setTab(AppTab.ORDERS)
      }
    )
  }
}
