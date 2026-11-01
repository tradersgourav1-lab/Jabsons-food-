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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.Order
import com.example.ui.theme.AmberRoasted
import com.example.ui.theme.SaffronDark
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.VegGreen

@Composable
fun OrderSuccessDialog(
  order: Order,
  isHindi: Boolean,
  onDismiss: () -> Unit,
  onViewOrders: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("order_success_dialog")
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Success checkmark badge
        Surface(
          modifier = Modifier.size(64.dp),
          shape = CircleShape,
          color = Color(0xFFE8F5E9)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = "Success",
              tint = VegGreen,
              modifier = Modifier.size(36.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = if (isHindi) "ऑर्डर सफलतापूर्वक दर्ज हुआ! 🎉" else "Demo Order Confirmed! 🎉",
          fontWeight = FontWeight.Black,
          fontSize = 18.sp,
          color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(4.dp))

        Surface(
          color = Color(0xFFFFF2DC),
          shape = RoundedCornerShape(8.dp)
        ) {
          Text(
            text = "ORDER ID: ${order.orderId}",
            fontWeight = FontWeight.Black,
            fontSize = 13.sp,
            color = SaffronDark,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Order Summary Box
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFFF9F6F1))
            .border(1.dp, Color(0xFFEFE8DD), RoundedCornerShape(14.dp))
            .padding(12.dp)
        ) {
          Column {
            Text(
              text = "${order.customerName} • ${order.phone}",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = "📍 ${order.address}, ${order.area}, Bhiwani",
              fontSize = 11.5.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Payment: ${order.paymentMethod} • Slot: ${order.slot}",
              fontSize = 11.5.sp,
              fontWeight = FontWeight.Medium,
              color = AmberRoasted
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFE8DFD3))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "${order.items.size} Items Total",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
              Text(
                text = "₹${order.totalAmount}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = SaffronDark
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Simulated Delivery Timeline
        Text(
          text = if (isHindi) "लाइव डिलीवरी ट्रैकिंग (भिवानी हब)" else "Live Delivery Tracking (Bhiwani Hub)",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          TimelineStep(icon = Icons.Default.DoneAll, title = "Placed", isDone = true)
          TimelineStep(icon = Icons.Default.Inventory2, title = "Packing", isDone = true)
          TimelineStep(icon = Icons.Default.DeliveryDining, title = "On Way", isDone = false)
          TimelineStep(icon = Icons.Default.Check, title = "Delivered", isDone = false)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Action buttons
        Button(
          onClick = {
            onDismiss()
            onViewOrders()
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .testTag("view_order_history_btn"),
          colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
          shape = RoundedCornerShape(12.dp)
        ) {
          Text(
            text = if (isHindi) "ऑर्डर हिस्ट्री देखें" else "View in Order History",
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
          onClick = onDismiss,
          modifier = Modifier
            .fillMaxWidth()
            .height(42.dp),
          shape = RoundedCornerShape(12.dp)
        ) {
          Text(text = if (isHindi) "जारी रखें" else "Continue Shopping", color = SaffronDark)
        }
      }
    }
  }
}

@Composable
private fun TimelineStep(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  title: String,
  isDone: Boolean
) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Surface(
      shape = CircleShape,
      color = if (isDone) VegGreen else Color(0xFFE0D7CC),
      modifier = Modifier.size(28.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = if (isDone) Color.White else Color(0xFF756A5E),
          modifier = Modifier.size(15.dp)
        )
      }
    }
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = title,
      fontSize = 10.sp,
      fontWeight = if (isDone) FontWeight.Bold else FontWeight.Normal,
      color = if (isDone) VegGreen else Color(0xFF756A5E)
    )
  }
}
