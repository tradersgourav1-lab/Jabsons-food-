package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BhiwaniData
import com.example.ui.theme.AmberRoasted
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.SaffronDark
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.VegGreen

@Composable
fun StoreInfoScreen(
  isHindi: Boolean,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CreamBackground)
      .verticalScroll(rememberScrollState())
      .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp)
      .testTag("store_info_screen")
  ) {
    // Store Title Card
    Card(
      colors = CardDefaults.cardColors(containerColor = Color.White),
      shape = RoundedCornerShape(20.dp),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Column(modifier = Modifier.padding(18.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            modifier = Modifier.size(52.dp),
            shape = CircleShape,
            color = Color(0xFFFFF0D6)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(imageVector = Icons.Default.Store, contentDescription = null, tint = SaffronPrimary, modifier = Modifier.size(28.dp))
            }
          }
          Spacer(modifier = Modifier.width(14.dp))
          Column {
            Text(
              text = "Jabsons Bhiwani Hub",
              fontSize = 18.sp,
              fontWeight = FontWeight.Black,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = if (isHindi) "अधिकृत भिवानी स्नैक्स व नमकीन डिलीवरी सेंटर" else "Authorized Bhiwani City Snacks Delivery Hub",
              fontSize = 12.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        Divider(modifier = Modifier.padding(vertical = 14.dp), color = Color(0xFFF1EAE0))

        // Address
        Row(verticalAlignment = Alignment.Top) {
          Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = SaffronPrimary, modifier = Modifier.size(18.dp).padding(top = 2.dp))
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(text = "Store Address:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(text = BhiwaniData.STORE_ADDRESS, fontSize = 12.5.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 17.sp)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Timings
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = AmberRoasted, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(text = "Delivery Hours:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(text = BhiwaniData.STORE_TIMINGS, fontSize = 12.5.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Action Buttons (Call / WhatsApp)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Button(
            onClick = {
              val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${BhiwaniData.STORE_PHONE}"))
              try { context.startActivity(intent) } catch (_: Exception) {}
            },
            modifier = Modifier.weight(1f).height(44.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
            shape = RoundedCornerShape(10.dp)
          ) {
            Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "Call Store", fontSize = 13.sp, fontWeight = FontWeight.Bold)
          }

          OutlinedButton(
            onClick = {
              val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/919812045678?text=Hello%20Jabsons%20Bhiwani%20Support"))
              try { context.startActivity(intent) } catch (_: Exception) {}
            },
            modifier = Modifier.weight(1f).height(44.dp),
            shape = RoundedCornerShape(10.dp)
          ) {
            Icon(imageVector = Icons.Default.Chat, contentDescription = null, tint = VegGreen, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "WhatsApp", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = VegGreen)
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Quality Commitments
    Text(
      text = if (isHindi) "क्वालिटी का वादा" else "Jabsons Quality Guarantee",
      fontSize = 16.sp,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(10.dp))

    Card(
      colors = CardDefaults.cardColors(containerColor = Color.White),
      shape = RoundedCornerShape(16.dp),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        QualityItem(
          title = "Slow Clay Oven Roasting",
          desc = "Peanuts are dry-roasted in traditional clay ovens to lock in natural oils and crunchiness without deep frying."
        )
        QualityItem(
          title = "Authentic Bharuch Heritage",
          desc = "Handpicked seeds and authentic traditional Gujarati & North Indian spice formulas perfected over decades."
        )
        QualityItem(
          title = "Zero Compromise on Hygiene",
          desc = "Nitrogen-flushed stay-fresh packaging ensuring crispness in all seasons across Haryana."
        )
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Bhiwani Delivery Areas Covered
    Text(
      text = if (isHindi) "भिवानी शहर के सभी क्षेत्र शामिल" else "Delivery Coverage in Bhiwani City",
      fontSize = 16.sp,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(8.dp))

    Card(
      colors = CardDefaults.cardColors(containerColor = Color.White),
      shape = RoundedCornerShape(16.dp),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Text(
          text = "Fast 30–45 min delivery to all major colonies:",
          fontSize = 12.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        BhiwaniData.LOCALITIES.chunked(2).forEach { rowAreas ->
          Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            rowAreas.forEach { area ->
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
              ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = VegGreen, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = area.split(" (").first(), fontSize = 11.5.sp, fontWeight = FontWeight.Medium)
              }
            }
          }
        }
      }
    }
  }
}

@Composable
private fun QualityItem(title: String, desc: String) {
  Row(verticalAlignment = Alignment.Top) {
    Surface(
      shape = CircleShape,
      color = Color(0xFFE8F5E9),
      modifier = Modifier.size(24.dp).padding(top = 2.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = VegGreen, modifier = Modifier.size(13.dp))
      }
    }
    Spacer(modifier = Modifier.width(10.dp))
    Column {
      Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
      Text(text = desc, fontSize = 11.5.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 16.sp)
    }
  }
}
