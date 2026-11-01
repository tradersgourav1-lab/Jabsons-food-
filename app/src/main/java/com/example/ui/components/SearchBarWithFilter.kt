package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SaffronPrimary

@Composable
fun SearchBarWithFilter(
  query: String,
  onQueryChange: (String) -> Unit,
  isHindi: Boolean,
  modifier: Modifier = Modifier
) {
  val focusManager = LocalFocusManager.current

  OutlinedTextField(
    value = query,
    onValueChange = onQueryChange,
    modifier = modifier
      .fillMaxWidth()
      .testTag("search_text_input"),
    placeholder = {
      Text(
        text = if (isHindi) "मूंगफली, नमकीन, स्नैक्स खोजें..." else "Search peanuts, namkeen, combos...",
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
      )
    },
    leadingIcon = {
      Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "Search",
        tint = SaffronPrimary
      )
    },
    trailingIcon = {
      if (query.isNotEmpty()) {
        IconButton(
          onClick = {
            onQueryChange("")
            focusManager.clearFocus()
          }
        ) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Clear search",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    },
    singleLine = true,
    shape = RoundedCornerShape(16.dp),
    colors = OutlinedTextFieldDefaults.colors(
      focusedContainerColor = Color.White,
      unfocusedContainerColor = Color.White,
      focusedBorderColor = SaffronPrimary,
      unfocusedBorderColor = MaterialTheme.colorScheme.outline
    ),
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
  )
}
