package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.Product
import com.example.model.ProductCategory
import com.example.ui.components.CategoryChips
import com.example.ui.components.ProductCard
import com.example.ui.components.SearchBarWithFilter
import com.example.ui.theme.AmberLight
import com.example.ui.theme.AmberRoasted
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.SaffronDark
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.VegGreen

@Composable
fun HomeScreen(
  products: List<Product>,
  selectedCategory: ProductCategory,
  searchQuery: String,
  cart: Map<Int, Int>,
  favoriteIds: Set<Int>,
  isHindi: Boolean,
  onCategorySelected: (ProductCategory) -> Unit,
  onSearchQueryChanged: (String) -> Unit,
  onAddToCart: (Int) -> Unit,
  onDecreaseFromCart: (Int) -> Unit,
  onToggleFavorite: (Int) -> Unit,
  onProductClick: (Product) -> Unit,
  modifier: Modifier = Modifier
) {
  val filteredProducts = products.filter { product ->
    val matchesCategory = selectedCategory == ProductCategory.ALL || product.category == selectedCategory
    val matchesSearch = searchQuery.isBlank() ||
      product.name.contains(searchQuery, ignoreCase = true) ||
      product.hindiName.contains(searchQuery, ignoreCase = true) ||
      product.description.contains(searchQuery, ignoreCase = true)
    matchesCategory && matchesSearch
  }

  LazyVerticalGrid(
    columns = GridCells.Adaptive(minSize = 160.dp),
    modifier = modifier
      .fillMaxSize()
      .background(CreamBackground)
      .testTag("home_products_grid"),
    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 96.dp),
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Search Bar
    item(span = { GridItemSpan(maxLineSpan) }) {
      SearchBarWithFilter(
        query = searchQuery,
        onQueryChange = onSearchQueryChanged,
        isHindi = isHindi
      )
    }

    // Hero Banner Card with visual image
    item(span = { GridItemSpan(maxLineSpan) }) {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
        ) {
          Image(
            painter = painterResource(id = R.drawable.jabsons_hero_banner),
            contentDescription = "Jabsons Snacks Spread",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
          )
          // Gradient scrim
          Box(
            modifier = Modifier
              .fillMaxSize()
              .background(
                brush = Brush.verticalGradient(
                  colors = listOf(
                    Color.Transparent,
                    Color(0xCC1A120B)
                  )
                )
              )
          )

          Column(
            modifier = Modifier
              .align(Alignment.BottomStart)
              .padding(14.dp)
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(SaffronPrimary)
                .padding(horizontal = 7.dp, vertical = 2.dp)
            ) {
              Icon(
                imageVector = Icons.Default.LocalOffer,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(11.dp)
              )
              Spacer(modifier = Modifier.width(3.dp))
              Text(
                text = "FLAT ₹50 OFF WITH CODE: BHIWANI50",
                fontSize = 9.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
              )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
              text = if (isHindi) "शुद्ध स्वाद • पारंपरिक कुरकुरापन" else "Pure Taste of Heritage Snacks",
              fontWeight = FontWeight.Black,
              fontSize = 17.sp,
              color = Color.White
            )
            Text(
              text = if (isHindi) "भिवानी शहर में सबसे तेज़ होम डिलीवरी" else "Finest Bharuch Peanuts & Festive Namkeen in Bhiwani",
              fontSize = 11.sp,
              color = Color.White.copy(alpha = 0.9f)
            )
          }
        }
      }
    }

    // Notice Box (from prototype)
    item(span = { GridItemSpan(maxLineSpan) }) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(AmberLight)
          .border(1.dp, Color(0xFFF5D5A9), RoundedCornerShape(12.dp))
          .padding(horizontal = 12.dp, vertical = 10.dp)
      ) {
        Row(verticalAlignment = Alignment.Top) {
          Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Notice",
            tint = AmberRoasted,
            modifier = Modifier.size(16.dp).padding(top = 1.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = if (isHindi)
              "डेमो प्रोटोटाइप: भिवानी शहर के लिए ऑनलाइन स्नैक्स ऑर्डरिंग ऐप। असली ऑर्डर व लाइव पेमेंट स्टोर बैकएंड से जोड़े जा सकते हैं।"
            else
              "Demo Prototype: Bhiwani City online snacks ordering app demo. Local catalog, 45-min delivery & simulated checkout ready.",
            fontSize = 11.5.sp,
            color = AmberRoasted,
            lineHeight = 16.sp
          )
        }
      }
    }

    // Value propositions row
    item(span = { GridItemSpan(maxLineSpan) }) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        // Fast delivery badge
        Row(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(horizontal = 8.dp, vertical = 7.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.ElectricBolt,
            contentDescription = null,
            tint = SaffronPrimary,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "30-45 Min Bhiwani",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        }

        // Roasted not fried badge
        Row(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(horizontal = 8.dp, vertical = 7.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Verified,
            contentDescription = null,
            tint = VegGreen,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Roasted, Zero Frying",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
        }
      }
    }

    // Category Selector
    item(span = { GridItemSpan(maxLineSpan) }) {
      Column {
        Text(
          text = if (isHindi) "कैटेगरी चुनें" else "Shop by Category",
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp,
          color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(6.dp))
        CategoryChips(
          selectedCategory = selectedCategory,
          onCategorySelected = onCategorySelected,
          isHindi = isHindi,
          modifier = Modifier.padding(horizontal = 0.dp)
        )
      }
    }

    // Header for products
    item(span = { GridItemSpan(maxLineSpan) }) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = if (isHindi) "लोकप्रिय स्नैक्स व कॉम्बो (${filteredProducts.size})" else "Popular Snacks & Combos (${filteredProducts.size})",
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp,
          color = MaterialTheme.colorScheme.onSurface
        )
      }
    }

    // Products
    if (filteredProducts.isEmpty()) {
      item(span = { GridItemSpan(maxLineSpan) }) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 40.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "🔍", fontSize = 36.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = if (isHindi) "कोई स्नैक नहीं मिला" else "No snacks found",
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = if (isHindi) "कृपया दूसरा नाम खोजें या फिल्टर बदलें" else "Try searching for peanuts, namkeen or combos",
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    } else {
      items(filteredProducts, key = { it.id }) { product ->
        ProductCard(
          product = product,
          cartQuantity = cart[product.id] ?: 0,
          isFavorite = favoriteIds.contains(product.id),
          isHindi = isHindi,
          onAddToCart = { onAddToCart(product.id) },
          onDecreaseQuantity = { onDecreaseFromCart(product.id) },
          onToggleFavorite = { onToggleFavorite(product.id) },
          onClick = { onProductClick(product) }
        )
      }
    }
  }
}
