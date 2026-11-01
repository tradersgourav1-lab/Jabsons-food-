package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SaffronLight
import com.example.ui.theme.SaffronPrimary

@Composable
fun CartFloatingBar(
  itemCount: Int,
  subtotal: Int,
  isHindi: Boolean,
  onCheckout: () -> Unit,
  modifier: Modifier = Modifier
) {
  AnimatedVisibility(
    visible = itemCount > 0,
    enter = slideInVertically(initialOffsetY = { it }),
    exit = slideOutVertically(targetOffsetY = { it }),
    modifier = modifier
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .shadow(12.dp, RoundedCornerShape(16.dp))
        .clip(RoundedCornerShape(16.dp))
        .clickable(onClick = onCheckout)
        .testTag("floating_cart_bar"),
      color = Color(0xFF28211A)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "🛒 ${if (isHindi) "कार्ट में" else "Cart ·"} $itemCount ${if (isHindi) "सामग्री" else "items"}",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.White
          )
          Text(
            text = "${if (isHindi) "कुल" else "Total"}: ₹$subtotal",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFE4D5C5)
          )
        }

        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(SaffronPrimary)
            .padding(horizontal = 14.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = if (isHindi) "ऑर्डर करें →" else "Checkout →",
            fontWeight = FontWeight.Black,
            fontSize = 13.sp,
            color = Color.White
          )
        }
      }
    }
  }
}
