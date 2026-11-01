package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Product
import com.example.ui.theme.AmberRoasted
import com.example.ui.theme.SaffronDark
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.VegGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailSheet(
  product: Product,
  cartQuantity: Int,
  isFavorite: Boolean,
  isHindi: Boolean,
  onDismiss: () -> Unit,
  onAddToCart: () -> Unit,
  onDecreaseFromCart: () -> Unit,
  onToggleFavorite: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = Color.White,
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
      // Top actions (close + favorite)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onDismiss,
          modifier = Modifier.testTag("detail_close_button")
        ) {
          Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
        }

        IconButton(
          onClick = onToggleFavorite,
          modifier = Modifier.testTag("detail_fav_button")
        ) {
          Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
            tint = if (isFavorite) Color(0xFFD32F2F) else MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }

      // Hero Emoji / Graphic Container
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(160.dp)
          .clip(RoundedCornerShape(20.dp))
          .background(
            brush = Brush.radialGradient(
              colors = listOf(Color(0xFFFFECCC), Color(0xFFFFDBA1))
            )
          ),
        contentAlignment = Alignment.Center
      ) {
        Text(text = product.emoji, fontSize = 64.sp)

        // Veg mark
        Box(
          modifier = Modifier
            .align(Alignment.TopStart)
            .padding(12.dp)
            .size(18.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.White)
            .border(1.5.dp, VegGreen, RoundedCornerShape(4.dp)),
          contentAlignment = Alignment.Center
        ) {
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(VegGreen)
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Tags Row
      Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        if (product.badge != null) {
          Surface(
            color = SaffronDark,
            shape = RoundedCornerShape(6.dp)
          ) {
            Text(
              text = product.badge,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
          }
        }

        Surface(
          color = Color(0xFFE8F5E9),
          shape = RoundedCornerShape(6.dp)
        ) {
          Text(
            text = "100% VEGETARIAN",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = VegGreen,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
          )
        }

        Surface(
          color = Color(0xFFFFF3E0),
          shape = RoundedCornerShape(6.dp)
        ) {
          Text(
            text = "ROASTED • NON-FRIED",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = AmberRoasted,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Name & Hindi name
      Text(
        text = product.name,
        fontSize = 20.sp,
        fontWeight = FontWeight.Black,
        color = MaterialTheme.colorScheme.onSurface
      )
      Text(
        text = product.hindiName,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        color = SaffronDark
      )

      Spacer(modifier = Modifier.height(6.dp))

      // Weight & Rating Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = Color(0xFFF59E0B),
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "${product.rating} ★ (${product.reviewCount} Bhiwani reviews)",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
          )
        }

        Text(
          text = "Net Wt: ${product.weight}",
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          color = AmberRoasted
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Price Row
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = "₹${product.price}",
          fontSize = 24.sp,
          fontWeight = FontWeight.Black,
          color = SaffronDark
        )
        if (product.originalPrice > product.price) {
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "₹${product.originalPrice}",
            fontSize = 15.sp,
            textDecoration = TextDecoration.LineThrough,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.width(8.dp))
          Surface(
            color = Color(0xFFE8F5E9),
            shape = RoundedCornerShape(4.dp)
          ) {
            val discountPercent = ((product.originalPrice - product.price) * 100) / product.originalPrice
            Text(
              text = "$discountPercent% OFF",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = VegGreen,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Description
      Text(
        text = if (isHindi) "विवरण (Description)" else "Description",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "${product.description}\n${product.hindiDescription}",
        fontSize = 13.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        lineHeight = 18.sp
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Ingredients Box
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(Color(0xFFF9F6F0))
          .border(1.dp, Color(0xFFEFE6D9), RoundedCornerShape(12.dp))
          .padding(12.dp)
      ) {
        Column {
          Text(
            text = "Ingredients / सामग्री:",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = product.ingredients,
            fontSize = 11.5.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "Shelf Life: ${product.shelfLife} • ${product.storageAdvice}",
            fontSize = 11.sp,
            color = AmberRoasted
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Delivery Promise
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(Color(0xFFFFF9ED))
          .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.LocalShipping,
          contentDescription = null,
          tint = SaffronPrimary,
          modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
          Text(
            text = "Bhiwani City Fast Delivery",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "Dispatched fresh from Hansi Gate Hub within 45 mins",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Bottom Add to Cart action button
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        if (cartQuantity > 0) {
          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(12.dp))
              .background(Color(0xFFFFF0DB))
              .border(1.dp, SaffronPrimary, RoundedCornerShape(12.dp))
              .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            IconButton(
              onClick = onDecreaseFromCart,
              modifier = Modifier.size(32.dp)
            ) {
              Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease", tint = SaffronDark)
            }
            Text(
              text = "$cartQuantity in Cart",
              fontWeight = FontWeight.Black,
              fontSize = 14.sp,
              color = SaffronDark,
              modifier = Modifier.padding(horizontal = 8.dp)
            )
            IconButton(
              onClick = onAddToCart,
              modifier = Modifier.size(32.dp)
            ) {
              Icon(imageVector = Icons.Default.Add, contentDescription = "Increase", tint = SaffronDark)
            }
          }
        }

        Button(
          onClick = {
            if (cartQuantity == 0) {
              onAddToCart()
            }
            onDismiss()
          },
          modifier = Modifier
            .weight(1f)
            .padding(start = if (cartQuantity > 0) 12.dp else 0.dp)
            .height(50.dp)
            .testTag("detail_add_cart_button"),
          colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
          shape = RoundedCornerShape(14.dp)
        ) {
          Text(
            text = if (cartQuantity == 0) "Add to Cart • ₹${product.price}" else "Done",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = Color.White
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}
