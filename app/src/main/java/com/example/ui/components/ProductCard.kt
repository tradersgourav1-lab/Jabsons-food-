package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Product
import com.example.ui.theme.AmberRoasted
import com.example.ui.theme.SaffronDark
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.VegGreen

@Composable
fun ProductCard(
  product: Product,
  cartQuantity: Int,
  isFavorite: Boolean,
  isHindi: Boolean,
  onAddToCart: () -> Unit,
  onDecreaseQuantity: () -> Unit,
  onToggleFavorite: () -> Unit,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(18.dp))
      .clickable(onClick = onClick)
      .testTag("product_card_${product.id}"),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    shape = RoundedCornerShape(18.dp)
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      // Image Box / Emoji Container
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(118.dp)
          .clip(RoundedCornerShape(14.dp))
          .background(
            brush = Brush.linearGradient(
              colors = listOf(Color(0xFFFFF2DC), Color(0xFFFFE2B3))
            )
          ),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = product.emoji,
          fontSize = 48.sp
        )

        // Top Badges (Veg mark + Promo badge)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopStart)
            .padding(6.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Veg indicator
          Box(
            modifier = Modifier
              .size(16.dp)
              .clip(RoundedCornerShape(3.dp))
              .background(Color.White)
              .border(1.2.dp, VegGreen, RoundedCornerShape(3.dp)),
            contentAlignment = Alignment.Center
          ) {
            Box(
              modifier = Modifier
                .size(7.dp)
                .clip(CircleShape)
                .background(VegGreen)
            )
          }

          // Favorite Heart
          IconButton(
            onClick = onToggleFavorite,
            modifier = Modifier
              .size(28.dp)
              .clip(CircleShape)
              .background(Color.White.copy(alpha = 0.85f))
              .testTag("fav_button_${product.id}")
          ) {
            Icon(
              imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
              contentDescription = "Favorite",
              tint = if (isFavorite) Color(0xFFD32F2F) else MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.size(16.dp)
            )
          }
        }

        // Bottom Badge if available
        if (product.badge != null) {
          Surface(
            color = SaffronDark,
            shape = RoundedCornerShape(topStart = 8.dp, bottomEnd = 8.dp),
            modifier = Modifier
              .align(Alignment.BottomStart)
              .padding(bottom = 0.dp)
          ) {
            Text(
              text = product.badge,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Rating & Weight Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Rating",
            tint = Color(0xFFF59E0B),
            modifier = Modifier.size(13.dp)
          )
          Spacer(modifier = Modifier.width(2.dp))
          Text(
            text = "${product.rating}",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = " (${product.reviewCount})",
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Text(
          text = product.weight,
          fontSize = 11.sp,
          fontWeight = FontWeight.SemiBold,
          color = AmberRoasted
        )
      }

      Spacer(modifier = Modifier.height(4.dp))

      // Product Title
      Text(
        text = if (isHindi) product.hindiName else product.name,
        fontWeight = FontWeight.Bold,
        fontSize = 13.5.sp,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        lineHeight = 18.sp,
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(3.dp))

      // Description
      Text(
        text = if (isHindi) product.hindiDescription else product.description,
        fontSize = 11.sp,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        lineHeight = 15.sp
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Price and Add / Stepper Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "₹${product.price}",
              fontWeight = FontWeight.Black,
              fontSize = 16.sp,
              color = SaffronDark
            )
            if (product.originalPrice > product.price) {
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "₹${product.originalPrice}",
                fontSize = 11.sp,
                textDecoration = TextDecoration.LineThrough,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }

        if (cartQuantity == 0) {
          // Add Button
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(10.dp))
              .background(SaffronPrimary)
              .clickable(onClick = onAddToCart)
              .padding(horizontal = 14.dp, vertical = 7.dp)
              .testTag("add_to_cart_${product.id}"),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = if (isHindi) "जोड़ें +" else "Add +",
              fontWeight = FontWeight.Bold,
              fontSize = 12.5.sp,
              color = Color.White
            )
          }
        } else {
          // Stepper (- qty +)
          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(10.dp))
              .background(Color(0xFFFFF0DB))
              .border(1.dp, SaffronPrimary.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
              .padding(horizontal = 4.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            IconButton(
              onClick = onDecreaseQuantity,
              modifier = Modifier
                .size(24.dp)
                .testTag("stepper_dec_${product.id}")
            ) {
              Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease",
                tint = SaffronDark,
                modifier = Modifier.size(14.dp)
              )
            }

            Text(
              text = "$cartQuantity",
              fontWeight = FontWeight.Black,
              fontSize = 13.sp,
              color = SaffronDark,
              modifier = Modifier.padding(horizontal = 6.dp)
            )

            IconButton(
              onClick = onAddToCart,
              modifier = Modifier
                .size(24.dp)
                .testTag("stepper_inc_${product.id}")
            ) {
              Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase",
                tint = SaffronDark,
                modifier = Modifier.size(14.dp)
              )
            }
          }
        }
      }
    }
  }
}
