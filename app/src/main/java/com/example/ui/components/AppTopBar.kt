package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SaffronDark
import com.example.ui.theme.SaffronLight
import com.example.ui.theme.SaffronPrimary

@Composable
fun AppTopBar(
  isHindi: Boolean,
  onToggleLanguage: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
      .background(
        brush = Brush.linearGradient(
          colors = listOf(SaffronPrimary, SaffronDark)
        )
      )
      .statusBarsPadding()
      .padding(horizontal = 16.dp, vertical = 14.dp)
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "JABSONS BHIWANI",
              fontSize = 20.sp,
              fontWeight = FontWeight.Black,
              letterSpacing = 0.5.sp,
              color = Color.White
            )
          }
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = if (isHindi) "नमकीन • मूंगफली • रोस्टेड स्नैक्स • गिफ्ट कॉम्बो"
                   else "Snacks • Namkeen • Peanuts • Gift Packs",
            fontSize = 11.5.sp,
            color = Color.White.copy(alpha = 0.92f),
            fontWeight = FontWeight.Medium
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          // Language Switcher
          AssistChip(
            onClick = onToggleLanguage,
            label = {
              Text(
                text = if (isHindi) "EN" else "हिन्दी",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = SaffronDark
              )
            },
            leadingIcon = {
              Icon(
                imageVector = Icons.Default.Translate,
                contentDescription = "Language",
                tint = SaffronDark,
                modifier = Modifier.height(14.dp).width(14.dp)
              )
            },
            colors = AssistChipDefaults.assistChipColors(
              containerColor = Color.White
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.testTag("lang_toggle_chip")
          )

          Spacer(modifier = Modifier.width(6.dp))

          // Location badge
          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(20.dp))
              .background(Color.White.copy(alpha = 0.25f))
              .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.LocationOn,
              contentDescription = "Bhiwani City",
              tint = Color.White,
              modifier = Modifier.height(14.dp).width(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Bhiwani",
              color = Color.White,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }
  }
}
