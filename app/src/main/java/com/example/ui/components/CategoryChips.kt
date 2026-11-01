package com.example.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ProductCategory
import com.example.ui.theme.AmberRoasted
import com.example.ui.theme.SaffronPrimary

@Composable
fun CategoryChips(
  selectedCategory: ProductCategory,
  onCategorySelected: (ProductCategory) -> Unit,
  isHindi: Boolean,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .horizontalScroll(rememberScrollState())
      .padding(horizontal = 16.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    ProductCategory.values().forEach { category ->
      val isSelected = category == selectedCategory
      FilterChip(
        selected = isSelected,
        onClick = { onCategorySelected(category) },
        label = {
          Text(
            text = "${category.icon} ${if (isHindi) category.hindiName else category.displayName}",
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 13.sp
          )
        },
        shape = RoundedCornerShape(12.dp),
        colors = FilterChipDefaults.filterChipColors(
          containerColor = Color.White,
          labelColor = MaterialTheme.colorScheme.onSurface,
          selectedContainerColor = SaffronPrimary,
          selectedLabelColor = Color.White
        ),
        border = FilterChipDefaults.filterChipBorder(
          enabled = true,
          selected = isSelected,
          borderColor = MaterialTheme.colorScheme.outline,
          selectedBorderColor = SaffronPrimary
        ),
        modifier = Modifier.testTag("cat_chip_${category.name.lowercase()}")
      )
    }
  }
}
