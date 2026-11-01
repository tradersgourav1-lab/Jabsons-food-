package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.OrderEntity
import com.example.ui.theme.AmberRoasted
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.SaffronDark
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.VegGreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrderHistoryScreen(
  orders: List<OrderEntity>,
  isHindi: Boolean,
  onStartShopping: () -> Unit,
  modifier: Modifier = Modifier
) {
  val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CreamBackground)
      .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 80.dp)
      .testTag("order_history_screen")
  ) {
    Text(
      text = if (isHindi) "ऑर्डर हिस्ट्री व ट्रैकिंग (${orders.size})" else "Order History & Tracking (${orders.size})",
      fontSize = 18.sp,
      fontWeight = FontWeight.Black,
      color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(12.dp))

    if (orders.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(bottom = 60.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Surface(
            modifier = Modifier.size(80.dp),
            shape = CircleShape,
            color = Color(0xFFFFF0D6)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.ReceiptLong,
                contentDescription = null,
                tint = SaffronPrimary,
                modifier = Modifier.size(38.dp)
              )
            }
          }
          Spacer(modifier = Modifier.height(14.dp))
          Text(
            text = if (isHindi) "अभी कोई पुराना ऑर्डर नहीं है" else "No Orders Placed Yet",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = if (isHindi) "भिवानी के लिए अपना पहला ऑर्डर दर्ज करें!" else "Your demo orders in Bhiwani will appear here with live tracking.",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(16.dp))
          Button(
            onClick = onStartShopping,
            colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
            shape = RoundedCornerShape(12.dp)
          ) {
            Text(text = if (isHindi) "स्नैक्स खरीदें" else "Browse Snacks")
          }
        }
      }
    } else {
      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        items(orders, key = { it.orderId }) { order ->
          Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth().testTag("order_card_${order.orderId}")
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Surface(
                  color = Color(0xFFFFF0D9),
                  shape = RoundedCornerShape(6.dp)
                ) {
                  Text(
                    text = "ID: ${order.orderId}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = SaffronDark,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                  )
                }

                Surface(
                  color = Color(0xFFE8F5E9),
                  shape = RoundedCornerShape(6.dp)
                ) {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                  ) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = VegGreen, modifier = Modifier.size(12.dp))
                    Text(
                      text = " Confirmed",
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Bold,
                      color = VegGreen
                    )
                  }
                }
              }

              Spacer(modifier = Modifier.height(8.dp))

              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.AccessTime, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(13.dp))
                Text(
                  text = " ${dateFormat.format(Date(order.createdAt))}",
                  fontSize = 11.5.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }

              Spacer(modifier = Modifier.height(4.dp))

              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = SaffronPrimary, modifier = Modifier.size(13.dp))
                Text(
                  text = " ${order.area}, Bhiwani",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = MaterialTheme.colorScheme.onSurface
                )
              }

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = "Items: ${order.itemsSummary}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp
              )

              Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFF1EAE0))

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "Payment: ${order.paymentMethod}",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Medium,
                  color = AmberRoasted
                )

                Text(
                  text = "Total: ₹${order.totalAmount}",
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Black,
                  color = SaffronDark
                )
              }
            }
          }
        }
      }
    }
  }
}
